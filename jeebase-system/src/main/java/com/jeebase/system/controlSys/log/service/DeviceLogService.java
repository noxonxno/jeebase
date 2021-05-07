package com.jeebase.system.controlSys.log.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jeebase.system.controlSys.log.entitly.DeviceLog;
import com.jeebase.system.controlSys.log.mapper.DeviceLogMapper;

/**
 * @author DELL
 */
public interface DeviceLogService extends IService<DeviceLog> {
    /**
     * 分页查询所有
     *
     * @param deviceLog
     * @param page
     * @return
     */
    Page<DeviceLog> listDeviceLog(DeviceLog deviceLog, Page<DeviceLog> page);

    /**
     * +
     * 通过id获取
     *
     * @param id
     * @return
     */
    DeviceLog getById(String id);

    /**
     * 新增
     *
     * @param deviceLog
     * @return
     */
    boolean insertDeviceLog(DeviceLog deviceLog);

    /**
     * 修改
     *
     * @param deviceLog
     * @return
     */
    boolean updateDeviceLog(DeviceLog deviceLog);

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    boolean deleteDeviceLog(String id);

}
