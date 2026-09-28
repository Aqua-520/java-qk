package com.wcy.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

// 商机表类
@Data
@TableName("business")
public class Business {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String name;
    private String phone;
    private Integer gender;
    private Integer age;
    private String wechat;
    private String qq;
    private Integer subject;
    private Integer courseId;
    // 学历
    private Integer degree;
    private Integer jobStatus;
    private Integer channel;
    private String remark;
    private Integer status;
    private Integer userId;
    private Integer clueId;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime nextTime;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime updateTime;

    // 归属人姓名,仅返回给前端,数据库忽略
    @TableField(exist = false)
    private String assignName;
}