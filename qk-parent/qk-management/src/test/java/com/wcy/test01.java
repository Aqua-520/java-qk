package com.wcy;

import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.wcy.entity.User;
import com.wcy.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class test01 {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void jiemi(){
        String username = "jinniuyuan";
        String password = "jinniuyuan123";

        // 查询出对应的数据库对象
        User user = userMapper.selectOne(Wrappers.lambdaQuery(User.class).eq(User::getUsername, username));

        // 对密码做校验
        String md5Hex = DigestUtil.md5Hex(password + user.getSalt());

        if (md5Hex.equals(user.getPassword())){
            System.out.println("密码正确");
        }else {
            System.out.println("密码错误");
        }
    }
}
