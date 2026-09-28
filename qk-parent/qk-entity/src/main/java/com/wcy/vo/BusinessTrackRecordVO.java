package com.wcy.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 商机跟进记录响应VO
 */
@Data
public class BusinessTrackRecordVO {

    private Integer id;

    private Integer businessId;
    private Integer userId;
    private Integer trackStatus;
    private String keyItems;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime nextTime;

    private String record;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /** 跟进人姓名（数据库没有，需联表或手动补充） */
    private String assignName;
}