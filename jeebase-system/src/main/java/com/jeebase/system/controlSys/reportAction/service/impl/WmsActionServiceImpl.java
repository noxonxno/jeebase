package com.jeebase.system.controlSys.reportAction.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeebase.system.controlSys.planManage.entity.MesAdviceEntity;
import com.jeebase.system.controlSys.reportAction.entity.WmsActionEntity;
import com.jeebase.system.controlSys.reportAction.mapper.IWmsActionMapper;
import com.jeebase.system.controlSys.reportAction.service.IWmsActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WmsActionServiceImpl extends ServiceImpl<IWmsActionMapper, WmsActionEntity> implements IWmsActionService {
    @Autowired
    private IWmsActionMapper wmsActionMapper;

    @Override
    public Page<WmsActionEntity> selectList(Page<WmsActionEntity> page, WmsActionEntity wmsActionEntity) {
        LambdaQueryWrapper<WmsActionEntity> lambda = new QueryWrapper<WmsActionEntity>().lambda();
        lambda.eq(wmsActionEntity.getResult() != null, WmsActionEntity::getResult, wmsActionEntity.getResult());

        IPage<WmsActionEntity> mesAdviceEntityIPage = wmsActionMapper.selectPage(page, lambda);
        return  (Page<WmsActionEntity>)mesAdviceEntityIPage;
    }
}
