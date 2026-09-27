package com.wcy.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.common.PageResponse;
import com.wcy.dto.ClueQueryDTO;
import com.wcy.entity.Clue;
import com.wcy.exception.BusinessException;
import com.wcy.mapper.ClueMapper;
import com.wcy.service.ClueService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClueServiceImpl extends ServiceImpl<ClueMapper, Clue> implements ClueService{
    //注入Mapper依赖
    private final ClueMapper clueMapper;

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
}

