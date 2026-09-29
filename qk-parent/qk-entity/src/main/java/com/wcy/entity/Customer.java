package com.wcy.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("customer")
public class Customer {

    /**
     * id, 主键，数据库自增
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 客户姓名
     */
    private String name;

    /**
     * 渠道来源，1:线上活动, 2:推广介绍
     */
    private Integer channel;

    /**
     * 性别，1:男, 2:女
     */
    private Integer gender;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 微信号
     */
    private String wechat;

    /**
     * qq号
     */
    private String qq;

    /**
     * 学历, 1:高中、2:中专、3:大专、4:本科、5:硕士、6:博士、7:其他
     */
    private Integer degree;

    /**
     * 在职情况, 1: 在职, 0: 离职
     */
    private Integer jobStatus;

    /**
     * 意向学科，1:ai智能应用开发(java), 2:ai大模型开发(python)，3:ai鸿蒙开发，4:ai大数据，5:ai嵌入式，6:ai测试，7:ai运维
     */
    private Integer subject;

    /**
     * 课程id
     */
    private Integer courseId;

    /**
     * 商机id
     */
    private Integer businessId;

    /**
     * 创建时间，自动插入
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改时间，自动插入和更新
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
}