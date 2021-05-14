package com.jeebase.system.controlSys.taskManage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeebase.common.base.BusinessException;
import com.jeebase.system.controlSys.api.WMSApi;
import com.jeebase.system.controlSys.planManage.entity.MesDoPlanEntity;
import com.jeebase.system.controlSys.planManage.mapper.IMesDoPlanMapper;
import com.jeebase.system.controlSys.reportAction.entity.FjActionEntity;
import com.jeebase.system.controlSys.reportAction.entity.WmsActionEntity;
import com.jeebase.system.controlSys.reportAction.mapper.IWmsActionMapper;
import com.jeebase.system.controlSys.taskManage.entity.FjTaskEntity;
import com.jeebase.system.controlSys.taskManage.entity.WmsTaskEntity;
import com.jeebase.system.controlSys.taskManage.mapper.IWmsTaskMapper;
import com.jeebase.system.controlSys.taskManage.service.IFjTaskService;
import com.jeebase.system.controlSys.taskManage.service.IRollerTaskService;
import com.jeebase.system.controlSys.taskManage.service.IWmsTaskService;
import com.jeebase.system.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class WmsTaskServiceImpl extends ServiceImpl<IWmsTaskMapper, WmsTaskEntity> implements IWmsTaskService {

    @Autowired
    private IWmsTaskMapper wmsTaskMapper;

    @Autowired
    private IMesDoPlanMapper mesDoPlanMapper;

    @Autowired
    private IWmsActionMapper wmsActionMapper;

    @Autowired
    private IRollerTaskService rollerTaskService;

    @Override
    public Page<WmsTaskEntity> selectList(Page<WmsTaskEntity> page, WmsTaskEntity wmsTaskEntity) {

        LambdaQueryWrapper<WmsTaskEntity> lambda = new QueryWrapper<WmsTaskEntity>().lambda();

        IPage<WmsTaskEntity> cutTaskEntityIPage = wmsTaskMapper.selectPage(page, lambda);
        return  (Page<WmsTaskEntity>)cutTaskEntityIPage;
    }


    @Override
    @Transactional
    public boolean doTask(String planCode, int taskType) throws BusinessException {

        /** 参数，参数对应可执行任务，wms任务正确性验证 ***********************************************************************/

        MesDoPlanEntity doPlanEntity = this.mesDoPlanValidation(planCode, "5");
        WmsTaskEntity taskEntity = this.wmsTaskValidation(planCode, taskType, "5",null);

        /** 任务下发 ****************************************************************************************************/

        //判断当前任务执行环节上料or下料,构建请求参数
        if (taskType == 1){
            //执行上料任务

        }else if(taskType == 2) {//执行下料任务

        }
        //String s = wmsApi.doWmsPlan(new ArrayList<>());
        //若请求发送失败则抛出异常
        if (false){
            throw new BusinessException("任务下发请求发送失败");
        }


        /** 更新可执行任务，wms任务状态为执行中，可执行计划任务环节+1，创建对应操作报工***********************************************/
        //更改mes可执行任务状态
        MesDoPlanEntity mesDoPlanEntity = new MesDoPlanEntity();
        mesDoPlanEntity.setId(planCode);
        mesDoPlanEntity.setTaskType(doPlanEntity.getTaskType()+1);
        mesDoPlanEntity.setPlanState("4");
        mesDoPlanEntity.setUpdateTime(LocalDateTime.now());
        if (mesDoPlanMapper.updateById(mesDoPlanEntity) <= 0){//更新mes可执行计划,若失败抛出异常
            throw new BusinessException("可执行计划更新失败,可执行任务编号："+planCode);
        }
        //创建初始报工记录，并入库
        WmsActionEntity wmsActionEntity = new WmsActionEntity();
        wmsActionEntity.setId(UUIDUtils.getUUID32());
        wmsActionEntity.setPlanCode(planCode);
        wmsActionEntity.setSendTime(LocalDateTime.now());
        wmsActionEntity.setActionName("");
        wmsActionEntity.setSendLog("");
        //报工入库
        if (wmsActionMapper.insert(wmsActionEntity) <=0 ){
            throw new BusinessException("添加报工失败");
        }
        //更改任务执行状态
        WmsTaskEntity wmsTaskEntity = new WmsTaskEntity();
        wmsTaskEntity.setFplanState("4");//任务状态0取消，1成功，2失败，3未开始，4执行中
        wmsTaskEntity.setId(taskEntity.getId());
        if(wmsTaskMapper.updateById(wmsTaskEntity) <= 0){
            throw new BusinessException("wms任务更新失败：wms任务编号："+taskEntity.getId());
        }

        return true;
    }

    @Override
    @Transactional
    public boolean doTaskCallBack(WmsTaskEntity taskEntity){
        /** 参数，参数对应可执行任务，fj任务正确性验证************************************************************/
        //返回条件参数
        String planCode = taskEntity.getPlanCode();
        MesDoPlanEntity doPlanEntity = this.mesDoPlanValidation(planCode, "4");
        WmsActionEntity wmsActionEntity = this.wmsActionValidation(planCode);
        this.wmsTaskValidation(taskEntity,"4");

        /** 更新主计划任务状态，任务环节，fj任务状态，报工结果完善***************************************************************/
        //创建可执行计划更新数据
        MesDoPlanEntity mesDoPlanEntity = new MesDoPlanEntity();
        mesDoPlanEntity.setId(planCode);
        mesDoPlanEntity.setUpdateTime(LocalDateTime.now());

        //创建报工更新数据
        wmsActionEntity.setReportTime(LocalDateTime.now());
        wmsActionEntity.setReportLog("");
                //去除冗余更新字段减少更新消耗
        wmsActionEntity.setSendTime(null);
        wmsActionEntity.setPlanCode(null);
        wmsActionEntity.setActionName(null);
        wmsActionEntity.setSendLog(null);

        //当前任务失败对应或成功更新数据
        if ("2".equals(taskEntity.getFplanState())){
            mesDoPlanEntity.setPlanState("2");//可执行计划状态->失败
            wmsActionEntity.setResult("失败");//报工结果设置为失败
        }else {
            wmsActionEntity.setResult("成功");
            mesDoPlanEntity.setTaskType(doPlanEntity.getTaskType()+1);//任务环节加一
            if (taskEntity.getTaskType() == 0){//若成功且任务为上料任务则，写入g_plan
                rollerTaskService.write("g_plan",planCode);
            }
            if ("auto".equals(doPlanEntity.getExeModel())){
                mesDoPlanEntity.setPlanState("5");
            }else {
                mesDoPlanEntity.setPlanState("3");
            }
        }



        //执行mes可执行计划更新
        if (mesDoPlanMapper.updateById(mesDoPlanEntity) <= 0){
            throw new BusinessException("可执行计划更新失败,可执行任务编号："+planCode);
        }
        if (wmsActionMapper.updateById(wmsActionEntity) <= 0){
            throw new BusinessException("wms任务更新失败,可执行任务编号："+planCode);
        }
        if (wmsTaskMapper.updateById(taskEntity) <= 0){
            throw new BusinessException("wms报工更新失败,可执行任务编号："+planCode);
        }

        return true;
    }



    /** 参数，参数对应可执行任务，fj任务,报工正确性验证*********************************************************************/

    private MesDoPlanEntity mesDoPlanValidation(String planCode,String state){
        //参数验证
        if (StringUtils.isEmpty(planCode)){
            throw new BusinessException("无主计划编号，请检查接口数据");
        }
        LambdaQueryWrapper<MesDoPlanEntity> wrapperMesDoPlanEntity = new QueryWrapper<MesDoPlanEntity>().lambda()
                .eq(MesDoPlanEntity::getId, planCode)
                .eq(MesDoPlanEntity::getPlanState,state);
        MesDoPlanEntity doPlanEntity = mesDoPlanMapper.selectOne(wrapperMesDoPlanEntity);
        if (doPlanEntity == null){
            throw  new BusinessException("不存在所属mes可执行计划");
        }
        return doPlanEntity;
    }

    private WmsTaskEntity wmsTaskValidation(String planCode,int taskType,String state,String id){
        LambdaQueryWrapper<WmsTaskEntity> wrapperFjTaskEntity = new QueryWrapper<WmsTaskEntity>().lambda()
                .eq(WmsTaskEntity::getPlanCode,planCode)
                .eq(WmsTaskEntity::getFplanState,state)
                .eq(id != null,WmsTaskEntity::getId,id)
                .eq(WmsTaskEntity::getTaskType,taskType);
        //判断是否存在对应fj任务
        WmsTaskEntity taskEntity = wmsTaskMapper.selectOne(wrapperFjTaskEntity);
        if (taskEntity == null){
            throw  new BusinessException("不存在喷码任务");
        }
        return taskEntity;
    }

    private void wmsTaskValidation(WmsTaskEntity wmsTaskEntity,String state){
        this.wmsTaskValidation(wmsTaskEntity.getPlanCode(),wmsTaskEntity.getTaskType(),state,wmsTaskEntity.getId());
    }

    private WmsActionEntity wmsActionValidation(String planCode){
        //判断是否有对应报工数据
        LambdaQueryWrapper<WmsActionEntity> wrapperWmsActionEntity = new QueryWrapper<WmsActionEntity>().lambda()
                .eq(WmsActionEntity::getPlanCode,planCode)
                .orderByDesc(WmsActionEntity::getSendTime);
        WmsActionEntity wmsActionEntity = wmsActionMapper.selectOne(wrapperWmsActionEntity);
        if (wmsActionEntity == null){
            throw new BusinessException("不存在对应报工数据");
        }
        return wmsActionEntity;
    }
}
