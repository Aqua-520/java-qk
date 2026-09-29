package com.wcy.dto;

import lombok.Data;

@Data
public class CustomerSaveDTO {
    private String phone;
    private String name;
    private Integer channel;
    private Integer gender;
    private Integer age;
    private String wechat;
    private String qq;
    private Integer degree;
    private Integer jobStatus;
    private Integer subject;
    private Integer courseId;
    private Integer businessId;
}