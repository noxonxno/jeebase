package com.jeebase.system.controlSys.reportAction.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeebase.system.controlSys.reportAction.entity.CutActionEntity;
import com.jeebase.system.controlSys.reportAction.mapper.ICutActionMapper;
import com.jeebase.system.controlSys.reportAction.service.ICutActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CutActionServiceImpl extends ServiceImpl<ICutActionMapper, CutActionEntity> implements ICutActionService {
    @Autowired
    private ICutActionMapper cutActionMapper;

    @Override
    public Page<CutActionEntity> selectList(Page<CutActionEntity> page, CutActionEntity cutActionEntity) {
        LambdaQueryWrapper<CutActionEntity> lambda = new QueryWrapper<CutActionEntity>().lambda();
        lambda.eq(cutActionEntity.getResult() != null, CutActionEntity::getResult, cutActionEntity.getResult());

        IPage<CutActionEntity> mesAdviceEntityIPage = cutActionMapper.selectPage(page, lambda);
        return  (Page<CutActionEntity>)mesAdviceEntityIPage;
    }
}
