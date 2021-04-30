package com.jeebase.system.controlSys.baseInfo.plcInfo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jeebase.system.controlSys.baseInfo.plcInfo.entity.PlcinfoEntitly;

/**
 * @author DELL
 */
public interface IPlcInfoService extends IService<PlcinfoEntitly> {

    Page<PlcinfoEntitly> listPlcInfo(Page<PlcinfoEntitly> page, PlcinfoEntitly plcinfoEntitly);

    PlcinfoEntitly getPlcInfoById(String plcId);

    boolean addPlcInfo(PlcinfoEntitly plcinfoEntitly);

    boolean updatePlcInfo(PlcinfoEntitly plcinfoEntitly);

    boolean deletePlcInfo(String plcId);

    boolean bacthDeletePlcInfo(String plcIds);
}
