package com.jeebase.system.controlSys.baseInfo.device.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jeebase.system.controlSys.baseInfo.device.entity.DeviceEntitly;
import com.jeebase.system.controlSys.baseInfo.location.entity.LocationEntitly;

/**
 * @author DELL
 */
public interface IDeviceService extends IService<DeviceEntitly> {

    DeviceEntitly getDeviceById(String deviceId);
    DeviceEntitly getDevice(DeviceEntitly deviceEntitly);
    Page<DeviceEntitly> deviceList(Page<DeviceEntitly> page, DeviceEntitly deviceEntitly);
    boolean addDevice(DeviceEntitly deviceEntitly);
    boolean updateDevice(DeviceEntitly deviceEntitly);
    boolean deleteDevice(String deviceId);
    boolean bacthDevice(String ids);
}
