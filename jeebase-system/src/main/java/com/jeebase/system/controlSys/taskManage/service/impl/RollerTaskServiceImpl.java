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
import org.springframework.util.StringUtils;

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
    public boolean initFinish(){
        //判断g点是否就绪
        if ("0".equals(rollerApi.read("g_status")) && "1".equals(rollerApi.read("g_place"))){
            return true;
        }
        return false;
    }

    /**
     * 查询到达指定点位是否可执行
     */
    @Override
    public boolean getPlcState(String place){
        //若当前辊道是停止状态并且是空闲时则可以下发输送至此的命令，用于 g点位到缓冲，切割完成缓冲位到分拣位
        if ("0".equals(rollerApi.read(place+"_status")) && "0".equals(rollerApi.read(place+"_place"))){
            return true;
        }
        return false;
    }


    /**
     * 下发输送指令，从目标点位到指定点位
     * 不包含切割机点位
     */
    @Override
    public boolean goPlcPlace(String from,String to){

        if (getPlcState(to)){
            rollerApi.write(from+"_target",to);
            return true;
        }
        return false;
    }


    /**
     * 根据所在缓冲位，输送至空闲可输送的切割机位
     */
    @Override
    public boolean fromBufferToCut(String bufferPlace){
        //对于g3,g7,g11需要对经过地点g2,g6,g10进行验证是否被阻挡

        return false;
    }


    @Override
    public boolean fromCutToBuffer(String cutPlace){
        //对于g2,g6,g10需要对经过地点g3,g7,g11进行验证是否被阻挡

        return false;
    }


    @Override
    public boolean riseUp(String plc){
        //验证当前任务是否与托盘id，rfid对应

        //判断当前点位托盘是否放下状态
        if ("0".equals(rollerApi.read(plc+"_jacks_status")) ) {
            rollerApi.write(plc+"_jacks_opt","1");
            return true;
        }
        return false;
    }


    @Override
    public boolean riseDown(String plc){
        //判断当前点位托盘是否举升状态，判断举升下方辊道是否空闲且处于停止状态
        if ("1".equals(rollerApi.read(plc+"_jacks_status")) && this.getPlcState(plc)) {
            rollerApi.write(plc+"_jacks_opt","0");
            return true;
        }
        return false;
    }


    @Override
    public boolean write(String plc,String value) {

        if (StringUtils.isEmpty(plc)&&StringUtils.isEmpty(value)){
            return false;
        }
        rollerApi.write(plc,value);
        return true;
    }

    @Override
    public String read(String plc) {
        return rollerApi.read(plc);
    }

}
