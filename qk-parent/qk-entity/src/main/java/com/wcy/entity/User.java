package com.wcy.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 用户表实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class User {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名
     */
    private String name;

    /**
     * 手机号 (char 11位，通常用 String 接收)
     */
    private String phone;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 性别 (tinyint)
     */
    private Integer gender;

    /**
     * 状态 (tinyint)
     */
    private Integer status;

    /**
     * 部门ID
     */
    private Integer deptId;

    /**
     * 角色ID
     */
    private Integer roleId;

    /**
     * 头像/图片地址
     */
    private String image;

    /**
     * 备注
     */
    private String remark;

    // 盐
    private String salt;

    /**
     * 创建时间 (插入时自动填充)
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 修改时间 (插入和更新时自动填充)
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    // 在 User 实体类中追加：
    @TableField(exist = false)
    private String deptName;

    @TableField(exist = false)
    private String roleName;
}