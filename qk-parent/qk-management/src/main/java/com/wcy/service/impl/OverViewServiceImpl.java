package com.wcy.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.wcy.mapper.BusinessMapper;
import com.wcy.mapper.ClueMapper;
import com.wcy.service.OverViewService;
import com.wcy.vo.OverviewVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OverViewServiceImpl implements OverViewService {
    // 导入线索和商机的Mapper
    private final ClueMapper clueMapper;
    private final BusinessMapper businessMapper;

    @Override
    public OverviewVO getHomeData() {
        // 通过两条自定义sql来查询对应的统计字段
        OverviewVO clueCount = this.clueMapper.getClueCount();
        OverviewVO businessCount = this.businessMapper.businessCount();

        // 合并两个对象的查询结果
        BeanUtil.copyProperties(clueCount,businessCount, CopyOptions.create().ignoreNullValue());

        return businessCount;
    }
}
