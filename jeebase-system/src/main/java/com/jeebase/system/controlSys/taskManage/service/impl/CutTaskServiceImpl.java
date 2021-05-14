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
import com.jeebase.system.controlSys.reportAction.entity.WmsActionEntity;
import com.jeebase.system.controlSys.reportAction.mapper.ICutActionMapper;
import com.jeebase.system.controlSys.taskManage.entity.CutTaskEntity;
import com.jeebase.system.controlSys.taskManage.entity.WmsTaskEntity;
import com.jeebase.system.controlSys.taskManage.mapper.ICutTaskMapper;
import com.jeebase.system.controlSys.taskManage.service.ICutTaskService;
import com.jeebase.system.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    public boolean doTask(String cutTaskId, String planCode) throws BusinessException {
        //调用api开始任务执行
        //cutAnalysisApi.doCutPlan("");
        LocalDateTime now = LocalDateTime.now();//获取当前时间

        //创建初始报工记录，并入库
        CutActionEntity cutActionEntity = new CutActionEntity();
        cutActionEntity.setId(UUIDUtils.getUUID32());
        cutActionEntity.setSendTime(now);
        //设置动作名称
        //设置指令接口
        //报工入库
        if (cutActionMapper.insert(cutActionEntity) <=0 ){
            throw new BusinessException("添加报工失败");
        }

        //更改mes可执行任务状态
        MesDoPlanEntity mesDoPlanEntity = new MesDoPlanEntity();
        mesDoPlanEntity.setId(planCode);
        mesDoPlanEntity.setPlanState("2");
        mesDoPlanEntity.setUpdateTime(now);
        if (mesDoPlanMapper.selectById(planCode) == null){
            throw  new BusinessException("不存在所属mes可执行计划");
        }
        mesDoPlanMapper.updateById(mesDoPlanEntity);//更新mes可执行计划


        //更改任务执行状态
        CutTaskEntity cutTaskEntity = new CutTaskEntity();
        cutTaskEntity.setId(cutTaskId);
        cutTaskEntity.setCtaskState("1");
        cutTaskEntity.setStartTime(now);
        if (cutTaskMapper.selectById(cutTaskId) == null){
            throw  new BusinessException("不存在任务id");
        }
        cutTaskMapper.updateById(cutTaskEntity);
        return true;
    }

    @Override
    @Transactional
    public boolean doTaskCallBack(CutTaskEntity taskEntity){

        if (taskEntity.getPlanCode() == null){
            throw new BusinessException("无主计划编号，请检查接口数据");
        }

        //更新报工信息
        //根据计划编号获取报工对象
        LambdaQueryWrapper<CutActionEntity> lambda = new QueryWrapper<CutActionEntity>().lambda()
                .eq(CutActionEntity::getPlanCode,taskEntity.getPlanCode())
                .orderByAsc(CutActionEntity::getSendTime);
        CutActionEntity cutActionEntity = cutActionMapper.selectOne(lambda);
        if (cutActionEntity == null){
            throw new BusinessException("不存在对应报工数据");
        }
        //设置更新参数
        cutActionEntity.setReportTime(LocalDateTime.now());
        //执行报工更新
        cutActionMapper.update(cutActionEntity,lambda);


        //更新task任务状态
        LambdaQueryWrapper<CutTaskEntity> lambda1 = new QueryWrapper<CutTaskEntity>().lambda()
                .eq(CutTaskEntity::getPlanCode,taskEntity.getPlanCode());
        CutTaskEntity cutTaskEntity = cutTaskMapper.selectOne(lambda1);
        if (cutTaskEntity == null){
            throw new BusinessException("不存在对应任务数据");
        }
        //设置更新参数
        taskEntity.setEndTime(LocalDateTime.now());
        //执行任务更新
        cutTaskMapper.update(taskEntity,lambda1);


        //判断是否自动任务，若为自动则调用下一步操作
        if("auto".equals(cutTaskEntity.getExeModel())){

        }
        return true;
    }

}
