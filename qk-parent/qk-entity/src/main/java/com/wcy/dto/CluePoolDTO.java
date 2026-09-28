package com.wcy.dto;

import lombok.Data;

@Data
public class CluePoolDTO {
    // 接收线索池回显的dto
    /** 线索ID */
    private Integer clueId;

    /** 手机号 */
    private String phone;

    /** 线索来源, 1:线上活动, 2:推广介绍 */
    private Integer channel;

    /** 页码 */
    private Integer page = 1;

    /** 每页展示记录数 */
    private Integer pageSize = 5;
}
