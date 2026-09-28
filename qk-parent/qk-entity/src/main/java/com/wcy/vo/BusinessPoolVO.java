package com.wcy.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 商机池响应VO
 */
@Data
public class BusinessPoolVO {

    /** 商机ID */
    private Integer id;

    /** 客户姓名 */
    private String name;

    /** 手机号 */
    private String phone;

    /** 性别(1: 男, 2: 女) */
    private Integer gender;

    /** 年龄 */
    private Integer age;

    /** 微信 */
    private String wechat;

    /** QQ */
    private String qq;

    /** 学科 */
    private Integer subject;

    /** 意向课程, 课程id */
    private Integer courseId;

    /** 学历 */
    private Integer degree;

    /** 在职情况, 1: 在职, 0: 离职 */
    private Integer jobStatus;

    /** 渠道(1:线上活动, 2:推广介绍) */
    private Integer channel;

    /** 备注 */
    private String remark;

    /** 状态(1:待分配, 2:待跟进, 3:跟进中, 4:回收, 5:转客户) */
    private Integer status;

    /** 归属人ID */
    private Integer userId;

    /** 关联线索ID */
    private Integer clueId;

    /** 下次跟进时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime nextTime;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /** 修改时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
}