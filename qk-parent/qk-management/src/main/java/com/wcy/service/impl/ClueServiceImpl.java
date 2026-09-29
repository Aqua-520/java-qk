package com.wcy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.common.PageResponse;
import com.wcy.dto.ClueMarkFalseDTO;
import com.wcy.dto.CluePoolDTO;
import com.wcy.dto.ClueQueryDTO;
import com.wcy.dto.ClueUpdateDTO;
import com.wcy.entity.Business;
import com.wcy.entity.Clue;
import com.wcy.entity.ClueTrackRecord;
import com.wcy.exception.BusinessException;
import com.wcy.mapper.BusinessMapper;
import com.wcy.mapper.ClueMapper;
import com.wcy.mapper.ClueTrackRecordMapper;
import com.wcy.service.ClueService;
import com.wcy.utils.UserHolder;
import com.wcy.vo.CluePoolVO;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ClueServiceImpl extends ServiceImpl<ClueMapper, Clue> implements ClueService{
    //注入Mapper依赖
    private final ClueMapper clueMapper;
    private final ClueTrackRecordMapper clueTrackRecordMapper;
    private final BusinessMapper businessMapper;

    /**
     * 分页版线索查询
     * @param clueQueryDTO DTO查询参数对象
     * @return 返回分页对象
     */
    @Override
    public PageResponse<Clue> selectClueListByLimit(ClueQueryDTO clueQueryDTO) {
        // 使用自定义mapper方法实现多表联查
        // 返回page对象
        Page<Clue> pageInfo = this.clueMapper.selectClueListByLimit(new Page<>(clueQueryDTO.getPage(), clueQueryDTO.getPageSize()),clueQueryDTO);

        PageResponse<Clue> pageResponse = new PageResponse<>();
        pageResponse.setTotal(pageInfo.getTotal());
        pageResponse.setRows(pageInfo.getRecords());
        return pageResponse;
    }

    /**
     * 修改某一条线索,让某位老师跟进
     * @param clueId 线索id
     * @param userId 跟进的老师id
     */
    @Override
    public void assignClue(Integer clueId, Integer userId) {
        // 根据id查询对象
        Clue clue = this.clueMapper.selectById(clueId);

        if (clue == null){
            // 没有查询到,抛异常
            throw new BusinessException("线索分配对应的老师失败");
        }

        // 设置跟进人
        clue.setUserId(userId);

        // 设置跟进状态
        clue.setStatus(2); // 2为待跟进

        // 回写对象
        this.clueMapper.updateById(clue);
    }

    /**
     * 根据id查询线索详情,并且根据线索id查询此线索的跟进记录,还得查询跟进老师的名字
     * @param clueId 线索id
     * @return 返回clue对象,里面已经新增好了一条
     */
    @Override
    public Clue selectClueById(Integer clueId) {
        // 去Mapper中定义抽象方法,然后通过xml自定义sql完成数据封装

        return this.clueMapper.selectClueById(clueId);
    }

    /**
     * 对线索做跟进的更新操作
     * @param clueUpdateDTO 更新的最新数据请求体
     */
    @Override
    // 添加事务注解,让多表操作同时成功
    @Transactional(rollbackFor = Exception.class) // 所有异常都会回滚
    public void updateClue(ClueUpdateDTO clueUpdateDTO) {
        // 先根据id看看能不能查到线索
        // 这里不能用三表联查的自定义sql,因为我们只需要线索表的字段数据
        Clue clue = this.clueMapper.selectById(clueUpdateDTO.getId());

        if (clue == null){
            throw new BusinessException("线索跟进失败,请检查业务逻辑");
        }

        // 使用工具包中的方法,将我们前端接收的字段全部更新到数据库对象中
        // 屏蔽更新id,phone,channel,不允许更新这三个字段
        BeanUtil.copyProperties(clueUpdateDTO,clue,"id","phone","channel");

        // 修改跟进状态
        clue.setStatus(3);

        // 将最新的对象保存到数据库
        this.clueMapper.updateById(clue);

        // 创建一条最新的跟进记录保存到数据库中
        ClueTrackRecord clueTrackRecord = getClueTrackRecord(clueUpdateDTO, clue);

        // 保存到数据库表中
        this.clueTrackRecordMapper.insert(clueTrackRecord);
    }

    /**
     * 创建线索跟进记录的工具函数
     * @param clueUpdateDTO 前端传入的新数据
     * @param clue 后端查询的旧数据对象
     * @return 返回一条记录对象
     */
    @NonNull
    private static ClueTrackRecord getClueTrackRecord(ClueUpdateDTO clueUpdateDTO, Clue clue) {
        ClueTrackRecord clueTrackRecord = new ClueTrackRecord();
        clueTrackRecord.setClueId(clueUpdateDTO.getId());

        // 从线程池中获取用户id,设置当前这条跟进记录的跟进老师是谁
        Integer currentUserId = UserHolder.getUserId();
        clueTrackRecord.setUserId(currentUserId);

        clueTrackRecord.setSubject(clueUpdateDTO.getSubject());
        clueTrackRecord.setLevel(clueUpdateDTO.getLevel());
        clueTrackRecord.setRecord(clueUpdateDTO.getRecord());
        clueTrackRecord.setNextTime(clueUpdateDTO.getNextTime());
        clueTrackRecord.setType(1);
        return clueTrackRecord;
    }

    /**
     * 将线索转商机的方法
     * @param clueId 线索id
     */
    @Override
    // 开启事务,一组操作
    @Transactional(rollbackFor = Exception.class)
    public void clueToBusiness(Integer clueId) {
        // 通过线索id查询到线索对象,修改状态为5,转为商机
        Clue clue = this.clueMapper.selectById(clueId);
        // 状态5为商机状态
        clue.setStatus(5);
        // 将线索对象保存回去
        this.clueMapper.updateById(clue);

        // 创建商机对象
        Business business = new Business();
        // 将线索对象的通用属性设置给商机
        BeanUtil.copyProperties(clue,business);

        // 将商机对象的一些属性重置一下
        business.setId(null);
        // 设置商机归属人为空
        business.setUserId(null);
        // 设置下次联系时间为空
        business.setNextTime(null);
        // 设置为待分配商机的跟进老师
        business.setStatus(1);
        // 此商机来自哪条线索
        business.setClueId(clue.getId());

        // 将商机对象保存到商机表中
        this.businessMapper.insert(business);
    }

    /**
     * 将线索转伪线索的方法
     * @param clueId 线索id
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void clueToFalse(Integer clueId ,ClueMarkFalseDTO clueMarkFalseDTO) {
        // 通过线索id查询到线索对象
        Clue clue = this.clueMapper.selectById(clueId);
        // 状态4为伪线索
        clue.setStatus(4);
        // 将线索对象保存回去
        this.clueMapper.updateById(clue);

        // 创建更新记录
        ClueTrackRecord clueTrackRecord = new ClueTrackRecord();

        // 设置此条记录操作的线索id
        clueTrackRecord.setClueId(clueId);
        // 设置操作的老师是
        // 获取老师id
        Integer userId = UserHolder.getUserId();
        clueTrackRecord.setUserId(userId);
        // 类型 0 表示伪线索
        clueTrackRecord.setType(0);
        // 设置伪线索原因
        clueTrackRecord.setFalseReason(clueMarkFalseDTO.getReason());
        // 设置伪线索的注释
        clueTrackRecord.setRecord(clueMarkFalseDTO.getRemark());
        clueTrackRecordMapper.insert(clueTrackRecord);
    }

    /**
     * 查询线索池,分页版,需要返回相应的字段,设计多张表
     * 通过xml自定义sql,用vo封装实体类返回
     * @param cluePoolDTO 前端dto
     * @return 返回分页响应对象
     */
    @Override
    public PageResponse<CluePoolVO> selectCluePool(CluePoolDTO cluePoolDTO) {
        // 调用mapper的自定义sql方法,实现多表联查
        Page<CluePoolVO> poolInfo = this.clueMapper.selectCluePool(new Page<>(cluePoolDTO.getPage(),cluePoolDTO.getPageSize()),cluePoolDTO);

        // 封装page响应
        PageResponse<CluePoolVO> pageResponse = new PageResponse<>();
        pageResponse.setTotal(poolInfo.getTotal());
        pageResponse.setRows(poolInfo.getRecords());
        return pageResponse;
    }

}

