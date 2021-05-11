package com.jeebase.system.controlSys.planManage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeebase.common.base.BusinessException;
import com.jeebase.system.controlSys.planManage.entity.MesDoPlanEntity;
import com.jeebase.system.controlSys.planManage.entity.MesPlanEntity;
import com.jeebase.system.controlSys.planManage.mapper.IMesDoPlanMapper;
import com.jeebase.system.controlSys.planManage.service.IMesDoPlanService;
import com.jeebase.system.controlSys.reportAction.entity.WmsActionEntity;
import com.jeebase.system.controlSys.reportAction.mapper.IWmsActionMapper;
import com.jeebase.system.controlSys.taskManage.entity.CutTaskEntity;
import com.jeebase.system.controlSys.taskManage.entity.FjTaskEntity;
import com.jeebase.system.controlSys.taskManage.entity.WmsTaskEntity;
import com.jeebase.system.controlSys.taskManage.mapper.ICutTaskMapper;
import com.jeebase.system.controlSys.taskManage.mapper.IFjTaskMapper;
import com.jeebase.system.controlSys.taskManage.mapper.IWmsTaskMapper;
import com.jeebase.system.utils.UUIDUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class MesDoPlanServiceImpl  extends ServiceImpl<IMesDoPlanMapper, MesDoPlanEntity> implements IMesDoPlanService {

    @Autowired
    private IMesDoPlanMapper mesDoPlanMapper;

    @Autowired
    private IWmsTaskMapper wmsTaskMapper;

    @Autowired
    private ICutTaskMapper cutTaskMapper;

    @Autowired
    private IFjTaskMapper fjTaskMapper;

    @Autowired
    private IWmsActionMapper wmsActionMapper;


    @Override
    public Page<MesDoPlanEntity> selectList(Page<MesDoPlanEntity> page,MesDoPlanEntity mesDoPlanEntity) {

        LambdaQueryWrapper<MesDoPlanEntity> lambda = new QueryWrapper<MesDoPlanEntity>().lambda();
        lambda.eq(mesDoPlanEntity.getMesAdviceId() != null, MesDoPlanEntity::getMesAdviceId, mesDoPlanEntity.getMesAdviceId())
                .eq(mesDoPlanEntity.getPlanCode() != null,MesDoPlanEntity::getPlanCode,mesDoPlanEntity.getPlanCode());


        IPage<MesDoPlanEntity> mesDoPlanEntityIPage = mesDoPlanMapper.selectPage(page, lambda);
        return (Page<MesDoPlanEntity>) mesDoPlanEntityIPage;
    }

    /**
     * 当可执行计划表生成时，分别生成三个任务对象入库
     * @return
     * @param mesPlanEntity
     */
    @Override
    public void create(MesPlanEntity mesPlanEntity) throws BusinessException {

        //查询出是否已存在可执行计划
        LambdaQueryWrapper<MesDoPlanEntity> lambda = new QueryWrapper<MesDoPlanEntity>().lambda()
                .eq(mesPlanEntity.getPlanCode() != null, MesDoPlanEntity::getPlanCode, mesPlanEntity.getPlanCode());

        if (mesDoPlanMapper.selectOne(lambda) != null){
            throw new BusinessException("该编码已存在可执行计划");
        }else {
            //若不存在重复计划编码则添加
            MesDoPlanEntity mesDoPlanEntity = new MesDoPlanEntity();
            BeanUtils.copyProperties(mesPlanEntity,mesDoPlanEntity);//拷贝属性
            if (mesDoPlanMapper.insert(mesDoPlanEntity)<1){throw new BusinessException("创建可执行计划失败");}//若添加失败则抛出异常结束方法
            //成功后则调用方法创建任务对象
            createTaskByDaPlan(mesDoPlanEntity);
        }
    }

    //通过可执行计划创建，分拣，切割，上料任务
    @Transactional
    protected void createTaskByDaPlan(MesDoPlanEntity mesDoPlanEntity) throws BusinessException {
        //创建任务添加对应属性
        //切割
        CutTaskEntity cutTaskEntity = new CutTaskEntity();
        BeanUtils.copyProperties(mesDoPlanEntity,cutTaskEntity,"id");
        //喷码
        FjTaskEntity fjTaskEntityPM = new FjTaskEntity();
        BeanUtils.copyProperties(mesDoPlanEntity,fjTaskEntityPM,"id");
        //大件
        FjTaskEntity fjTaskEntityDJ = new FjTaskEntity();
        BeanUtils.copyProperties(mesDoPlanEntity,fjTaskEntityDJ,"id");
        //小件
        FjTaskEntity fjTaskEntityXJ = new FjTaskEntity();
        BeanUtils.copyProperties(mesDoPlanEntity,fjTaskEntityXJ,"id");
        //上下料
        WmsTaskEntity wmsTaskEntity = new WmsTaskEntity();
        BeanUtils.copyProperties(mesDoPlanEntity,wmsTaskEntity,"id");

        //任务入库
        boolean success ;
        if (cutTaskMapper.insert(cutTaskEntity) != 1){
            throw new BusinessException("切割任务生成失败");
        };
        if (fjTaskMapper.insert(fjTaskEntityPM) +fjTaskMapper.insert(fjTaskEntityDJ) + fjTaskMapper.insert(fjTaskEntityXJ) != 3){
            throw new BusinessException("分拣任务生成失败");
        }
        if (wmsTaskMapper.insert(wmsTaskEntity) != 1){
            throw new BusinessException("上下料任务生成失败");
        }
    }


    @Override
    @Transactional
    public boolean doAutoTask(String planCode) throws BusinessException {



//        //创建初始报工记录，并入库
//        WmsActionEntity wmsActionEntity = new WmsActionEntity();
//        wmsActionEntity.setId(UUIDUtils.getUUID32());
//        //设置指令发送时间
//        LocalDateTime now = LocalDateTime.now();
//        wmsActionEntity.setSendTime(now);
//        //设置动作名称
//        //设置指令接口
//        //报工入库
//        if (wmsActionMapper.insert(wmsActionEntity) <=0 ){
//            throw new BusinessException("添加报工失败");
//        }
//
//        //更改mes可执行任务状态
//        if (mesDoPlanMapper.selectById(planCode) == null){
//            throw  new BusinessException("不存在所属mes可执行计划");
//        }
//        MesDoPlanEntity mesDoPlanEntity = new MesDoPlanEntity();
//        mesDoPlanEntity.setPlanState("1");
//        mesDoPlanEntity.setExeModel("auto");
//        mesDoPlanEntity.setUpdateTime(now);
//        mesDoPlanMapper.updateById(mesDoPlanEntity);//更新mes可执行计划
//
//        //更改wms喷码任务执行状态
//        LambdaQueryWrapper<WmsTaskEntity> lambda = new QueryWrapper<WmsTaskEntity>().lambda();
//        lambda.eq(WmsTaskEntity::getPlanCode,planCode).eq(WmsTaskEntity::getFplanType,"pm");
//        WmsTaskEntity wmsTaskEntity = wmsTaskMapper.selectOne(lambda);
//        if (wmsTaskEntity == null){
//            throw  new BusinessException("不存在任务id");
//        }
//        wmsTaskEntity.setExeModel("auto");
//        wmsTaskEntity.setFplanState("1");
//        wmsTaskMapper.updateById(wmsTaskEntity);
        return true;
    }
}
