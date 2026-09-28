package com.wcy.dto;

import lombok.Data;

// 将线索转伪线索的dto
@Data
public class ClueMarkFalseDTO {
    private Integer reason;
    private String remark;
}
