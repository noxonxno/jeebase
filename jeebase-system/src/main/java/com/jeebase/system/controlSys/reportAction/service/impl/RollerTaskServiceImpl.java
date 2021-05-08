package com.jeebase.system.controlSys.reportAction.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeebase.system.controlSys.reportAction.entity.RollerActionEntity;
import com.jeebase.system.controlSys.reportAction.mapper.IRollerActionMapper;
import com.jeebase.system.controlSys.reportAction.service.IRollerActionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RollerTaskServiceImpl extends ServiceImpl<IRollerActionMapper, RollerActionEntity> implements IRollerActionService {

    @Autowired
    private IRollerActionMapper rollerActionMapper;

    public void doTask(){
        //执行辊道输送任务


    }

    public void doTaskCallBack(){
        //执行辊道输送任务


    }


}
