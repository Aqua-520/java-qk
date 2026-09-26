package com.wcy.dto;

// 通过loginDTO接收登录参数

import lombok.Data;

@Data
public class LoginDTO {
    // 定义两个属性
    private String username;
    // 接收登录密码
    // 因为User类中的password字段被屏蔽了,前后端交互会忽略,单独定义类来进行接收
    private String password;
}
