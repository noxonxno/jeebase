package com.jeebase.system.controlSys.planManage.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeebase.system.controlSys.planManage.entity.MesAdviceEntity;
import com.jeebase.system.controlSys.planManage.entity.MesPlanEntity;
import com.jeebase.system.controlSys.planManage.mapper.IMesAdviceMapper;
import com.jeebase.system.controlSys.planManage.mapper.IMesPlanMapper;
import com.jeebase.system.controlSys.planManage.service.IMesPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MesPlanServiceImpl extends ServiceImpl<IMesPlanMapper, MesPlanEntity> implements IMesPlanService {

    @Autowired
    private IMesPlanMapper mesPlanMapper;

    @Override
    public Page<MesPlanEntity> selectList(Page<MesPlanEntity> page ,MesPlanEntity mesPlanEntity) {
        LambdaQueryWrapper<MesPlanEntity> lambda = new QueryWrapper<MesPlanEntity>().lambda();
        lambda.eq(mesPlanEntity.getMesAdviceId() != null, MesPlanEntity::getMesAdviceId, mesPlanEntity.getMesAdviceId());
        IPage<MesPlanEntity> mesPlanEntityIPage = mesPlanMapper.selectPage(page, lambda);
        return (Page<MesPlanEntity>) mesPlanEntityIPage;
    }
}
