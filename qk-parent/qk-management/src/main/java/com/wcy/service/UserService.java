package com.wcy.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.wcy.common.PageResponse;
import com.wcy.dto.UserQueryDTO;
import com.wcy.entity.User;

import java.util.List;

public interface UserService extends IService<User> {
    PageResponse<User> getUserListByLimit(UserQueryDTO userDto);

    void addUserInfo(User user);

    void updateUserInfo(User user);

    List<User> selectUserInfoByRoleLabel(String roleLabel);

    List<User> selectUserInfoByDeptId(Integer deptId);
}
