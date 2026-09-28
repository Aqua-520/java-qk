package com.wcy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.common.PageResponse;
import com.wcy.dto.BusinessAddDTO;
import com.wcy.dto.BusinessQueryDTO;
import com.wcy.entity.Business;
import com.wcy.mapper.BusinessMapper;
import com.wcy.service.BusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BusinessServiceImpl extends ServiceImpl<BusinessMapper, Business> implements BusinessService {
    // 导入BusinessMapper
    private final BusinessMapper businessMapper;

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
}
