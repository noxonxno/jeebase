package com.jeebase.system.controlSys.planManage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jeebase.system.controlSys.planManage.entity.MesDoPlanEntity;

import java.util.List;

public interface IMesDoPlanService extends IService<MesDoPlanEntity> {

    Page<MesDoPlanEntity> selectList(Page<MesDoPlanEntity> page,MesDoPlanEntity mesDoPlanEntity);

    boolean saveAndUpdateByTask();

}
