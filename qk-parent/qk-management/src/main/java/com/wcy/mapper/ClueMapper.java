package com.wcy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wcy.dto.ClueQueryDTO;
import com.wcy.entity.Clue;
import com.wcy.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ClueMapper extends BaseMapper<Clue> {
    // 去xml中定义映射
    Page<Clue> selectClueListByLimit(IPage<Clue> page, ClueQueryDTO clueQueryDTO);

    // 三表联查
    Clue selectClueById(Integer clueId);
}
