package com.wcy.service;

import com.wcy.common.PageResponse;
import com.wcy.entity.OperateLog;

public interface LogsService {
    PageResponse<OperateLog> selectLogsByLimit(Integer page, Integer pageSize);
}
