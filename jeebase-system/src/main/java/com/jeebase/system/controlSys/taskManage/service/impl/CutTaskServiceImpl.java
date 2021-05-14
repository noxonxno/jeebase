package com.jeebase.system.controlSys.taskManage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeebase.common.base.BusinessException;
import com.jeebase.system.controlSys.api.CutAnalysisApi;
import com.jeebase.system.controlSys.planManage.entity.MesDoPlanEntity;
import com.jeebase.system.controlSys.planManage.mapper.IMesDoPlanMapper;
import com.jeebase.system.controlSys.reportAction.entity.CutActionEntity;
import com.jeebase.system.controlSys.reportAction.entity.FjActionEntity;
import com.jeebase.system.controlSys.reportAction.entity.WmsActionEntity;
import com.jeebase.system.controlSys.reportAction.mapper.ICutActionMapper;
import com.jeebase.system.controlSys.taskManage.entity.CutTaskEntity;
import com.jeebase.system.controlSys.taskManage.entity.FjTaskEntity;
import com.jeebase.system.controlSys.taskManage.entity.WmsTaskEntity;
import com.jeebase.system.controlSys.taskManage.mapper.ICutTaskMapper;
import com.jeebase.system.controlSys.taskManage.service.ICutTaskService;
import com.jeebase.system.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class CutTaskServiceImpl extends ServiceImpl<ICutTaskMapper, CutTaskEntity> implements ICutTaskService {

    @Autowired
    private ICutTaskMapper cutTaskMapper;

    @Autowired
    private ICutActionMapper cutActionMapper;

    @Autowired
    private IMesDoPlanMapper mesDoPlanMapper;


    @Override
    public Page<CutTaskEntity> selectList(Page<CutTaskEntity> page, CutTaskEntity cutTaskEntity) {
        LambdaQueryWrapper<CutTaskEntity> lambda = new QueryWrapper<CutTaskEntity>().lambda();
        IPage<CutTaskEntity> cutTaskEntityIPage = cutTaskMapper.selectPage(page, lambda);
        return  (Page<CutTaskEntity>)cutTaskEntityIPage;
    }


    @Override
    @Transactional
    public boolean doTask(String planCode) throws BusinessException {

        /** 参数，参数对应可执行任务，cut任务正确性验证************************************************************************/

        MesDoPlanEntity doPlanEntity = this.mesDoPlanValidation(planCode, "5");
        CutTaskEntity taskEntity = this.cutTaskValidation(planCode, "5", null);

        /** 任务下发 ****************************************************************************************************/



        //若请求发送失败则抛出异常
        if (false){
            throw new BusinessException("cut任务下发失败");
        }

        /** 更新可执行任务，cut任务状态为执行中，可执行计划任务环节+1，创建对应操作报工**********************************************/
        //创建初始报工记录
        CutActionEntity cutActionEntity = new CutActionEntity();
        cutActionEntity.setId(UUIDUtils.getUUID32());
        cutActionEntity.setSendTime(LocalDateTime.now());
        cutActionEntity.setActionName("");
        cutActionEntity.setSendLog("");
        cutActionEntity.setPlanCode(planCode);
        if (cutActionMapper.insert(cutActionEntity) <=0 ){
            throw new BusinessException("添加报工失败");
        }
        //更改mes可执行任务状态
        MesDoPlanEntity mesDoPlanEntity = new MesDoPlanEntity();
        mesDoPlanEntity.setId(planCode);
        mesDoPlanEntity.setPlanState("4");
        mesDoPlanEntity.setTaskType(doPlanEntity.getTaskType()+1);
        mesDoPlanEntity.setUpdateTime(LocalDateTime.now());
        if (mesDoPlanMapper.updateById(mesDoPlanEntity) <= 0){//更新mes可执行计划,若失败抛出异常
            throw new BusinessException("可执行计划更新失败,可执行任务编号："+planCode);
        }
        //更改任务执行状态
        CutTaskEntity cutTaskEntity = new CutTaskEntity();
        cutTaskEntity.setCtaskState("4");
        cutTaskEntity.setId(taskEntity.getId());
        if(cutTaskMapper.updateById(cutTaskEntity) <= 0){
            throw new BusinessException("cut任务更新失败：cut任务编号："+taskEntity.getId());
        }
        return true;
    }

    @Override
    @Transactional
    public boolean doTaskCallBack(CutTaskEntity taskEntity){
        /** 参数，参数对应可执行任务，fj任务正确性验证************************************************************/

        String planCode = taskEntity.getPlanCode();
        MesDoPlanEntity doPlanEntity = this.mesDoPlanValidation(planCode,"4");
        CutActionEntity cutActionEntity = this.cutActionValidation(planCode);
        this.cutTaskValidation(taskEntity,"4");

        /** 更新主计划任务状态，任务环节，fj任务状态，报工结果完善***************************************************************/
        //创建主计划更新数据
        MesDoPlanEntity mesDoPlanEntity = new MesDoPlanEntity();
        mesDoPlanEntity.setId(planCode);
        mesDoPlanEntity.setUpdateTime(LocalDateTime.now());

        //创建报工更新数据
        cutActionEntity.setReportTime(LocalDateTime.now());
        cutActionEntity.setReportLog("");
                //去除冗余更新字段减少更新消耗
        cutActionEntity.setSendLog(null);
        cutActionEntity.setActionName(null);
        cutActionEntity.setSendTime(null);
        cutActionEntity.setPlanCode(null);

        //当前任务失败对应或成功更新数据
        if ("2".equals(taskEntity.getCtaskState())){
            mesDoPlanEntity.setPlanState("2");//可执行计划状态设置失败
            taskEntity.setCtaskState("2");//任务状态设置为失败
            cutActionEntity.setResult("失败");//报工结果设置为失败
        }else {
            taskEntity.setCtaskState("1");
            cutActionEntity.setResult("成功");
            mesDoPlanEntity.setTaskType(doPlanEntity.getTaskType()+1);//任务环节加一
            if ("auto".equals(doPlanEntity.getExeModel())){
                mesDoPlanEntity.setPlanState("5");
            }else {
                mesDoPlanEntity.setPlanState("3");
            }
        }

        //更新
        if (mesDoPlanMapper.updateById(mesDoPlanEntity) <= 0){
            throw new BusinessException("可执行计划更新失败,可执行任务编号："+planCode);
        }
        if (cutTaskMapper.updateById(taskEntity) <= 0){
            throw new BusinessException("cut任务更新失败,可执行任务编号："+planCode);
        }
        if ( cutActionMapper.updateById(cutActionEntity) <= 0){
            throw new BusinessException("cut任务报工更新失败,可执行任务编号："+planCode);
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

    private CutTaskEntity cutTaskValidation(String planCode,String state,String id){
        LambdaQueryWrapper<CutTaskEntity> wrapperFjTaskEntity = new QueryWrapper<CutTaskEntity>().lambda()
                .eq(CutTaskEntity::getPlanCode,planCode)
                .eq(id != null,CutTaskEntity::getId,id)
                .eq(CutTaskEntity::getCtaskState,state);
        //判断是否存在对应fj任务
        CutTaskEntity taskEntity = cutTaskMapper.selectOne(wrapperFjTaskEntity);
        if (taskEntity == null){
            throw  new BusinessException("不存在喷码任务");
        }
        return taskEntity;
    }

    private void cutTaskValidation(CutTaskEntity cutTaskEntity,String state){
        this.cutTaskValidation(cutTaskEntity.getPlanCode(),state,cutTaskEntity.getId());
    }

    private CutActionEntity cutActionValidation(String planCode){
        //判断是否有对应报工数据
        LambdaQueryWrapper<CutActionEntity> wrapperWmsActionEntity = new QueryWrapper<CutActionEntity>().lambda()
                .eq(CutActionEntity::getPlanCode,planCode)
                .orderByDesc(CutActionEntity::getSendTime);
        CutActionEntity cutActionEntity = cutActionMapper.selectOne(wrapperWmsActionEntity);
        if (cutActionEntity == null){
            throw new BusinessException("不存在对应报工数据");
        }
        return cutActionEntity;
    }
}
