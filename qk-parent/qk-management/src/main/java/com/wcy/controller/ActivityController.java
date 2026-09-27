package com.wcy.controller;

import com.wcy.common.PageResponse;
import com.wcy.common.Response;
import com.wcy.dto.ActivityQueryDTO;
import com.wcy.entity.Activity;
import com.wcy.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/activities")
public class ActivityController {
    // 注入业务层对象
    private final ActivityService activityService;

    // 查询所有列表
    @GetMapping("/list")
    public Response getAllActivities(){
        // 无参数,直接调用业务层方法拿到列表返回
        List<Activity> activities =  this.activityService.getAllActivities();

        return Response.success(activities);
    }

    // 查询列表,分页版
    @GetMapping
    public Response getActivitiesByLimit(ActivityQueryDTO queryDTO){
        // 通过dto对象封装查询参数
        PageResponse<Activity> activities = this.activityService.getActivitiesByLimit(queryDTO);

        return Response.success(activities);
    }

    // 修改活动
    @PutMapping
    public Response updateActivity(@RequestBody Activity activity){
        // 定义请求体接收前端json并包装成对象

        boolean result = this.activityService.updateById(activity);
        return result ? Response.success() : Response.error("活动更新失败");
    }

    // 添加活动
    @PostMapping
    public Response addActivity(@RequestBody Activity activity){
        // 定义请求体接收前端json并包装成对象
        // 返回布尔值
        boolean result = this.activityService.save(activity);
        return result ? Response.success() : Response.error("新增活动失败");
    }

    // 根据id查询活动
    @GetMapping("/{id}")
    public Response selectActivityById(@PathVariable("id") Integer activityId){
        // 这里是框架内置的
        Activity activity = this.activityService.getById(activityId);
        return activity == null ? Response.error("角色id不存在") : Response.success(activity);
    }

    // 删除活动
    @DeleteMapping("/{id}")
    public Response deleteActivityById(@PathVariable("id") Integer activityId){
        // 这里是框架内置的
        // 我们还是切回自己写的
        boolean result = this.activityService.removeById(activityId);

        return result ? Response.success() : Response.error("删除失败");
    }

    // 根据活动频道筛选活动,线下或者线上
    @GetMapping("/type/{type}")
    public Response selectActivityByChannel(@PathVariable Integer type) {
        List<Activity> activityList = activityService.selectActivityByChannel(type);
        return Response.success(activityList);
    }
}
