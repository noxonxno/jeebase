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

    @Override
    public Page<WmsTaskEntity> selectList(Page<WmsTaskEntity> page, WmsTaskEntity wmsTaskEntity) {

        LambdaQueryWrapper<WmsTaskEntity> lambda = new QueryWrapper<WmsTaskEntity>().lambda();

        IPage<WmsTaskEntity> cutTaskEntityIPage = wmsTaskMapper.selectPage(page, lambda);
        return  (Page<WmsTaskEntity>)cutTaskEntityIPage;
    }

    @Override
    @Transactional
    public boolean doTask(String wmsTaskId, String planCode) throws BusinessException {
        //调用api开始任务执行
        //cutAnalysisApi.doCutPlan("");

        //创建初始报工记录，并入库
        WmsActionEntity wmsActionEntity = new WmsActionEntity();
        wmsActionEntity.setId(UUIDUtils.getUUID32());
        wmsActionEntity.setPlanCode(planCode);
        //设置指令发送时间
        LocalDateTime now = LocalDateTime.now();
        wmsActionEntity.setSendTime(now);
        //设置动作名称
        //设置指令接口
        //报工入库
        if (wmsActionMapper.insert(wmsActionEntity) <=0 ){
            throw new BusinessException("添加报工失败");
        }

        //更改mes可执行任务状态
        MesDoPlanEntity mesDoPlanEntity = new MesDoPlanEntity();
        mesDoPlanEntity.setId(planCode);
        mesDoPlanEntity.setExeModel("manual");
        mesDoPlanEntity.setPlanState("2");
        mesDoPlanEntity.setUpdateTime(LocalDateTime.now());
        if (mesDoPlanMapper.selectById(planCode) == null){
            throw  new BusinessException("不存在所属mes可执行计划");
        }
        mesDoPlanMapper.updateById(mesDoPlanEntity);//更新mes可执行计划


        //更改任务执行状态
        WmsTaskEntity wmsTaskEntity = new WmsTaskEntity();
        wmsTaskEntity.setId(wmsTaskId);
        wmsTaskEntity.setFplanState("3");
        if (wmsTaskMapper.selectById(wmsTaskId) == null){
            throw  new BusinessException("不存在任务id");
        }
        wmsTaskMapper.updateById(wmsTaskEntity);
        return true;
    }

    @Override
    @Transactional
    public boolean doTaskCallBack(WmsTaskEntity taskEntity){
        String planCode = taskEntity.getPlanCode();
        if (StringUtils.isEmpty(planCode)){
            throw new BusinessException("无主计划编号，请检查接口数据");
        }

        //更新mes可执行计划信息
        if (mesDoPlanMapper.selectById(planCode) == null){
            throw  new BusinessException("不存在所属mes可执行计划");
        }
        MesDoPlanEntity mesDoPlanEntity = new MesDoPlanEntity();
        mesDoPlanEntity.setId(planCode);
        mesDoPlanEntity.setPlanState("2");
        mesDoPlanEntity.setUpdateTime(LocalDateTime.now());
        mesDoPlanMapper.updateById(mesDoPlanEntity);//更新mes可执行计划


        //更新报工信息
        //根据计划编号获取报工对象
        LambdaQueryWrapper<WmsActionEntity> lambda = new QueryWrapper<WmsActionEntity>().lambda()
                .eq(WmsActionEntity::getPlanCode,planCode)
                .orderByAsc(WmsActionEntity::getSendTime);
        List<WmsActionEntity> wmsActionEntities = wmsActionMapper.selectList(lambda);

        if (wmsActionEntities.size() >= 0){
            throw new BusinessException("不存在对应报工数据");
        }
        WmsActionEntity wmsActionEntity = wmsActionEntities.get(0);
        //设置更新参数
        wmsActionEntity.setReportTime(LocalDateTime.now());
        //执行报工更新
        wmsActionMapper.update(wmsActionEntity,lambda);


        //更新task信息
        LambdaQueryWrapper<WmsTaskEntity> lambda1 = new QueryWrapper<WmsTaskEntity>().lambda()
                .eq(WmsTaskEntity::getPlanCode,planCode);
        WmsTaskEntity wmsTaskEntity = wmsTaskMapper.selectOne(lambda1);
        if (wmsTaskEntity == null){
            throw new BusinessException("不存在对应任务数据");
        }
        //设置更新参数
        taskEntity.setEndTime(LocalDateTime.now());
        //执行任务更新
        wmsTaskMapper.update(taskEntity,lambda1);

        //判断是否自动任务，若为自动则调用下一步操作
        if("auto".equals(wmsTaskEntity.getExeModel())){

        }
        return true;
    }
}
