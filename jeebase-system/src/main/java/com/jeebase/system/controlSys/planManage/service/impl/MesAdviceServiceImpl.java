package com.jeebase.system.controlSys.planManage.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeebase.system.controlSys.planManage.entity.MesAdviceEntity;
import com.jeebase.system.controlSys.planManage.mapper.IMesAdviceMapper;
import com.jeebase.system.controlSys.planManage.service.IMesAdviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MesAdviceServiceImpl extends ServiceImpl<IMesAdviceMapper, MesAdviceEntity> implements IMesAdviceService {

    @Autowired
    private IMesAdviceMapper mesAdviceMapper;


    @Override
    public boolean saveAndCreate(MesAdviceEntity mesAdviceEntity){
//        mesAdviceMapper
        return true;
    }

    @Override
    public Page<MesAdviceEntity> selectList(Page<MesAdviceEntity> page, MesAdviceEntity mesAdviceEntity) {
        LambdaQueryWrapper<MesAdviceEntity> lambda = new QueryWrapper<MesAdviceEntity>().lambda();
        lambda.eq(mesAdviceEntity.getState() != null, MesAdviceEntity::getState, mesAdviceEntity.getState());

        IPage<MesAdviceEntity> mesAdviceEntityIPage = mesAdviceMapper.selectPage(page, lambda);
        return  (Page<MesAdviceEntity>)mesAdviceEntityIPage;
    }

}
