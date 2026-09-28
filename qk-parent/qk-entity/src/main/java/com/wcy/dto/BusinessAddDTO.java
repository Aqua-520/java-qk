package com.wcy.dto;

import lombok.Data;

/**
 * 新增商机DTO
 * 只包含前端允许传入的字段，防止前端恶意篡改状态、归属人等
 */
@Data
public class BusinessAddDTO {

    /** 客户姓名 */
    private String name;

    /** 手机号 (前端建议传字符串，避免大数字精度丢失) */
    private String phone;

    /** 性别，1:男, 2:女 */
    private Integer gender;

    /** 年龄 */
    private Integer age;

    /** 微信号 */
    private String wechat;

    /** QQ号 */
    private String qq;

    /** 意向学科，1:ai智能应用开发(java), 2:ai大模型开发(python)，3:ai鸿蒙开发，4:ai大数据，5:ai嵌入式，6:ai测试，7:ai运维 */
    private Integer subject;

    /** 意向课程, 课程id */
    private Integer courseId;

    /** 学历, 1:高中、2:中专、3:大专、4:本科、5:硕士、6:博士、7:其他 */
    private Integer degree;

    /** 在职情况, 1: 在职, 0: 离职 */
    private Integer jobStatus;

    /** 渠道来源，1:线上活动, 2:推广介绍 */
    private Integer channel;

    /** 备注 */
    private String remark;
}