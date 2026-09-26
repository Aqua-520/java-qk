package com.wcy.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

// 前端需要的返回字段
@Data
public class LoginVO {
    private Long id;
    private String username;
    private String name;
    private String image;
    private String roleLabel;
    private String token;

    // 供数据库查询使用，不返回给前端
    @JsonIgnore
    private String password;

    @JsonIgnore
    private String salt;
}
