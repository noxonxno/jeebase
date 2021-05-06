package com.jeebase.system.controlSys.reportAction.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeebase.system.controlSys.reportAction.entity.FjActionEntity;
import com.jeebase.system.controlSys.reportAction.mapper.IFjActionMapper;
import com.jeebase.system.controlSys.reportAction.service.IFjActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FjActionServiceImpl extends ServiceImpl<IFjActionMapper, FjActionEntity> implements IFjActionService {

    @Autowired
    private IFjActionMapper fjActionMapper;

    @Override
    public Page<FjActionEntity> selectList(Page<FjActionEntity> page, FjActionEntity fjActionEntity) {
        LambdaQueryWrapper<FjActionEntity> lambda = new QueryWrapper<FjActionEntity>().lambda();
        lambda.eq(fjActionEntity.getResult() != null, FjActionEntity::getResult, fjActionEntity.getResult());

        IPage<FjActionEntity> mesAdviceEntityIPage = fjActionMapper.selectPage(page, lambda);
        return  (Page<FjActionEntity>)mesAdviceEntityIPage;
    }
}
