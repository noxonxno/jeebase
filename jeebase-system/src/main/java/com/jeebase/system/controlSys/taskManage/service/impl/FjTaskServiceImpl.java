package com.jeebase.system.controlSys.taskManage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeebase.common.base.BusinessException;
import com.jeebase.system.controlSys.api.FjApi;
import com.jeebase.system.controlSys.planManage.entity.MesDoPlanEntity;
import com.jeebase.system.controlSys.planManage.mapper.IMesDoPlanMapper;
import com.jeebase.system.controlSys.reportAction.entity.CutActionEntity;
import com.jeebase.system.controlSys.reportAction.entity.FjActionEntity;
import com.jeebase.system.controlSys.reportAction.mapper.IFjActionMapper;
import com.jeebase.system.controlSys.taskManage.entity.CutTaskEntity;
import com.jeebase.system.controlSys.taskManage.entity.FjTaskEntity;
import com.jeebase.system.controlSys.taskManage.mapper.IFjTaskMapper;
import com.jeebase.system.controlSys.taskManage.service.IFjTaskService;
import com.jeebase.system.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class FjTaskServiceImpl extends ServiceImpl<IFjTaskMapper, FjTaskEntity> implements IFjTaskService {

    @Autowired
    private IFjTaskMapper fjTaskMapper;

    @Autowired
    private IFjActionMapper fjActionMapper;

    @Autowired
    private IMesDoPlanMapper mesDoPlanMapper;

    @Autowired
    private FjApi fjApi;

    @Override
    public Page<FjTaskEntity> selectList(Page<FjTaskEntity> page, FjTaskEntity fjTaskEntity) {

        LambdaQueryWrapper<FjTaskEntity> lambda = new QueryWrapper<FjTaskEntity>().lambda();
        IPage<FjTaskEntity> cutTaskEntityIPage = fjTaskMapper.selectPage(page, lambda);
        return  (Page<FjTaskEntity>)cutTaskEntityIPage;
    }

    @Override
    @Transactional
    public boolean doTask(String planCode,int taskType) throws BusinessException {

        /** 参数，参数对应可执行任务，fj任务正确性验证*************************************************************************/

        MesDoPlanEntity doPlanEntity = this.mesDoPlanValidation(planCode,"5");
        FjTaskEntity taskEntity = this.fjTaskValidation(planCode,taskType,"5",null);

        /** 任务下发 ****************************************************************************************************/

        //fjApi.doFJPlan("");

        //若请求发送失败则抛出异常
        if (false){
            throw new BusinessException("任务下发请求发送失败");
        }

        /** 更新可执行任务，fj任务状态为执行中，可执行计划任务环节+1，创建对应操作报工***********************************************/
        //创建初始报工记录
        FjActionEntity fjActionEntity = new FjActionEntity();
        fjActionEntity.setId(UUIDUtils.getUUID32());
        fjActionEntity.setSendTime(LocalDateTime.now());
        fjActionEntity.setPlanCode(planCode);
        fjActionEntity.setSendLog("");
        //更改mes可执行任务状态
        MesDoPlanEntity mesDoPlanEntity = new MesDoPlanEntity();
        mesDoPlanEntity.setId(planCode);
        mesDoPlanEntity.setPlanState("4");
        mesDoPlanEntity.setTaskType(doPlanEntity.getTaskType()+1);
        mesDoPlanEntity.setUpdateTime(LocalDateTime.now());
        //更改任务执行状态
        FjTaskEntity fjTaskEntity = new FjTaskEntity();
        fjTaskEntity.setFtaskState("4");
        fjTaskEntity.setId(taskEntity.getId());

        //判断当前执行任务 喷码，大件分拣，小件分拣
        if (taskType == 1){//喷码
            fjActionEntity.setActionName("喷码");

        }else if (taskType == 2){//小件分拣
            fjActionEntity.setActionName("小件分拣");

        }else if (taskType == 3){//大件分拣
            fjActionEntity.setActionName("大件分拣");

        }else {//类型错误
            throw new BusinessException("任务环节错误");
        }

        //分拣任务报工对象创建
        if (fjActionMapper.insert(fjActionEntity) <=0 ){
            throw new BusinessException("添加报工失败");
        }
        //mes可执行计划更新
        mesDoPlanMapper.updateById(mesDoPlanEntity);//更新mes可执行计划
        //更新fj计划
        fjTaskMapper.updateById(fjTaskEntity);
        return true;
    }

    @Override
    @Transactional
    public boolean doTaskCallBack(FjTaskEntity taskEntity){
        /** 参数，参数对应可执行任务，fj任务,报工正确性验证*********************************************************************/

        String planCode = taskEntity.getPlanCode();
        MesDoPlanEntity doPlanEntity = this.mesDoPlanValidation(planCode,"4");
        FjActionEntity fjActionEntity = this.fjActionValidation(planCode);
        this.fjTaskValidation(taskEntity,"4");

        /** 更新主计划任务状态，任务环节，fj任务状态，报工结果完善***************************************************************/
        //创建主计划更新数据
        MesDoPlanEntity mesDoPlanEntity = new MesDoPlanEntity();
        mesDoPlanEntity.setId(planCode);
        mesDoPlanEntity.setUpdateTime(LocalDateTime.now());

        //创建报工更新数据
        fjActionEntity.setReportTime(LocalDateTime.now());
        fjActionEntity.setReportLog("");
                    //去除冗余更新字段减少更新消耗
        fjActionEntity.setSendLog(null);
        fjActionEntity.setActionName(null);
        fjActionEntity.setSendTime(null);
        fjActionEntity.setPlanCode(null);

        //当前任务失败对应或成功更新数据
        if ("2".equals(taskEntity.getFtaskState())){
            mesDoPlanEntity.setPlanState("2");//可执行计划状态设置失败
            taskEntity.setFtaskState("2");//任务状态设置为失败
            fjActionEntity.setResult("失败");//报工结果设置为失败
        }else {
            taskEntity.setFtaskState("1");
            fjActionEntity.setResult("成功");
            mesDoPlanEntity.setTaskType(doPlanEntity.getTaskType()+1);//任务环节加一
            if ("auto".equals(doPlanEntity.getExeModel())){
                mesDoPlanEntity.setPlanState("5");
            }else {
                mesDoPlanEntity.setPlanState("3");
            }
        }

        //若该次任务为大小件分拣则需对分拣执行结果与零件表进行关联操作


        //更新
        if (mesDoPlanMapper.updateById(mesDoPlanEntity) <= 0){//更新mes可执行计划,若失败抛出异常
            throw new BusinessException("可执行计划更新失败,可执行任务编号："+planCode);
        }
        if (fjTaskMapper.updateById(taskEntity) <= 0){//更新fj任务,若失败抛出异常
            throw new BusinessException("fj任务更新失败,可执行任务编号："+planCode);
        }
        if (fjActionMapper.updateById(fjActionEntity) <= 0){//更新mes可执行计划,若失败抛出异常
            throw new BusinessException("fj任务报工更新失败,可执行任务编号："+planCode);
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

    private FjTaskEntity fjTaskValidation(String planCode,int taskType,String state,String id){
        LambdaQueryWrapper<FjTaskEntity> wrapperFjTaskEntity = new QueryWrapper<FjTaskEntity>().lambda()
                .eq(FjTaskEntity::getPlanCode,planCode)
                .eq(FjTaskEntity::getFtaskState,state)
                .eq(id != null,FjTaskEntity::getId,id)
                .eq(FjTaskEntity::getTaskType,taskType);
        //判断是否存在对应fj任务
        FjTaskEntity taskEntity = fjTaskMapper.selectOne(wrapperFjTaskEntity);
        if (taskEntity == null){
            throw  new BusinessException("不存在喷码任务");
        }
        return taskEntity;
    }

    private void fjTaskValidation(FjTaskEntity fjTaskEntity,String state){
        this.fjTaskValidation(fjTaskEntity.getPlanCode(),fjTaskEntity.getTaskType(),state,fjTaskEntity.getId());
    }

    private FjActionEntity fjActionValidation(String planCode){
        //判断是否有对应报工数据
        LambdaQueryWrapper<FjActionEntity> wrapperFjActionEntity = new QueryWrapper<FjActionEntity>().lambda()
                .eq(FjActionEntity::getPlanCode,planCode)
                .orderByDesc(FjActionEntity::getSendTime);
        FjActionEntity fjActionEntity = fjActionMapper.selectOne(wrapperFjActionEntity);
        if (fjActionEntity == null){
            throw new BusinessException("不存在对应报工数据");
        }
        return fjActionEntity;
    }
}
