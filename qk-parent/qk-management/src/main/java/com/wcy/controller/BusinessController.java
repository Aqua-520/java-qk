package com.wcy.controller;

import com.wcy.common.PageResponse;
import com.wcy.common.Response;
import com.wcy.dto.BusinessAddDTO;
import com.wcy.dto.BusinessQueryDTO;
import com.wcy.entity.Business;
import com.wcy.service.BusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

// 商机相关接口控制层类
@RestController
@RequiredArgsConstructor
public class BusinessController {
    // 注入业务层依赖
    private final BusinessService businessService;

    // 查询商机列表,分页版
    @GetMapping("/businesses")
    public Response selectBusinessListByLimit(BusinessQueryDTO businessQueryDTO){
        // 直接将查询参数对象传给业务层
        // 这里分页参数用实体类接收即可
        PageResponse<Business> businessPageResponse = this.businessService.selectBusinessListByLimit(businessQueryDTO);

        return Response.success(businessPageResponse);
    }

    // 添加商机
    @PostMapping("/businesses")
    public Response addBusiness(@RequestBody BusinessAddDTO businessAddDTO){
        // 将dto传给业务层
        this.businessService.addBusiness(businessAddDTO);

        return Response.success();
    }

    // 分配商机给指定的老师处理
    @PutMapping("/businesses/assign/{businessId}/{userId}")
    public Response assignBusiness(@PathVariable Integer businessId,@PathVariable Integer userId){
        // 接收到路径参数的商机id和用户id
        this.businessService.assignBusiness(businessId,userId);
        return Response.success();
    }
}
