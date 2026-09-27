package com.wcy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.common.PageResponse;
import com.wcy.dto.ClueQueryDTO;
import com.wcy.dto.ClueUpdateDTO;
import com.wcy.entity.Clue;
import com.wcy.entity.ClueTrackRecord;
import com.wcy.exception.BusinessException;
import com.wcy.mapper.ClueMapper;
import com.wcy.mapper.ClueTrackRecordMapper;
import com.wcy.service.ClueService;
import com.wcy.utils.UserHolder;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ClueServiceImpl extends ServiceImpl<ClueMapper, Clue> implements ClueService{
    //注入Mapper依赖
    private final ClueMapper clueMapper;
    private final ClueTrackRecordMapper clueTrackRecordMapper;

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
     * @param clueUpdateDTO
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
}

