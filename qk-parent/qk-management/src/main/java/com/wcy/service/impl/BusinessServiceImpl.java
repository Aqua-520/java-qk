package com.wcy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.common.PageResponse;
import com.wcy.dto.BusinessAddDTO;
import com.wcy.dto.BusinessPoolQueryDTO;
import com.wcy.dto.BusinessQueryDTO;
import com.wcy.dto.BusinessTrackDTO;
import com.wcy.entity.Business;
import com.wcy.entity.BusinessTrackRecord;
import com.wcy.entity.Customer;
import com.wcy.exception.BusinessException;
import com.wcy.mapper.BusinessMapper;
import com.wcy.mapper.BusinessTrackRecordMapper;
import com.wcy.mapper.CustomerMapper;
import com.wcy.service.BusinessService;
import com.wcy.utils.UserHolder;
import com.wcy.vo.BusinessPoolVO;
import com.wcy.vo.BusinessVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class BusinessServiceImpl extends ServiceImpl<BusinessMapper, Business> implements BusinessService {
    // 导入BusinessMapper
    private final BusinessMapper businessMapper;
    private final BusinessTrackRecordMapper businessTrackRecordMapper;
    private final CustomerMapper customerMapper;

    /**
     * 分页查询商机列表
     * @param businessQueryDTO 查询参数DTO
     * @return 返回分页的响应对象
     */
    @Override
    public PageResponse<Business> selectBusinessListByLimit(BusinessQueryDTO businessQueryDTO) {
        // 在mapper中创建自定义方法,返回分页对象
        Page<Business> page = this.businessMapper.selectBusinessListByLimit(new Page<>(businessQueryDTO.getPage(),
                businessQueryDTO.getPageSize()), businessQueryDTO);

        PageResponse<Business> businessPageResponse = new PageResponse<>();
        businessPageResponse.setTotal(page.getTotal());
        businessPageResponse.setRows(page.getRecords());

        return businessPageResponse;
    }

    /**
     * 保存新建的商机
     * @param businessAddDTO 请求体模型,收集前端输入的字段
     */
    @Override
    public void addBusiness(BusinessAddDTO businessAddDTO) {
        // 新建商机对象,进行值copy
        Business business = new Business();

        // 值拷贝
        BeanUtil.copyProperties(businessAddDTO,business);

        // 强制设置后台控制的字段，防止前端篡改
        business.setId(null);
        business.setStatus(1);        // 默认待分配
        business.setUserId(null);     // 待分配，无归属人
        business.setClueId(null);     // 如果是直接新增商机，无关联线索（视业务而定）
        business.setNextTime(null);

        // 保存对象
        this.businessMapper.insert(business);
    }

    /**
     * 将未分配的商机分配给指定专员
     * @param businessId 商机id
     * @param userId 哪一位老师跟进
     */
    @Override
    public void assignBusiness(Integer businessId, Integer userId) {
        // 查出商机对象
        Business business = this.businessMapper.selectById(businessId);

        // 修改商机专员的id
        business.setUserId(userId);
        // 修改跟进状态为待跟进
        business.setStatus(2);

        // 回存
        this.businessMapper.updateById(business);
    }

    /**
     * 将商机踢回公海
     * @param businessId 商机id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void backBusiness(Integer businessId) {
        // 查询出商机对象
        Business business = this.businessMapper.selectById(businessId);
        // 修改状态为回收
        business.setStatus(4);
        // 保存
        this.businessMapper.updateById(business);

        // 新建商机跟进记录对象
        BusinessTrackRecord businessTrackRecord = new BusinessTrackRecord();

        // 设置字段
        // 设置记录的商机id
        businessTrackRecord.setBusinessId(businessId);
        // 设置操作人id
        businessTrackRecord.setUserId(UserHolder.getUserId());

        // 踢回公海，不在正常跟进状态里，看你们字典定义，没有就随便给个值
        businessTrackRecord.setTrackStatus(0);
        businessTrackRecord.setKeyItems("踢回公海");
        businessTrackRecord.setNextTime(null);
        businessTrackRecord.setRecord("商机已被踢回公海"); // 没传原因，先写死

        // 保存到数据库
        this.businessTrackRecordMapper.insert(businessTrackRecord);
    }

    /**
     * 转客户
     * @param businessId 商机id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void toCustomer(Integer businessId) {
        // 将商机状态设置为客户
        // 查询出商机对象
        Business business = this.businessMapper.selectById(businessId);
        // 修改状态为回收
        business.setStatus(5);
        // 保存
        this.businessMapper.updateById(business);

        // 新建客户对象,存储客户信息到客户表
        Customer customer = new Customer();
        // 设置customer字段
        BeanUtils.copyProperties(business, customer, "id", "createTime", "updateTime");

        // courseId 类型现在一致，已自动复制
        // businessId 需要手动设置，因为 Customer 里才有这个字段
        customer.setBusinessId(businessId);

        this.customerMapper.insert(customer);
    }

    /**
     * 根据id查询商机详情
     * @param businessId 商机id
     * @return 返回vo模型对象,涉及到三表联查,需要自定义sql
     */
    @Override
    public BusinessVO selectBusinessById(Integer businessId) {
        return this.businessMapper.selectBusinessById(businessId);
    }

    /**
     * 跟进商机
     * @param businessTrackDTO 前端传过来的dto
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void assignBusiness(BusinessTrackDTO businessTrackDTO) {
        // 先根据id查询商机
        Business business = this.businessMapper.selectById(businessTrackDTO.getId());
        if (business == null) {
            throw new BusinessException("商机跟进失败，商机不存在");
        }

        // 将前端接收的字段更新到数据库对象中
        // 屏蔽更新id,phone,channel,userId,clueId，不允许修改这些字段
        BeanUtil.copyProperties(businessTrackDTO, business, "id", "phone", "userId", "clueId");

        // 修改商机状态为跟进中 (3)
        business.setStatus(3);

        // 将最新的商机对象保存到数据库
        this.businessMapper.updateById(business);

        // 创建跟进记录对象
        BusinessTrackRecord businessTrackRecord = new BusinessTrackRecord();
        // 设置此条记录操作的商机id
        businessTrackRecord.setBusinessId(businessTrackDTO.getId());

        // 获取当前登录的老师id并设置
        Integer userId = UserHolder.getUserId();
        businessTrackRecord.setUserId(userId);

        // 设置跟进状态
        businessTrackRecord.setTrackStatus(businessTrackDTO.getTrackStatus());

        // 注意：前端传的 keyItems 是 List<String>，数据库是 varchar，需要拼接
        if (businessTrackDTO.getKeyItems() != null && !businessTrackDTO.getKeyItems().isEmpty()) {
            businessTrackRecord.setKeyItems(String.join(",", businessTrackDTO.getKeyItems()));
        } else {
            businessTrackRecord.setKeyItems("[]");
        }

        // 设置下次跟进时间
        businessTrackRecord.setNextTime(businessTrackDTO.getNextTime());

        // 设置沟通纪要
        businessTrackRecord.setRecord(businessTrackDTO.getRecord());

        // 保存到数据库表中
        this.businessTrackRecordMapper.insert(businessTrackRecord);
    }

    /**
     * 查询公海池列表,并且需要带分页
     * @param poolQueryDTO 前端的查询参数封装的dto对象
     * @return 返回分页响应对象
     */
    @Override
    public PageResponse<BusinessPoolVO> selectPool(BusinessPoolQueryDTO poolQueryDTO) {
        Page<BusinessPoolVO> businessPoolVOPage = this.businessMapper.selectPool(new Page<>(
                poolQueryDTO.getPage(),poolQueryDTO.getPageSize()),poolQueryDTO);

        // 封装分页对象
        PageResponse<BusinessPoolVO> pageResponse = new PageResponse<>();
        pageResponse.setTotal(businessPoolVOPage.getTotal());
        pageResponse.setRows(businessPoolVOPage.getRecords());

        return pageResponse;
    }

}
