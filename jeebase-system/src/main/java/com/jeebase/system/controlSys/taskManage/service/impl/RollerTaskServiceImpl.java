package com.jeebase.system.controlSys.taskManage.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeebase.common.base.BusinessException;
import com.jeebase.system.controlSys.api.TableRollerApi;
import com.jeebase.system.controlSys.planManage.entity.MesDoPlanEntity;
import com.jeebase.system.controlSys.planManage.mapper.IMesDoPlanMapper;
import com.jeebase.system.controlSys.planManage.service.IMesDoPlanService;
import com.jeebase.system.controlSys.taskManage.service.IFjTaskService;
import com.jeebase.system.controlSys.taskManage.service.IRollerTaskService;
import com.jeebase.system.controlSys.taskManage.service.IWmsTaskService;
import org.apache.tools.ant.taskdefs.Sleep;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 该类为流程控制核心类
 */
@Service
public class RollerTaskServiceImpl implements IRollerTaskService {

    @Autowired
    private TableRollerApi rollerApi;

    /**
     * 查询到达指定点位是否可执行
     */
    @Override
    public boolean getPlcState(String place){
        //若当前辊道是停止状态并且是空闲时则可以下发输送至此的命令
        if ("0".equals(rollerApi.read(place+"_status")) && "0".equals(rollerApi.read(place+"_place"))){
            return true;
        }
        return false;
    }

    /**
     * 下发输送指令，从目标点位到指定点位
     */
    @Override
    public boolean goPlcPlace(String from,String to){

        if (getPlcState(to)){//验证目标位置是否可输送，若可输送，则下达输送指令、、简单验证需修改
            rollerApi.write(from+"_target",to);
            return true;
        }
        return false;
    }


//    public boolean go

}
