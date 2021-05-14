package com.jeebase.system.controlSys.reportAction.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeebase.system.controlSys.reportAction.entity.RollerActionEntity;
import com.jeebase.system.controlSys.reportAction.entity.RollerActionEntity;
import com.jeebase.system.controlSys.reportAction.mapper.IRollerActionMapper;
import com.jeebase.system.controlSys.reportAction.service.IRollerActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RollerActionServiceImpl extends ServiceImpl<IRollerActionMapper, RollerActionEntity> implements IRollerActionService {

    @Autowired
    private IRollerActionMapper rollerActionMapper;
    @Override
    public Page<RollerActionEntity> selectList(Page<RollerActionEntity> page, RollerActionEntity rollerActionEntity) {
        LambdaQueryWrapper<RollerActionEntity> lambda = new QueryWrapper<RollerActionEntity>().lambda();
        lambda.eq(rollerActionEntity.getResult() != null, RollerActionEntity::getResult, rollerActionEntity.getResult());

        IPage<RollerActionEntity> mesAdviceEntityIPage = rollerActionMapper.selectPage(page, lambda);
        return  (Page<RollerActionEntity>)mesAdviceEntityIPage;
    }
    //确认

    //执行

    //报工

    //plc



    public void doTask(){
        //执行辊道输送任务


    }

    public void doTaskCallBack(){
        //执行辊道输送任务


    }


}
