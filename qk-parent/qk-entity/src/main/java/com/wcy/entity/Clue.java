package com.wcy.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 线索实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("clue")
public class Clue {

    /** 线索id, 主键 */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /** 手机号 (唯一) */
    private String phone;

    /** 渠道来源, 1:线上活动, 2:推广介绍 (字典) */
    private Integer channel;

    /** 活动id, 关联 activity.id */
    private Integer activityId;

    /** 客户姓名 */
    private String name;

    /** 性别, 1:男, 2:女 (字典) */
    private Integer gender;

    /** 年龄 */
    private Integer age;

    /** 微信号 */
    private String wechat;

    /** QQ号 */
    private String qq;

    /**
     * 归属人id, 关联 user.id (NULL 表示待分配)
     * 这里的user都是系统中的各种权限账号,比如管理员,或者不同权限的老师
     * */
    private Integer userId;

    /** 线索状态, 1:待分配, 2:待跟进, 3:跟进中, 4:伪线索, 5:转为商机 (字典, 以建表语句为准) */
    private Integer status;

    /** 意向学科, 1:AI智能应用开发(Java), 2:AI大模型开发(Python), 3:AI鸿蒙开发, 4:AI大数据, 5:AI嵌入式, 6:AI测试, 7:AI运维 (字典) */
    private Integer subject;

    /** 意向等级, 1:近期学习, 2:打算学习(考虑中), 3:进行了解, 4:打酱油 (字典) */
    private Integer level;

    /** 下次跟进时间 (冗余自最新一条 clue_track_record.next_time) */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime nextTime;

    /** 创建时间 (插入时自动填充) */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /** 修改时间 (插入和更新时自动填充) */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    // 新增归属人的名称,作为响应参数返回给前端,并且让数据库忽略
    @TableField(exist = false)
    private String assignName;

    // 新增一条属性,封装需要返回的跟进历史
    @TableField(exist = false)
    private List<ClueTrackRecord> trackRecords;

}