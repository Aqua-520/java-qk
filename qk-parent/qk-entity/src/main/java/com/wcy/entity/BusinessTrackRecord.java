package com.wcy.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

// 记录商机跟进历史的表
@Data
@TableName("business_track_record")
public class BusinessTrackRecord {

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer businessId;
    private Integer userId;
    private Integer trackStatus;
    private String keyItems;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime nextTime;

    private String record;

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
}