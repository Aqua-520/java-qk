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
        // 删除
        this.userService.removeBatchByIds(userIdList);

        return Response.success();
    }
}
