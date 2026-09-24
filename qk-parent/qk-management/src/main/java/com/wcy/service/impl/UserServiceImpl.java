package com.wcy.service.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.common.PageResponse;
import com.wcy.dto.UserQueryDTO;
import com.wcy.entity.User;
import com.wcy.mapper.UserMapper;
import com.wcy.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    // 注入Mapper依赖
    private final UserMapper userMapper;
    @Override
    public PageResponse<User> getUserListByLimit(UserQueryDTO userDto) {
        // 分页查询,创建分页对象
        // 通过自定义分页方法
        Page<User> userListByLimit = this.userMapper.getUserListByLimit(new Page<>(userDto.getPage(), userDto.getPageSize()), userDto);

        return new PageResponse<>(
                userListByLimit.getTotal(),
                userListByLimit.getRecords()
        );
    }

    @Override
    public void addUserInfo(User user) {
        // 新增用户的时候没有传密码,设置初始密码,然后进行加密存储到数据库
        // 获取盐值对密码做混淆
        String randomString = RandomUtil.randomString(10);
        user.setPassword(DigestUtil.md5Hex(user.getUsername() + "123" + randomString));

        // 将盐保存到字段中
        user.setSalt(randomString);

        // 保存用户
        this.userMapper.insert(user);
    }
}
