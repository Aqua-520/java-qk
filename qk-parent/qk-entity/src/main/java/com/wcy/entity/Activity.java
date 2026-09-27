package com.wcy.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

// 数据库模型类
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("activity")
public class Activity {

    /** id, 主键 */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /** 渠道来源, 1:线上活动, 2:推广介绍 */
    private Integer channel;

    /** 活动名称 */
    private String name;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime startTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime endTime;

    /** 活动简介 */
    private String description;

    /** 活动类型, 1:课程折扣, 2:代金券 */
    private Integer type;

    /** 课程折扣 */
    private Double discount;

    /** 代金券金额（元） */
    private Integer voucher;

    /** 创建时间 : 插入时自动填充 */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /** 修改时间 : 插入和更新时都自动填充 */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;
}