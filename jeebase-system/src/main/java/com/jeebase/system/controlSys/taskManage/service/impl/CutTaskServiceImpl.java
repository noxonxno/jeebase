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
    private CutAnalysisApi cutAnalysisApi;

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
        LambdaQueryWrapper<CutTaskEntity> wrapperCutTaskEntity = new QueryWrapper<CutTaskEntity>().lambda();
        wrapperCutTaskEntity.eq(CutTaskEntity::getPlanCode,planCode)
                .eq(doPlanEntity.getTaskType() == 0,CutTaskEntity::getCtaskState,"3");
        CutTaskEntity taskEntity = cutTaskMapper.selectOne(wrapperCutTaskEntity);
        if (taskEntity == null){
            throw  new BusinessException("不存在任务");
        }

        //创建初始报工记录
        CutActionEntity cutActionEntity = new CutActionEntity();
        cutActionEntity.setId(UUIDUtils.getUUID32());
        cutActionEntity.setSendTime(LocalDateTime.now());
        cutActionEntity.setActionName("");
        cutActionEntity.setSendLog("");
        cutActionEntity.setPlanCode(planCode);
        //更改mes可执行任务状态
        MesDoPlanEntity mesDoPlanEntity = new MesDoPlanEntity();
        mesDoPlanEntity.setId(planCode);
        mesDoPlanEntity.setTaskType(doPlanEntity.getTaskType()+1);
        mesDoPlanEntity.setUpdateTime(LocalDateTime.now());
        //更改任务执行状态
        CutTaskEntity cutTaskEntity = new CutTaskEntity();
        cutTaskEntity.setCtaskState("4");
        cutTaskEntity.setId(taskEntity.getId());
        //报工入库
        if (cutActionMapper.insert(cutActionEntity) <=0 ){
            throw new BusinessException("添加报工失败");
        }

        //调用api执行任务

        //切割任务报工对象创建
        if (cutActionMapper.insert(cutActionEntity) <=0 ){
            throw new BusinessException("添加报工失败");
        }
        //更改mes可执行任务状态
        mesDoPlanMapper.updateById(mesDoPlanEntity);//更新mes可执行计划
        //更改任务状态
        cutTaskMapper.updateById(cutTaskEntity);

        return true;
    }

    @Override
    @Transactional
    public boolean doTaskCallBack(CutTaskEntity taskEntity){

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
        LambdaQueryWrapper<CutActionEntity> wrapperCutActionEntity = new QueryWrapper<CutActionEntity>().lambda()
                .eq(CutActionEntity::getPlanCode,taskEntity.getPlanCode())
                .orderByAsc(CutActionEntity::getSendTime);
        List<CutActionEntity> cutActionEntities = cutActionMapper.selectList(wrapperCutActionEntity);
        if (cutActionEntities.size() <= 0){
            throw new BusinessException("不存在对应报工数据");
        }
        //判断是否有对应任务数据
        LambdaQueryWrapper<CutTaskEntity> wrapperCutTaskEntity = new QueryWrapper<CutTaskEntity>().lambda()
                .eq(CutTaskEntity::getPlanCode,taskEntity.getPlanCode())
                .eq(CutTaskEntity::getCtaskState,"4");
        CutTaskEntity cutTaskEntity = cutTaskMapper.selectOne(wrapperCutTaskEntity);
        if (cutTaskEntity == null){
            throw new BusinessException("不存在对应任务数据");
        }


        //创建主计划更新数据
        MesDoPlanEntity mesDoPlanEntity = new MesDoPlanEntity();
        mesDoPlanEntity.setId(planCode);
        mesDoPlanEntity.setUpdateTime(LocalDateTime.now());

        //创建报工更新数据
        CutActionEntity cutActionEntity = cutActionEntities.get(0);
        cutActionEntity.setReportTime(LocalDateTime.now());
        cutActionEntity.setReportLog("");
                //去除冗余更新字段减少更新消耗
        cutActionEntity.setSendLog(null);
        cutActionEntity.setActionName(null);
        cutActionEntity.setSendTime(null);
        cutActionEntity.setPlanCode(null);

        //创建分拣任务更新数据
        taskEntity.setId(cutTaskEntity.getId());

        //当前任务失败对应或成功更新数据
        if ("2".equals(taskEntity.getCtaskState())){
            mesDoPlanEntity.setPlanState("2");//可执行计划状态设置失败
            taskEntity.setCtaskState("2");//任务状态设置为失败
            cutActionEntity.setResult("失败");//报工结果设置为失败
        }else {
            taskEntity.setCtaskState("1");
            cutActionEntity.setResult("成功");
            mesDoPlanEntity.setTaskType(doPlanEntity.getTaskType()+1);//任务环节加一
        }



        //更新
        cutTaskMapper.updateById(taskEntity);
        cutActionMapper.updateById(cutActionEntity);
        mesDoPlanMapper.updateById(mesDoPlanEntity);

        //判断是否自动任务，若为自动则调用下一步操作
        if("auto".equals(cutTaskEntity.getExeModel()) && "2".equals(taskEntity.getCtaskState())){

        }
        return true;
    }

}
