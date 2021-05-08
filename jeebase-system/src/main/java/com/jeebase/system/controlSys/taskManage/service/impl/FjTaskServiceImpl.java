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

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class FjTaskServiceImpl extends ServiceImpl<IFjTaskMapper, FjTaskEntity> implements IFjTaskService {

    @Autowired
    private IFjTaskMapper fjTaskMapper;

    @Autowired
    private IFjActionMapper fjActionMapper;

    @Autowired
    private IMesDoPlanMapper mesDoPlanMapper;

    @Override
    public Page<FjTaskEntity> selectList(Page<FjTaskEntity> page, FjTaskEntity fjTaskEntity) {

        LambdaQueryWrapper<FjTaskEntity> lambda = new QueryWrapper<FjTaskEntity>().lambda();
        IPage<FjTaskEntity> cutTaskEntityIPage = fjTaskMapper.selectPage(page, lambda);
        return  (Page<FjTaskEntity>)cutTaskEntityIPage;
    }

    @Override
    @Transactional
    public boolean doTask(String fjTaskId, String planCode) throws BusinessException {
        //调用api开始任务执行
        //cutAnalysisApi.doCutPlan("");

        //创建初始报工记录，并入库
        FjActionEntity fjActionEntity = new FjActionEntity();
        fjActionEntity.setId(UUIDUtils.getUUID32());
        //设置指令发送时间
        LocalDateTime now = LocalDateTime.now();
        fjActionEntity.setSendTime(now);
        //设置动作名称
        //设置指令接口
        //报工入库
        if (fjActionMapper.insert(fjActionEntity) <=0 ){
            throw new BusinessException("添加报工失败");
        }

        //更改mes可执行任务状态
        MesDoPlanEntity mesDoPlanEntity = new MesDoPlanEntity();
        mesDoPlanEntity.setId(planCode);
        mesDoPlanEntity.setPlanState("2");
        mesDoPlanEntity.setUpdateTime(LocalDateTime.now());
        if (mesDoPlanMapper.selectById(planCode) == null){
            throw  new BusinessException("不存在所属mes可执行计划");
        }
        mesDoPlanMapper.updateById(mesDoPlanEntity);//更新mes可执行计划


        //更改任务执行状态
        FjTaskEntity fjTaskEntity = new FjTaskEntity();
        fjTaskEntity.setId(fjTaskId);
        fjTaskEntity.setFtaskState("1");
        if (fjTaskMapper.selectById(fjTaskId) == null){
            throw  new BusinessException("不存在任务id");
        }
        fjTaskMapper.updateById(fjTaskEntity);
        return true;
    }

    @Override
    @Transactional
    public boolean doTaskCallBack(FjTaskEntity taskEntity){

        if (taskEntity.getPlanCode() == null){
            throw new BusinessException("无主计划编号，请检查接口数据");
        }

        //更新报工信息
        //根据计划编号获取报工对象
        LambdaQueryWrapper<FjActionEntity> lambda = new QueryWrapper<FjActionEntity>().lambda()
                .eq(FjActionEntity::getPlanCode,taskEntity.getPlanCode())
                .orderByAsc(FjActionEntity::getSendTime);
        FjActionEntity fjActionEntity = fjActionMapper.selectOne(lambda);
        if (fjActionEntity == null){
            throw new BusinessException("不存在对应报工数据");
        }
        //设置更新参数
        fjActionEntity.setReportTime(LocalDateTime.now());
        //执行报工更新
        fjActionMapper.update(fjActionEntity,lambda);


        //更新task任务状态
        LambdaQueryWrapper<FjTaskEntity> lambda1 = new QueryWrapper<FjTaskEntity>().lambda()
                .eq(FjTaskEntity::getPlanCode,taskEntity.getPlanCode());
        FjTaskEntity fjTaskEntity = fjTaskMapper.selectOne(lambda1);
        if (fjTaskEntity == null){
            throw new BusinessException("不存在对应任务数据");
        }
        //设置更新参数
        taskEntity.setEndTime(LocalDateTime.now());
        //执行任务更新
        fjTaskMapper.update(taskEntity,lambda1);


        //判断是否自动任务，若为自动则调用下一步操作
        if("auto".equals(fjTaskEntity.getExeModel())){

        }
        return true;
    }


}
