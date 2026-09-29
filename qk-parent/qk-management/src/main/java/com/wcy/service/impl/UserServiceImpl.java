package com.wcy.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestUtil;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.annotation.WcyLogger;
import com.wcy.common.PageResponse;
import com.wcy.dto.UserQueryDTO;
import com.wcy.entity.User;
import com.wcy.exception.BusinessException;
import com.wcy.exception.DataNotFoundException;
import com.wcy.exception.ParamsException;
import com.wcy.mapper.UserMapper;
import com.wcy.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    // 注入Mapper依赖
    private final UserMapper userMapper;

    /**
     * 查询用户列表,分页版
     * 并实现了多表联查自定义sql
     * @param userDto 通过Dto定义的请求体模型
     * @return 返回我们定义的分页响应对象
     */
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

    /**
     * 新增用户,需要对密码做md5加密,还有加盐混淆
     * @param user 用户请求体
     */
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

    /**
     * 更新用户信息,暂时不涉及密码
     * @param user 用户请求体
     */
    @Override
    public void updateUserInfo(User user) {
        log.info("更新用户信息, id={}", user.getId());

        if (user.getId() == null) {
            throw new ParamsException("用户id不能为空");
        }

        User existing = this.getById(user.getId());
        if (existing == null) {
            throw new DataNotFoundException("用户不存在，id=" + user.getId());
        }

        // 构造干净对象，只 set 允许更新的字段
        User update = new User();
        update.setId(user.getId());
        update.setUsername(user.getUsername());
        update.setName(user.getName());
        update.setPhone(user.getPhone());
        update.setEmail(user.getEmail());
        update.setGender(user.getGender());
        update.setStatus(user.getStatus());
        update.setDeptId(user.getDeptId());
        update.setRoleId(user.getRoleId());
        update.setImage(user.getImage());
        update.setRemark(user.getRemark());
        // 不 set password / createTime / updateTime

        int rows = this.userMapper.updateById(update);
        if (rows == 0) {
            throw new BusinessException("更新用户失败，请重试");
        }
    }

    /**
     * 根据相应的role角色,查询对应的角色列表
     * 涉及多表,我们需要写自定义sql到xml中
     * @param roleLabel role别名 示例:admin
     * @return 角色列表
     */
    @Override
    public List<User> selectUserInfoByRoleLabel(String roleLabel) {
        log.info("根据角色 label 查询用户列表, roleLabel={}", roleLabel);

        // 1. 参数校验
        if (StrUtil.isBlank(roleLabel)) {
            throw new ParamsException("角色 label 不能为空");
        }

        // 2. 调用 Mapper 自定义方法
        List<User> userList = this.userMapper.selectUserInfoByRoleLabel(roleLabel);

        // 3. 空列表处理
        if (CollUtil.isEmpty(userList)) {
            throw new DataNotFoundException("角色 label=" + roleLabel + " 下没有用户");
        }

        return userList;
    }

    /**
     * 根据部门id查询用户列表
     * @param deptId 部门id
     * @return 用户列表,感觉不需要多表查询
     */
    @Override
    public List<User> selectUserInfoByDeptId(Integer deptId) {
        log.info("根据部门 id 查询用户列表, deptId={}", deptId);

        // 1. 参数校验
        if (deptId == null) {
            throw new ParamsException("部门id不能为空");
        }

        // 2. 用内置的 list + LambdaQueryWrapper 查
        List<User> userList = this.list(
                Wrappers.lambdaQuery(User.class)
                        .eq(User::getDeptId, deptId)
                        .orderByDesc(User::getCreateTime)
        );

        // 3. 空结果处理
        if (CollUtil.isEmpty(userList)) {
            throw new DataNotFoundException("部门 id=" + deptId + " 下没有用户");
        }

        return userList;
    }
}
