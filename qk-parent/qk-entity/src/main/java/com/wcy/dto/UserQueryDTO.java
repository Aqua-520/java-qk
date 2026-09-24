package com.wcy.dto;

import lombok.Data;

/**
 * 用户分页查询参数 DTO
 */
@Data
public class UserQueryDTO {

    /**
     * 姓名
     */
    private String name;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 手机号
     * ⚠️ 注意：必须用 String 接收！数据库文档里的 integer <int32> 是错的，
     * 像 13309091111 这种11位手机号远超 int32 最大值（约21亿），用 Integer 会直接报错或溢出。
     */
    private String phone;

    /**
     * 部门ID
     */
    private Integer deptId;

    /**
     * 页码（默认 1）
     */
    private Integer page = 1;

    /**
     * 每页展示记录（默认 10）
     */
    private Integer pageSize = 10;
}