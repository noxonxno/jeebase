package com.jeebase.system.controlSys.taskManage;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jeebase.common.base.BusinessException;
import com.jeebase.system.controlSys.planManage.entity.MesDoPlanEntity;
import com.jeebase.system.controlSys.planManage.service.IMesDoPlanService;
import com.jeebase.system.controlSys.taskManage.service.ICutTaskService;
import com.jeebase.system.controlSys.taskManage.service.IFjTaskService;
import com.jeebase.system.controlSys.taskManage.service.IRollerTaskService;
import com.jeebase.system.controlSys.taskManage.service.IWmsTaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


public class TaskExecutionCenter {

//    @Autowired
    private IMesDoPlanService mesDoPlanService;

//    @Autowired
    private IWmsTaskService wmsTaskService;

//    @Autowired
    private IFjTaskService  fjTaskService;

//    @Autowired
    private ICutTaskService cutTaskService;

//    @Autowired
    private IRollerTaskService rollerTaskService;


    private static Thread thread = null;

    private static boolean run = false;//流水线循环控制变量

    private static int sleepTime = 1000;//循环检测频率（单位毫秒）


    /**
     * 启动接口
     */
    @PostMapping("")
    public void startExecution(){
        try {


            this.run = true;
            this.execution();

        }catch (Exception e){

        }
    }


    /**
     * 关闭接口
     */
    @PostMapping("")
    public void stopExecution(){

        try {

            this.run = false;

        }catch (Exception e){

        }
    }


    private void execution() throws InterruptedException {
        //流程启动器
        while (run){
            //查询上料托盘是否就绪
                if (rollerTaskService.initFinish()){
                //若就绪则查询是否有需要开始的计划，并查出排序字段最高的计划
                LambdaQueryWrapper<MesDoPlanEntity> mesDoPlanEntityLambdaQueryWrapper = new QueryWrapper<MesDoPlanEntity>().lambda()
                        .eq(MesDoPlanEntity::getPlanState, 5)
                        .eq(MesDoPlanEntity::getTaskType, 0)
                        .orderByDesc(MesDoPlanEntity::getSort);
                MesDoPlanEntity doPlanEntity = mesDoPlanService.getOne(mesDoPlanEntityLambdaQueryWrapper);

                if (doPlanEntity != null){//若有则开始执行计划
                    //下发上料任务
                    wmsTaskService.doTask(doPlanEntity.getPlanCode(),1);
                }
            }

            //查询所有等待下一步执行的主计划
            LambdaQueryWrapper<MesDoPlanEntity> mesDoPlanEntityLambdaQueryWrapper = new QueryWrapper<MesDoPlanEntity>().lambda()
                    .eq(MesDoPlanEntity::getPlanState,"4")
                    .eq(MesDoPlanEntity::getTaskType, "")
                    .or().eq(MesDoPlanEntity::getTaskType, "")
                    .or().eq(MesDoPlanEntity::getTaskType, "")
                    .or().eq(MesDoPlanEntity::getTaskType, "")
                    .or().eq(MesDoPlanEntity::getTaskType, "")
                    .or().eq(MesDoPlanEntity::getTaskType, "")
                    .or().eq(MesDoPlanEntity::getTaskType, "");
            List<MesDoPlanEntity> mesDoPlanEntityList = mesDoPlanService.list(mesDoPlanEntityLambdaQueryWrapper);

            //如果存在待下发操作的计划，则对计划下发下一步操作指令
            if (mesDoPlanEntityList.size()>0){
                for (MesDoPlanEntity mesDoPlanEntity : mesDoPlanEntityList) {
                    toNext(mesDoPlanEntity);
                }
            }
            //设置间隔时间
            Thread.sleep(sleepTime);
        }
    }


    /**
     *  根据传入计划判断下一步操作并调用
     *
     */
    private void toNext(MesDoPlanEntity doPlanEntity){

        //判断当前任务环节与所在plc点位是否符合


        //根据任务环节执行下一步对应操作
        switch (doPlanEntity.getTaskType()){
            case    3   : //下料任务执行完成
                //验证rfid是否写入成功
                if ("1".equals(rollerTaskService.read("g_rfid_status"))){
                    //若成功则下发喷码任务
                    fjTaskService.doTask(doPlanEntity.getId(),1);
                }else {
                    //若rfid写入失败则更新计划状态

                }
                break;
            case    6   : //喷码完成
                //根据切割类型查询对应切割缓存切割缓存位是否有空闲，并下发输送任务
                if (doPlanEntity.getCutType() == 0){//等离子切割机，对应缓存为G5，G1
                    if (rollerTaskService.goPlcPlace("g","g1")){rollerTaskService.goPlcPlace("g","g5");};
                }else if (doPlanEntity.getCutType() == 1){//激光切割机，对应缓存为G9
                    rollerTaskService.goPlcPlace("g","g9");
                }
                break;
            case    8   : //切割缓存位输送到位
                //根据切割查询当前计划对应切割机是否有空闲
                rollerTaskService.fromBufferToCut(doPlanEntity.getPlace());
                break;
            case    10  : //切割位输送到位
                //下法托盘举升指令
                rollerTaskService.riseUp(doPlanEntity.getPlace());
                break;
            case    12  : //托盘举升到位
                //下发切割任务
                cutTaskService.doTask(doPlanEntity.getPlanCode());
                break;
            case    14  : //切割完成
                //下发托盘下放指令
                rollerTaskService.riseDown(doPlanEntity.getPlace());
                break;
            case    16  : //托盘下放到位
                //下发从切割位至切割缓存位指令
                rollerTaskService.fromCutToBuffer(doPlanEntity.getPlace());
                break;
            case    18  : //分拣缓存位输送到位
                //查看分拣位是否空闲
                rollerTaskService.goPlcPlace(doPlanEntity.getPlace(),"g13");
                break;
            case    20  : //小件分拣位输送到位
                //下发小件分拣任务
                    fjTaskService.doTask(doPlanEntity.getPlanCode(),2);
                break;
            case    22  : //小件分拣任务完成
                //输送至大件分拣位
                rollerTaskService.goPlcPlace(doPlanEntity.getPlace(),"g14");
                break;
            case    24  : //大件分拣位输送到位
                    //下发大件分拣任务
                    fjTaskService.doTask(doPlanEntity.getPlanCode(),3);
                break;
            default:
                //无对应环节，不做任何操作
                break;
        }
    }
}
