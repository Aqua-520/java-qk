package com.wcy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wcy.common.PageResponse;
import com.wcy.dto.ClueQueryDTO;
import com.wcy.entity.Clue;

public interface ClueService extends IService<Clue> {
    PageResponse<Clue> selectClueListByLimit(ClueQueryDTO clueQueryDTO);

    void assignClue(Integer clueId, Integer userId);
}
