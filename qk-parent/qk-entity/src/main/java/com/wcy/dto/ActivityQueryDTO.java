package com.wcy.dto;

import lombok.Data;

/**
 * 活动列表查询条件 DTO
 */
@Data
public class ActivityQueryDTO {

    /** 渠道来源(1: 线上活动, 2: 推广介绍) */
    private Integer channel;

    /** 活动类型(1: 课程折扣, 2: 代金券) */
    private Integer type;

    /** 状态(1: 未开始, 2: 进行中, 3: 已结束) */
    private Integer status;

    /** 页码，默认 1 */
    private Integer page = 1;

    /** 每页展示记录数，默认 5 */
    private Integer pageSize = 5;
}