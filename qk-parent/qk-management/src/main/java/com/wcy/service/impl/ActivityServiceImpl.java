package com.wcy.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wcy.common.PageResponse;
import com.wcy.dto.ActivityQueryDTO;
import com.wcy.entity.Activity;
import com.wcy.entity.Role;
import com.wcy.mapper.ActivityMapper;
import com.wcy.mapper.RoleMapper;
import com.wcy.service.ActivityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActivityServiceImpl extends ServiceImpl<ActivityMapper, Activity> implements ActivityService {
    // 注入Mapper依赖
    private final ActivityMapper activityMapper;

    @Override
    public List<Activity> getAllActivities() {
        log.info("查询活动列表,全部");
        // 查询数据列表并返回
        List<Activity> activities = this.activityMapper.selectList(Wrappers.emptyWrapper());

        return activities;
    }

    /**
     * 查询列表,分页版
     * @param queryDTO dto接收请求参数
     * @return 返回活动列表
     */
    @Override
    public PageResponse<Activity> getActivitiesByLimit(ActivityQueryDTO queryDTO) {
        // 将状态摘出来,因为status不是数据库字段,需要根据时间计算是否为生效期返回给前端
        LocalDateTime now = LocalDateTime.now();
        Integer status = queryDTO.getStatus();
        // 构建查询条件
        LambdaQueryWrapper<Activity> activityLambdaQueryWrapper = Wrappers.lambdaQuery(Activity.class)
                .eq(queryDTO.getChannel() != null, Activity::getChannel, queryDTO.getChannel())
                .eq(queryDTO.getType() != null, Activity::getType, queryDTO.getType())
                // status = 1 未开始
                .gt(status != null && status == 1, Activity::getStartTime, now)
                // status = 2 进行中
                .le(status != null && status == 2, Activity::getStartTime, now)
                .ge(status != null && status == 2, Activity::getEndTime, now)
                // status = 3 已结束
                .lt(status != null && status == 3, Activity::getEndTime, now)
                .orderByDesc(Activity::getCreateTime);

        // 调用分页查询
        Page<Activity> activityPage = this.activityMapper.selectPage(new Page<>(queryDTO.getPage(), queryDTO.getPageSize()), activityLambdaQueryWrapper);

        // 包装分页对象返回
        // 构造 PageResponse，参数顺序按字段声明来：total, rows
        PageResponse<Activity> response = new PageResponse<>();
        response.setTotal(activityPage.getTotal());
        response.setRows(activityPage.getRecords());

        return response;
    }
}
