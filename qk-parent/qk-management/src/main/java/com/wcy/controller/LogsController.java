package com.wcy.controller;

import com.wcy.common.PageResponse;
import com.wcy.common.Response;
import com.wcy.entity.OperateLog;
import com.wcy.service.LogsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LogsController {
    private final LogsService logsService;

    // 日志列表回显
    @GetMapping("/logs")
    public Response selectLogsByLimit(
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer pageSize) {
        // 调用 service 查询
        PageResponse<OperateLog> logPageResponse = this.logsService.selectLogsByLimit(page,pageSize);
        return Response.success(logPageResponse);
    }
}
