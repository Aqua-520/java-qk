package com.wcy.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 线索跟进记录实体类
 */
@Data
@TableName("clue_track_record")
public class ClueTrackRecord {

    /** 跟进记录id, 主键 */
    @TableId
    private Integer id;

    /**
     * 线索id, 关联 clue.id (NOT NULL)
     * 关联线索表的主键,线索表记录的是一个个的学员信息
     * */
    private Integer clueId;

    /**
     * 跟进人id, 关联 user.id (NOT NULL)
     * 记录由我们系统中的哪位老师来进行跟进
     * */
    private Integer userId;

    /** 本次跟进后的意向学科, 1:AI智能应用开发(Java), 2:AI大模型开发(Python), 3:AI鸿蒙开发, 4:AI大数据, 5:AI嵌入式, 6:AI测试, 7:AI运维 (字典, 同步更新到 clue.subject) */
    private Integer subject;

    /** 本次跟进后的意向等级, 1:近期学习, 2:打算学习(考虑中), 3:进行了解, 4:打酱油 (字典, 同步更新到 clue.level) */
    private Integer level;

    /** 跟进记录内容 */
    private String record;

    /** 下次跟进时间 (同步更新到 clue.next_time) */
    private LocalDateTime nextTime;

    /** 跟进类型, 1:正常跟进, 0:伪线索 (字典) */
    private Integer type;

    /** 伪线索原因, 1:空号, 2:停机, 3:竞品, 4:无法联系, 5:其他 (仅 type=0 时有值) */
    private Integer falseReason;

    /** 创建时间 (插入时自动填充, 记录为日志, 不设 updateTime) */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}