package com.wcy.controller;

import com.wcy.common.Response;
import com.wcy.dto.LoginDTO;
import com.wcy.service.LoginService;
import com.wcy.vo.LoginVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// 接收登录请求的类
@RestController
@RequiredArgsConstructor
@Slf4j
public class LoginController {
    // 封装业务层对象
    private final LoginService loginService;

    // 接收登录请求
    @PostMapping("/login")
    public Response login(@RequestBody LoginDTO loginDTO){
        // 接收前端传来的登录参数
        // 业务层返回一个前端需要的vo对象
        LoginVO loginVO = this.loginService.login(loginDTO);

        return Response.success(loginVO);
    }
}
