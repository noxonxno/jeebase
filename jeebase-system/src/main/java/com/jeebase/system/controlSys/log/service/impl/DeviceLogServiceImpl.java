package com.jeebase.system.controlSys.log.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeebase.system.controlSys.log.entitly.DeviceLog;
import com.jeebase.system.controlSys.log.mapper.DeviceLogMapper;
import com.jeebase.system.controlSys.log.service.DeviceLogService;
import com.jeebase.system.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author DELL
 */
@Service
public class DeviceLogServiceImpl extends ServiceImpl<DeviceLogMapper, DeviceLog>
        implements DeviceLogService {

    @Autowired
    private DeviceLogMapper deviceLogMapper;

    @Override
    public Page<DeviceLog> listDeviceLog(DeviceLog deviceLog, Page<DeviceLog> page) {
        QueryWrapper<DeviceLog> wrapper = new QueryWrapper<>();


        IPage<DeviceLog> iPage = deviceLogMapper.selectPage(page, wrapper);
        return (Page<DeviceLog>) iPage;
    }

    @Override
    public DeviceLog getById(String id) {
        return deviceLogMapper.selectById(id);
    }

    @Override
    public boolean insertDeviceLog(DeviceLog deviceLog) {
        deviceLog.setLogId(UUIDUtils.getUUID32());
        return save(deviceLog);
    }

    @Override
    public boolean updateDeviceLog(DeviceLog deviceLog) {
        return saveOrUpdate(deviceLog);
    }

    @Override
    public boolean deleteDeviceLog(String id) {
        return deviceLogMapper.deleteById(id) > 0 ? true : false;
    }
}
