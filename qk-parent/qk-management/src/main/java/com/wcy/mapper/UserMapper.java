package com.wcy.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wcy.dto.UserQueryDTO;
import com.wcy.entity.User;
import com.wcy.vo.LoginVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    // 编写自定义抽象方法,去xml实现sql查询
    Page<User> getUserListByLimit(IPage<User> page, UserQueryDTO userDto);

    // 添加注解,也可以不加,在xml中拼写多表sql
    List<User> selectUserInfoByRoleLabel(@Param("roleLabel") String roleLabel);

    // 通过xml实现
    LoginVO loginByUserName(String username);
}
