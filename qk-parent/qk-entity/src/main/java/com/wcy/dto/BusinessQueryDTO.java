package com.wcy.dto;

import lombok.Data;

/**
 * 商机查询DTO
 */
@Data
public class BusinessQueryDTO {

    /** 商机ID */
    private Integer businessId;

    /** 客户姓名 */
    private String name;

    /** 手机号 */
    private String phone;

    /** 状态, 1:待分配, 2:待跟进, 3:跟进中, 4:回收, 5:转客户 */
    private Integer status;

    /** 归属人姓名 */
    private String assignName;

    /** 页码, 默认1 */
    private Integer page = 1;

    /** 每页展示记录数, 默认10 */
    private Integer pageSize = 10;
}