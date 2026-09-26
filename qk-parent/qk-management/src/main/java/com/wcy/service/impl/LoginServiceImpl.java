package com.wcy.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.wcy.dto.LoginDTO;
import com.wcy.exception.BusinessException;
import com.wcy.mapper.UserMapper;
import com.wcy.service.LoginService;
import com.wcy.utils.JwtUtil;
import com.wcy.vo.LoginVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {
    // 这里注入UserMapper,查询用户数据
    private final UserMapper userMapper;

    @Override
    public LoginVO login(LoginDTO loginDTO) {
        // 校验登录参数是否为空
        if (loginDTO == null || StrUtil.isBlank(loginDTO.getUsername()) || StrUtil.isBlank(loginDTO.getPassword())) {
            throw new BusinessException("用户名和密码不能为空");
        }

        // 根据DTO中的用户名进行数据库查询操作,并且通过VO字段拿到对象
        LoginVO loginVO = this.userMapper.loginByUserName(loginDTO.getUsername());

        // 做安全性校验,看看有没有查到数据
        if (loginVO == null) {
            throw new BusinessException("用户名错误,请重新输入");
        }

        // 校验密码,VO中数据库中的密码和传过来的密码加密做校验
        String md5Hex = DigestUtil.md5Hex(loginDTO.getPassword() + loginVO.getSalt());
        if(!StrUtil.equals(md5Hex,loginVO.getPassword())){
            // 如果这两个密码不相等,则代表密码错误
            throw new BusinessException("密码错误,请重新输入");
        }

        // 生成jwt令牌作为token发给前端
        // 用用户id和用户名作为载荷
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("id",loginVO.getPassword());
        hashMap.put("username",loginVO.getUsername());

        String token = JwtUtil.generateToken(hashMap);

        // 设置vo的token字段
        loginVO.setToken(token);

        return loginVO;
    }
}
