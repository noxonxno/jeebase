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
    public boolean doTask(String planCode) throws BusinessException {

        //参数验证
        if (StringUtils.isEmpty(planCode)){
            throw new BusinessException("无主计划编号，请检查接口数据");
        }

        //判断是否有mes可执行计划信息
        LambdaQueryWrapper<MesDoPlanEntity> wrapperMesDoPlanEntity = new QueryWrapper<MesDoPlanEntity>().lambda();
        wrapperMesDoPlanEntity.eq(MesDoPlanEntity::getId, planCode)
                .eq(MesDoPlanEntity::getPlanState,"4");
        MesDoPlanEntity doPlanEntity = mesDoPlanMapper.selectOne(wrapperMesDoPlanEntity);
        if (doPlanEntity == null){
            throw  new BusinessException("不存在所属mes可执行计划");
        }
        //判断是否有对应fj任务
        LambdaQueryWrapper<FjTaskEntity> wrapperFjTaskEntity = new QueryWrapper<FjTaskEntity>().lambda();
        wrapperFjTaskEntity.eq(FjTaskEntity::getPlanCode,planCode)
        .eq(FjTaskEntity::getFtaskState,"3")
        .eq(doPlanEntity.getTaskType() == 3,FjTaskEntity::getTaskType,"pm")
        .eq(doPlanEntity.getTaskType() == 7,FjTaskEntity::getTaskType,"xj")
        .eq(doPlanEntity.getTaskType() == 9,FjTaskEntity::getTaskType,"dj");
        //判断是否存在对应fj任务
        FjTaskEntity taskEntity = fjTaskMapper.selectOne(wrapperFjTaskEntity);
        if (taskEntity == null){
            throw  new BusinessException("不存在喷码任务");
        }

        //创建初始报工记录
        FjActionEntity fjActionEntity = new FjActionEntity();
        fjActionEntity.setId(UUIDUtils.getUUID32());
        fjActionEntity.setSendTime(LocalDateTime.now());
        fjActionEntity.setPlanCode(planCode);
        fjActionEntity.setSendLog("");
        //更改mes可执行任务状态
        MesDoPlanEntity mesDoPlanEntity = new MesDoPlanEntity();
        mesDoPlanEntity.setId(planCode);
        mesDoPlanEntity.setTaskType(doPlanEntity.getTaskType()+1);
        mesDoPlanEntity.setUpdateTime(LocalDateTime.now());
        //更改任务执行状态
        FjTaskEntity fjTaskEntity = new FjTaskEntity();
        fjTaskEntity.setFtaskState("4");
        fjTaskEntity.setId(taskEntity.getId());


        //判断当前执行任务 喷码，大件分拣，小件分拣
        if (mesDoPlanEntity.getTaskType() == 3){//喷码
            fjActionEntity.setActionName("喷码");

        }else if (mesDoPlanEntity.getTaskType() == 7){//小件分拣
            fjActionEntity.setActionName("小件分拣");

        }else if (mesDoPlanEntity.getTaskType() == 9){//大件分拣
            fjActionEntity.setActionName("大件分拣");

        }else {//类型错误
            throw new BusinessException("任务环节错误");
        }

        //调用api执行任务
        //fjApi.doFJPlan("");


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

        //返回条件参数
        String planCode = taskEntity.getPlanCode();
        if (StringUtils.isEmpty(planCode)){
            throw new BusinessException("无主计划编号，请检查接口数据");
        }

        //判断是否有mes可执行计划信息
        LambdaQueryWrapper<MesDoPlanEntity> wrapperMesDoPlanEntity = new QueryWrapper<MesDoPlanEntity>().lambda();
        wrapperMesDoPlanEntity.eq(MesDoPlanEntity::getId, planCode)
                .eq(MesDoPlanEntity::getPlanState,"4");
        MesDoPlanEntity doPlanEntity = mesDoPlanMapper.selectOne(wrapperMesDoPlanEntity);
        if (doPlanEntity == null){
            throw  new BusinessException("不存在所属mes可执行计划");
        }

        //判断是否有对应报工数据
        LambdaQueryWrapper<FjActionEntity> wrapperFjActionEntity = new QueryWrapper<FjActionEntity>().lambda()
                .eq(FjActionEntity::getPlanCode,taskEntity.getPlanCode())
                .orderByAsc(FjActionEntity::getSendTime);
        List<FjActionEntity> fjActionEntities = fjActionMapper.selectList(wrapperFjActionEntity);
        if (fjActionEntities.size() <= 0){
            throw new BusinessException("不存在对应报工数据");
        }

        //判断是否有对应任务数据
        LambdaQueryWrapper<FjTaskEntity> wrapperFjTaskEntity = new QueryWrapper<FjTaskEntity>().lambda()
                .eq(FjTaskEntity::getPlanCode,taskEntity.getPlanCode())
                .eq(FjTaskEntity::getFtaskState,"4")
                .eq(doPlanEntity.getTaskType() == 3,FjTaskEntity::getTaskType,"pm")
                .eq(doPlanEntity.getTaskType() == 7,FjTaskEntity::getTaskType,"xj")
                .eq(doPlanEntity.getTaskType() == 9,FjTaskEntity::getTaskType,"dj");
        FjTaskEntity fjTaskEntity = fjTaskMapper.selectOne(wrapperFjTaskEntity);
        if (fjTaskEntity == null){
            throw new BusinessException("不存在对应任务数据");
        }


        //创建主计划更新数据
        MesDoPlanEntity mesDoPlanEntity = new MesDoPlanEntity();
        mesDoPlanEntity.setId(planCode);
        mesDoPlanEntity.setUpdateTime(LocalDateTime.now());

        //创建报工更新数据
        FjActionEntity fjActionEntity = fjActionEntities.get(0);
        fjActionEntity.setReportTime(LocalDateTime.now());
        fjActionEntity.setReportLog("");
                    //去除冗余更新字段减少更新消耗
        fjActionEntity.setSendLog(null);
        fjActionEntity.setActionName(null);
        fjActionEntity.setSendTime(null);
        fjActionEntity.setPlanCode(null);

        //创建分拣任务更新数据
        taskEntity.setId(fjTaskEntity.getId());


        //当前任务失败对应或成功更新数据
        if ("2".equals(taskEntity.getFtaskState())){
            mesDoPlanEntity.setPlanState("2");//可执行计划状态设置失败
            taskEntity.setFtaskState("2");//任务状态设置为失败
            fjActionEntity.setResult("失败");//报工结果设置为失败
        }else {
            taskEntity.setFtaskState("1");
            fjActionEntity.setResult("成功");
            mesDoPlanEntity.setTaskType(doPlanEntity.getTaskType()+1);//任务环节加一
        }

        //若该次任务为大小件分拣则需对分拣执行结果与零件表进行关联操作

        //更新
        fjTaskMapper.updateById(taskEntity);
        fjActionMapper.updateById(fjActionEntity);
        mesDoPlanMapper.updateById(mesDoPlanEntity);

        //判断是否自动任务，若为自动则调用下一步操作
        if("auto".equals(doPlanEntity.getExeModel())&& "2".equals(taskEntity.getFtaskState())){

        }
        return true;
    }

}
