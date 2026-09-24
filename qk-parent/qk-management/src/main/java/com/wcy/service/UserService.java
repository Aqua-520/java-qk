package com.wcy.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wcy.common.PageResponse;
import com.wcy.dto.UserQueryDTO;
import com.wcy.entity.User;

public interface UserService extends IService<User> {
    PageResponse<User> getUserListByLimit(UserQueryDTO userDto);

    void addUserInfo(User user);
}
