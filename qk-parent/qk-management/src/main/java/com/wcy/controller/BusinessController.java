package com.wcy.controller;

import com.wcy.service.BusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

// 商机相关接口控制层类
@RestController
@RequiredArgsConstructor
public class BusinessController {
    // 注入业务层依赖
    private BusinessService businessService;
}
