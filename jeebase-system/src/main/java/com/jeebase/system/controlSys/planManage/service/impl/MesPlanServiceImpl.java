package com.jeebase.system.controlSys.planManage.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeebase.system.controlSys.planManage.entity.MesPlanEntity;
import com.jeebase.system.controlSys.planManage.mapper.IMesPlanMapper;
import com.jeebase.system.controlSys.planManage.service.IMesDoPlanService;
import com.jeebase.system.controlSys.planManage.service.IMesPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MesPlanServiceImpl extends ServiceImpl<IMesPlanMapper, MesPlanEntity> implements IMesPlanService {

    @Autowired
    private IMesPlanMapper mesPlanMapper;

    @Autowired
    private IMesDoPlanService mesDoPlanService;

    @Override
    public Page<MesPlanEntity> selectList(Page<MesPlanEntity> page ,MesPlanEntity mesPlanEntity) {
        LambdaQueryWrapper<MesPlanEntity> lambda = new QueryWrapper<MesPlanEntity>().lambda();
        lambda.eq(mesPlanEntity.getMesAdviceId() != null, MesPlanEntity::getMesAdviceId, mesPlanEntity.getMesAdviceId());
        IPage<MesPlanEntity> mesPlanEntityIPage = mesPlanMapper.selectPage(page, lambda);
        return (Page<MesPlanEntity>) mesPlanEntityIPage;
    }


    /**
     * 接受校验结果并更新mes计划实例
     * @param mesPlanEntity
     * @throws Exception
     */
    public void saveAndUpdateByCheck(MesPlanEntity mesPlanEntity) throws Exception {
        LambdaQueryWrapper<MesPlanEntity> lambda = new QueryWrapper<MesPlanEntity>().lambda()
                .eq(mesPlanEntity.getPlanCode() != null, MesPlanEntity::getPlanCode, mesPlanEntity.getPlanCode());
        MesPlanEntity mesPlan = mesPlanMapper.selectOne(lambda);

        if (mesPlan == null){
            throw new Exception("校验状态更新失败，未找到对应计划");
        }else {
            mesPlanMapper.updateById(mesPlanEntity);
        }

        //若完成所有校验则生成可执行计划
        if ("".equals(mesPlan.getFjCheck())){
            mesDoPlanService.create(mesPlan);
        }
    }



}
