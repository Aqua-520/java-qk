package com.wcy.controller;

import com.wcy.common.Response;
import com.wcy.service.OverViewService;
import com.wcy.vo.OverviewVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// 回显首页数据展示的接口
@RestController
@RequiredArgsConstructor
public class OverViewController {
    private final OverViewService overViewService;

    // 回显接口
    @GetMapping("/report/overview")
    public Response getHomeData(){
        OverviewVO overviewVO = this.overViewService.getHomeData();

        return Response.success(overviewVO);
    }
}
