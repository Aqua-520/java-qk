package com.wcy.controller;

import com.wcy.common.PageResponse;
import com.wcy.common.Response;
import com.wcy.dto.UserQueryDTO;
import com.wcy.entity.User;
import com.wcy.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
@Slf4j
public class UserController {
    // 业务层对象
    private final UserService userService;

    // 分页查询
    @GetMapping
    public Response getUserListByLimit(UserQueryDTO userDto){
        // 调用业务层拿到分页对象
        PageResponse<User> userList = this.userService.getUserListByLimit(userDto);

        return Response.success(userList);
    }

    // 新增用户
    @PostMapping
    public Response addUserInfo(@RequestBody User user){
        // 接收前端请求体传给业务层做处理
        this.userService.addUserInfo(user);

        return Response.success();
    }

    // 删除用户,支持批量
    @DeleteMapping("/{ids}")
    public Response deleteUserInfo(@PathVariable("ids") List<Integer> userIdList){
        // 删除用户日志
        log.info("删除用户,用户ID是:{}",userIdList);
        // 删除,这里是继承下来的自带方法
        boolean b = this.userService.removeBatchByIds(userIdList);

        return b ? Response.success() : Response.error("删除用户失败");
    }

    // 根据id查询单条用户详情
    @GetMapping("/{id}")
    public Response selectUserInfoById(@PathVariable("id") Integer userId){
        // 查询单条用户
        log.info("查询用户详情,id:{}",userId);

        // 将id传给业务层,返回对象
        // 这里是框架继承下来的方法
        User user = this.userService.getById(userId);

        return user == null ? Response.error("用户不存在，id=" + userId) : Response.success(user);
    }

    // 更新用户
    @PutMapping
    public Response updateUserInfoById(@RequestBody User user){
        // 使用自定义方法做更新,对属性做校验
        this.userService.updateUserInfo(user);

        return Response.success();
    }

    // 查询全部用户列表
    @GetMapping("/list")
    public Response selectAllUserList(){
        log.info("查询全部用户列表");
        List<User> userList = this.userService.list();

        return Response.success(userList);
    }

    // 根据角色查询,涉及到多表操作
    @GetMapping("/role/{roleLabel}")
    public Response selectUserInfoByRoleLabel(@PathVariable String roleLabel){
        // 将角色名称传递给业务层,多表查询id,然后通过id进行回显对应用户
        List<User> userList = this.userService.selectUserInfoByRoleLabel(roleLabel);

        return Response.success(userList);
    }

    // 根据部门查询,涉及多表操作
    @GetMapping("/dept/{deptId}")
    public Response selectUserInfoByDeptId(@PathVariable Integer deptId){
        // 将标传递给业务层
        List<User> userList = this.userService.selectUserInfoByDeptId(deptId);

        return Response.success(userList);
    }
}
