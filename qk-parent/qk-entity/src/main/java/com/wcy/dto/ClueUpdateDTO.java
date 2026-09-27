package com.wcy.dto;

import com.wcy.entity.Clue;
import lombok.Data;
import lombok.EqualsAndHashCode;

// 接收前端请求体
@EqualsAndHashCode(callSuper = true)
@Data
public class ClueUpdateDTO extends Clue {
    private String record;
}
