package com.jeebase.system.controlSys.planManage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeebase.system.controlSys.planManage.entity.MesDoPlanEntity;
import com.jeebase.system.controlSys.planManage.mapper.IMesDoPlanMapper;
import com.jeebase.system.controlSys.planManage.service.IMesDoPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MesDoPlanServiceImpl  extends ServiceImpl<IMesDoPlanMapper, MesDoPlanEntity> implements IMesDoPlanService {

    @Autowired
    private IMesDoPlanMapper mesDoPlanMapper;


    @Override
    public Page<MesDoPlanEntity> selectList(Page<MesDoPlanEntity> page,MesDoPlanEntity mesDoPlanEntity) {
        LambdaQueryWrapper<MesDoPlanEntity> lambda = new QueryWrapper<MesDoPlanEntity>().lambda();
        lambda.eq(mesDoPlanEntity.getMesAdviceId() != null, MesDoPlanEntity::getMesAdviceId, mesDoPlanEntity.getMesAdviceId());
        IPage<MesDoPlanEntity> mesDoPlanEntityIPage = mesDoPlanMapper.selectPage(page, lambda);
        return (Page<MesDoPlanEntity>) mesDoPlanEntityIPage;
    }

    @Override
    public boolean saveAndUpdateByTask() {

        return false;
    }

}
