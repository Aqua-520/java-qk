package com.wcy.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wcy.common.PageResponse;
import com.wcy.entity.OperateLog;
import com.wcy.mapper.LogsMapper;
import com.wcy.service.LogsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// 实现日志服务接口
@Service
@RequiredArgsConstructor
public class LogsServiceImpl implements LogsService {
    private final LogsMapper logsMapper;

    /**
     * 查询日志列表,分页
     * @param page 页码
     * @param pageSize 每页展示几条
     * @return 分页结果对象
     */
    @Override
    public PageResponse<OperateLog> selectLogsByLimit(Integer page, Integer pageSize) {
        Page<OperateLog> selectPage = this.logsMapper.selectLogPage(new Page<>(page, pageSize));

        // 创建分页结果对象
        PageResponse<OperateLog> pageResponse = new PageResponse<>();
        pageResponse.setTotal(selectPage.getTotal());
        pageResponse.setRows(selectPage.getRecords());

        return pageResponse;
    }
}
