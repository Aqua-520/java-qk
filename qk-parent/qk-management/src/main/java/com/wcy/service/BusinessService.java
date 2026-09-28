package com.wcy.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wcy.common.PageResponse;
import com.wcy.dto.BusinessAddDTO;
import com.wcy.dto.BusinessPoolQueryDTO;
import com.wcy.dto.BusinessQueryDTO;
import com.wcy.dto.BusinessTrackDTO;
import com.wcy.entity.Business;
import com.wcy.vo.BusinessPoolVO;
import com.wcy.vo.BusinessVO;

// 继承Iservice,拿到一些单表操作的快捷方法
public interface BusinessService extends IService<Business> {
    PageResponse<Business> selectBusinessListByLimit(BusinessQueryDTO businessQueryDTO);

    void addBusiness(BusinessAddDTO businessAddDTO);

    void assignBusiness(Integer businessId, Integer userId);

    void backBusiness(Integer businessId);

    void toCustomer(Integer businessId);

    BusinessVO selectBusinessById(Integer businessId);

    void assignBusiness(BusinessTrackDTO businessTrackDTO);

    PageResponse<BusinessPoolVO> selectPool(BusinessPoolQueryDTO poolQueryDTO);
}
