package com.wcy.dto;

import lombok.Data;

/**
 * 客户分页查询参数 DTO
 */
@Data
public class CustomerQueryDTO {

    /**
     * 手机号
     */
    private String phone;

    /**
     * 客户姓名
     */
    private String name;

    /**
     * 渠道来源
     */
    private Integer channel;

    /**
     * 意向学科
     */
    private Integer subject;

    /**
     * 分页查询的页码，如果未指定，默认为1
     */
    private Integer page = 1;

    /**
     * 分页查询的每页记录数，如果未指定，默认为10
     */
    private Integer pageSize = 10;
}