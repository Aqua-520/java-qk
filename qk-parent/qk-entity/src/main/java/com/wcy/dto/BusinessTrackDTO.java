package com.wcy.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 商机跟进记录合并DTO
 * 用于接收包含商机基础信息及本次跟进记录的数据
 */
@Data
public class BusinessTrackDTO {

    /** 商机id */
    private Integer id;

    /** 客户姓名 */
    private String name;

    /** 手机号 */
    private String phone;

    /** 性别，1:男, 2:女 */
    private Integer gender;

    /** 年龄 */
    private Integer age;

    /** 微信号 */
    private String wechat;

    /** QQ号 */
    private String qq;

    /** 意向学科 */
    private Integer subject;

    /** 意向课程id */
    private Integer courseId;

    /** 学历 */
    private Integer degree;

    /** 在职情况, 1:在职, 0:离职 */
    private Integer jobStatus;

    /** 渠道来源, 1:线上活动, 2:推广介绍 */
    private Integer channel;

    /** 备注 */
    private String remark;

    /** 商机状态 */
    private Integer status;

    /** 归属人id */
    private Integer userId;

    /** 归属线索id */
    private Integer clueId;

    /** 下次跟进时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime nextTime;

    // ================== 以下为本次跟进记录相关字段 ==================

    /** 沟通重点 (前端传的是数组，对应数据库的 varchar) */
    private List<String> keyItems;

    /** 跟进状态, 1:接通, 2:拒绝, 3:无人接听 */
    private Integer trackStatus;

    /** 沟通纪要 */
    private String record;
}