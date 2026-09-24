package com.wcy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wcy.dto.UserQueryDTO;
import com.wcy.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    // 编写自定义抽象方法,去xml实现sql查询
    Page<User> getUserListByLimit(IPage<User> page, UserQueryDTO userDto);
}
