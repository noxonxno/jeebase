package com.jeebase.system.controlSys.planManage.service;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jeebase.system.controlSys.planManage.entity.MesPlanEntity;
import java.util.List;

public interface IMesPlanService extends IService<MesPlanEntity> {


    Page<MesPlanEntity> selectList(Page<MesPlanEntity> page,MesPlanEntity mesPlanEntity);
}
