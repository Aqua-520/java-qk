package com.wcy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wcy.common.PageResponse;
import com.wcy.dto.ClueMarkFalseDTO;
import com.wcy.dto.CluePoolDTO;
import com.wcy.dto.ClueQueryDTO;
import com.wcy.dto.ClueUpdateDTO;
import com.wcy.entity.Clue;
import com.wcy.vo.CluePoolVO;

public interface ClueService extends IService<Clue> {
    PageResponse<Clue> selectClueListByLimit(ClueQueryDTO clueQueryDTO);

    void assignClue(Integer clueId, Integer userId);

    Clue selectClueById(Integer clueId);

    void updateClue(ClueUpdateDTO clueUpdateDTO);

    void clueToBusiness(Integer clueId);

    void clueToFalse(Integer clueId, ClueMarkFalseDTO clueMarkFalseDTO);

    PageResponse<CluePoolVO> selectCluePool(CluePoolDTO cluePoolDTO);
}
