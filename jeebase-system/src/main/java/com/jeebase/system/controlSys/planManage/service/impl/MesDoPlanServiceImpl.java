package com.jeebase.system.controlSys.planManage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeebase.system.controlSys.planManage.entity.MesDoPlanEntity;
import com.jeebase.system.controlSys.planManage.entity.MesPlanEntity;
import com.jeebase.system.controlSys.planManage.mapper.IMesDoPlanMapper;
import com.jeebase.system.controlSys.planManage.service.IMesDoPlanService;
import com.jeebase.system.controlSys.taskManage.entity.CutTaskEntity;
import com.jeebase.system.controlSys.taskManage.entity.FjTaskEntity;
import com.jeebase.system.controlSys.taskManage.entity.WmsTaskEntity;
import com.jeebase.system.controlSys.taskManage.mapper.ICutTaskMapper;
import com.jeebase.system.controlSys.taskManage.mapper.IFjTaskMapper;
import com.jeebase.system.controlSys.taskManage.mapper.IWmsTaskMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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


    @Override
    public Page<MesDoPlanEntity> selectList(Page<MesDoPlanEntity> page,MesDoPlanEntity mesDoPlanEntity) {

        LambdaQueryWrapper<MesDoPlanEntity> lambda = new QueryWrapper<MesDoPlanEntity>().lambda();
        lambda.eq(mesDoPlanEntity.getMesAdviceId() != null, MesDoPlanEntity::getMesAdviceId, mesDoPlanEntity.getMesAdviceId());

        IPage<MesDoPlanEntity> mesDoPlanEntityIPage = mesDoPlanMapper.selectPage(page, lambda);
        return (Page<MesDoPlanEntity>) mesDoPlanEntityIPage;
    }

    /**
     * 当可执行计划表生成时，分别生成三个任务对象入库
     * @return
     * @param mesPlanEntity
     */
    @Override
    public void create(MesPlanEntity mesPlanEntity) throws Exception {

        //查询出是否已存在可执行计划
        LambdaQueryWrapper<MesDoPlanEntity> lambda = new QueryWrapper<MesDoPlanEntity>().lambda()
                .eq(mesPlanEntity.getPlanCode() != null, MesDoPlanEntity::getPlanCode, mesPlanEntity.getPlanCode());

        if (mesDoPlanMapper.selectOne(lambda) != null){
            throw new Exception("该编码已存在可执行计划");
        }else {
            //若不存在重复计划编码则添加
            MesDoPlanEntity mesDoPlanEntity = new MesDoPlanEntity();
            BeanUtils.copyProperties(mesPlanEntity,mesDoPlanEntity);//拷贝属性
            if (mesDoPlanMapper.insert(mesDoPlanEntity)<1){throw new Exception("创建可执行计划失败");}//若添加失败则抛出异常结束方法
            //成功后则调用方法创建任务对象
            createTaskByDaPlan(mesDoPlanEntity);
        }
    }

    //通过可执行计划创建，分拣，切割，上料任务
    @Transactional
    protected void createTaskByDaPlan(MesDoPlanEntity mesDoPlanEntity) throws Exception {
        //创建任务添加对应属性
        CutTaskEntity cutTaskEntity = new CutTaskEntity();
        BeanUtils.copyProperties(mesDoPlanEntity,cutTaskEntity,"");
        FjTaskEntity fjTaskEntity = new FjTaskEntity();
        BeanUtils.copyProperties(mesDoPlanEntity,fjTaskEntity,"");
        WmsTaskEntity wmsTaskEntity = new WmsTaskEntity();
        BeanUtils.copyProperties(mesDoPlanEntity,wmsTaskEntity,"");

        //任务入库
        boolean success ;
        if (cutTaskMapper.insert(cutTaskEntity) != 1){
            throw new Exception("切割任务生成失败");
        };
        if (fjTaskMapper.insert(fjTaskEntity) != 1){
            throw new Exception("分拣任务生成失败");
        }
        if (wmsTaskMapper.insert(wmsTaskEntity) != 1){
            throw new Exception("上下料任务生成失败");
        }
    }

}
