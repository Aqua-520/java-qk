package com.wcy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wcy.common.PageResponse;
import com.wcy.dto.ActivityQueryDTO;
import com.wcy.entity.Activity;

import java.util.List;

// 继承一下IServer简化一些增删改查
public interface ActivityService extends IService<Activity> {
    List<Activity> getAllActivities();

    PageResponse<Activity> getActivitiesByLimit(ActivityQueryDTO queryDTO);
}
