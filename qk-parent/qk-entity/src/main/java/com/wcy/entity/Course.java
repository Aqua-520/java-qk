package com.wcy.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 课程表
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("course")
public class Course {

    /**
     * 课程id, 主键
     */
    private Integer id;

    /**
     * 课程学科，1:AI智能应用开发(Java), 2:AI大模型开发(Python)，
     * 3:AI鸿蒙开发，4:AI大数据，5:AI嵌入式，6:AI测试，7:AI运维
     */
    private Integer subject;

    /**
     * 课程名称
     */
    private String name;

    /**
     * 课程价格（元）
     */
    private Integer price;

    /**
     * 适用人群, 1:小白学员, 2:初级程序员, 3:中级程序员
     */
    private Integer target;

    /**
     * 课程介绍
     */
    private String description;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改时间
     */
    private LocalDateTime updateTime;
}