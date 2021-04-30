package com.jeebase.system.controlSys.baseInfo.plcInfo.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeebase.common.base.BusinessException;
import com.jeebase.system.controlSys.baseInfo.device.entity.DeviceEntitly;
import com.jeebase.system.controlSys.baseInfo.device.service.IDeviceService;
import com.jeebase.system.controlSys.baseInfo.location.entity.LocationEntitly;
import com.jeebase.system.controlSys.baseInfo.plcInfo.entity.PlcinfoEntitly;
import com.jeebase.system.controlSys.baseInfo.plcInfo.mapper.IPlcInfoMapper;
import com.jeebase.system.controlSys.baseInfo.plcInfo.service.IPlcInfoService;
import com.jeebase.system.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

/**
 * @author DELL
 */
@Service
public class PlcInfoServiceImpl extends ServiceImpl<IPlcInfoMapper, PlcinfoEntitly>
        implements IPlcInfoService {

    @Autowired
    private IPlcInfoMapper iPlcInfoMapper;
    @Autowired
    private IDeviceService iDeviceService;

    @Override
    public Page<PlcinfoEntitly> listPlcInfo(Page<PlcinfoEntitly> page, PlcinfoEntitly plc) {

        QueryWrapper<PlcinfoEntitly> wrapper = new QueryWrapper<PlcinfoEntitly>();
        wrapper.like(plc.getPlcName() != null && !(("").equals(plc.getPlcName())), "plc_name", plc.getPlcName())
                .eq(plc.getDeviceCode() != null && !(("").equals(plc.getDeviceCode())), "g_code", plc.getDeviceCode());
        IPage<PlcinfoEntitly> plainEntitleList = iPlcInfoMapper.selectPage(page, wrapper);

        return (Page<PlcinfoEntitly>) plainEntitleList;
    }

    @Override
    public PlcinfoEntitly getPlcInfoById(String plcId) {
        return iPlcInfoMapper.selectById(plcId);
    }

    @Override
    public boolean addPlcInfo(PlcinfoEntitly plcinfoEntitly) {
        //通过设备编号 查询设备id 反写点位表字段
        DeviceEntitly deviceEntitly = new DeviceEntitly();
        deviceEntitly.setDeviceCode(plcinfoEntitly.getDeviceCode());
        DeviceEntitly device = iDeviceService.getDevice(deviceEntitly);

        plcinfoEntitly.setDeviceId(device.getDeviceId());
        plcinfoEntitly.setPlcId(UUIDUtils.getUUID32());
        return save(plcinfoEntitly);
    }

    @Override
    public boolean updatePlcInfo(PlcinfoEntitly plcinfoEntitly) {

        return saveOrUpdate(plcinfoEntitly);
    }

    @Override
    public boolean deletePlcInfo(String plcId) {
        return iPlcInfoMapper.deleteById(plcId) > 0 ? true : false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean bacthDeletePlcInfo(String plcIds) {
        String[] ids = plcIds.split(",");
        List idList = Arrays.asList(ids);
        if (idList.isEmpty()) {
            throw new BusinessException("id不能为空");
        }
        int plcList = iPlcInfoMapper.selectBatchIds(idList).size();
        try {
            int deleteList = iPlcInfoMapper.deleteBatchIds(idList);
            if (plcList == deleteList) {
                return true;
            } else {
                throw new BusinessException("删除未能成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


}
