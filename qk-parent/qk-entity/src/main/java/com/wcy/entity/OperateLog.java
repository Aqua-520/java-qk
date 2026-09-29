package com.wcy.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@TableName("operate_log")
public class OperateLog {
    @TableId
    private Integer id; //ID
    private Integer operateUserId; //操作用户ID
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime operateTime; //操作时间
    private String className; //类名称
    private String methodName; //方法名称
    private String methodParams; //方法参数
    private String returnValue; //返回值
    private Long costTime; //耗时

    @TableField(exist = false)
    private String operateUserName; // 操作用户姓名，仅返回给前端
}