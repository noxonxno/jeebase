package com.jeebase.system.controlSys.taskManage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeebase.common.base.BusinessException;
import com.jeebase.system.controlSys.planManage.entity.MesDoPlanEntity;
import com.jeebase.system.controlSys.planManage.mapper.IMesDoPlanMapper;
import com.jeebase.system.controlSys.reportAction.entity.CutActionEntity;
import com.jeebase.system.controlSys.reportAction.entity.WmsActionEntity;
import com.jeebase.system.controlSys.reportAction.mapper.IWmsActionMapper;
import com.jeebase.system.controlSys.taskManage.entity.CutTaskEntity;
import com.jeebase.system.controlSys.taskManage.entity.WmsTaskEntity;
import com.jeebase.system.controlSys.taskManage.mapper.IWmsTaskMapper;
import com.jeebase.system.controlSys.taskManage.service.IFjTaskService;
import com.jeebase.system.controlSys.taskManage.service.IWmsTaskService;
import com.jeebase.system.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class WmsTaskServiceImpl extends ServiceImpl<IWmsTaskMapper, WmsTaskEntity> implements IWmsTaskService {

    @Autowired
    private IWmsTaskMapper wmsTaskMapper;

    @Autowired
    private IMesDoPlanMapper mesDoPlanMapper;

    @Autowired
    private IWmsActionMapper wmsActionMapper;

    @Autowired
    private IFjTaskService fjTaskService;

    @Override
    public Page<WmsTaskEntity> selectList(Page<WmsTaskEntity> page, WmsTaskEntity wmsTaskEntity) {

        LambdaQueryWrapper<WmsTaskEntity> lambda = new QueryWrapper<WmsTaskEntity>().lambda();

        IPage<WmsTaskEntity> cutTaskEntityIPage = wmsTaskMapper.selectPage(page, lambda);
        return  (Page<WmsTaskEntity>)cutTaskEntityIPage;
    }

    @Override
    @Transactional
    public boolean doTask(String planCode,String isAuto) throws BusinessException {


        //确认辊道状态托盘就位信号

        //写入g_plan

        //调用api开始上料任务
        //wmsAnalysisApi.doCutPlan("");


        MesDoPlanEntity doPlanEntity = mesDoPlanMapper.selectById(planCode);
        if (doPlanEntity == null){
            throw new BusinessException("不存在所属mes可执行计划");
        }

        //判断当前任务执行环节上料or下料
        if (doPlanEntity.getTaskType() == 0){
            //执行上料任务
            //cutAnalysisApi.doCutPlan("");


        }else {//执行下料任务

        }


        //更改mes可执行任务状态
        MesDoPlanEntity mesDoPlanEntity = new MesDoPlanEntity();
        mesDoPlanEntity.setId(planCode);
        mesDoPlanEntity.setTaskType(doPlanEntity.getTaskType()+1);
        mesDoPlanEntity.setUpdateTime(LocalDateTime.now());
        mesDoPlanEntity.setExeModel(isAuto);
        mesDoPlanMapper.updateById(mesDoPlanEntity);//更新mes可执行计划


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
        LambdaQueryWrapper<WmsTaskEntity> lambda = new QueryWrapper<WmsTaskEntity>().lambda();
        lambda.eq(WmsTaskEntity::getPlanCode,planCode).eq(WmsTaskEntity::getTaskTpye,"1");
        WmsTaskEntity taskEntity = wmsTaskMapper.selectOne(lambda);
        if (taskEntity == null){
            throw  new BusinessException("不存在任务id");
        }
        wmsTaskEntity.setId(taskEntity.getId());
        wmsTaskMapper.updateById(wmsTaskEntity);
        return true;
    }

    /**
     * wms任务执行结果回调
     * @param taskEntity
     * @return
     */
    @Override
    @Transactional
    public boolean doTaskCallBack(WmsTaskEntity taskEntity){

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
        LambdaQueryWrapper<WmsActionEntity> lambda = new QueryWrapper<WmsActionEntity>().lambda()
                .eq(WmsActionEntity::getPlanCode,planCode)
                .orderByAsc(WmsActionEntity::getSendTime);
        List<WmsActionEntity> wmsActionEntities = wmsActionMapper.selectList(lambda);
        if (wmsActionEntities.size() >= 0){
            throw new BusinessException("不存在对应报工数据");
        }

        //判断是否有task信息
        LambdaQueryWrapper<WmsTaskEntity> wrapperWmsTaskEntity = new QueryWrapper<WmsTaskEntity>().lambda()
                .eq(WmsTaskEntity::getPlanCode,planCode)
                .eq(WmsTaskEntity::getTaskTpye,taskEntity.getTaskTpye())
                .eq(WmsTaskEntity::getId,taskEntity.getId());
        if (wmsTaskMapper.selectOne(wrapperWmsTaskEntity) == null){
            throw new BusinessException("不存在对应任务数据");
        }

        //创建可执行计划更新数据
        MesDoPlanEntity mesDoPlanEntity = new MesDoPlanEntity();
        mesDoPlanEntity.setId(planCode);
        mesDoPlanEntity.setUpdateTime(LocalDateTime.now());

        //创建报工更新数据
        WmsActionEntity wmsActionEntity = wmsActionEntities.get(0);
        wmsActionEntity.setReportTime(LocalDateTime.now());
        wmsActionEntity.setReportLog("");
                //去除冗余更新字段减少更新消耗
        wmsActionEntity.setSendTime(null);
        wmsActionEntity.setPlanCode(null);
        wmsActionEntity.setActionName(null);
        wmsActionEntity.setSendLog(null);

        //当前任务失败对应或成功更新数据
        if ("2".equals(taskEntity.getFplanState())){
            mesDoPlanEntity.setPlanState("2");//可执行计划状态设置失败
            wmsActionEntity.setResult("失败");//报工结果设置为失败
        }else {
            wmsActionEntity.setResult("成功");
            mesDoPlanEntity.setTaskType(doPlanEntity.getTaskType()+1);//任务环节加一
        }

        //执行mes可执行计划更新
        mesDoPlanMapper.updateById(mesDoPlanEntity);
        //执行报工更新
        wmsActionMapper.updateById(wmsActionEntity);
        //执行任务更新
        wmsTaskMapper.updateById(taskEntity);


        //判断是否自动任务，若为自动则调用下一步操作//根据主任务执行环节来判断下一步执行步骤
        if("auto".equals(doPlanEntity.getExeModel()) && "2".equals(taskEntity.getFplanState())){
                doNext(doPlanEntity);
        }
        return true;
    }


    public boolean doNext(MesDoPlanEntity doPlanEntity){
        if (doPlanEntity.getTaskType() == 2){
            //执行喷码操作
            fjTaskService.doTask(doPlanEntity.getPlanCode());
        }
        return true;
    }
}
