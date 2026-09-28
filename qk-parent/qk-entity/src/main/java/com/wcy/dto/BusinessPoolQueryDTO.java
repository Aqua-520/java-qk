package com.wcy.dto;

import lombok.Data;

/**
 * 商机池查询DTO
 */
@Data
public class BusinessPoolQueryDTO {

    /** 商机ID */
    private Integer businessId;

    /** 手机号 */
    private String phone;

    /** 客户姓名 */
    private String name;

    /** 意向学科 */
    private Integer subject;

    /** 页码, 默认1 */
    private Integer page = 1;

    /** 每页展示记录数, 默认10 */
    private Integer pageSize = 10;
}