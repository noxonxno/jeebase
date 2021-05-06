package com.jeebase.system.controlSys.log.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeebase.common.base.PageResult;
import com.jeebase.common.base.Result;
import com.jeebase.system.controlSys.log.entitly.DeviceLog;
import com.jeebase.system.controlSys.log.service.DeviceLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author DELL
 */
@RestController
@RequestMapping("/devicelog")
public class DeviceLogController {
    @Autowired
    private DeviceLogService deviceLogService;

    /**
     * 查询所有
     *
     * @param page
     * @param deviceLog
     * @return
     */
    @RequestMapping("/listDeviceLog")
    public PageResult<DeviceLog> listDeviceLog(@ApiIgnore Page<DeviceLog> page, DeviceLog deviceLog) {
        Page<DeviceLog> deviceLogPage = deviceLogService.listDeviceLog(deviceLog, page);
        PageResult<DeviceLog> pageResult = new PageResult<>(deviceLogPage.getTotal(), deviceLogPage.getRecords());
        return pageResult;
    }


    /**
     * 通过id查找
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "getById", method = RequestMethod.POST)
    public Result<?> getById(String id) {
        return new Result<DeviceLog>().success().put(deviceLogService.getById(id));
    }

    /**
     * 新增
     * @param deviceLog
     * @return
     */
    @RequestMapping("insertDeviceLog")
    public Result<?> insertDeviceLog(DeviceLog deviceLog) {
        boolean res = deviceLogService.insertDeviceLog(deviceLog);
        return new Result<>().success(res == true ? "新增成功" : "新增失败");
    }

    /**
     * 修改
     * @param deviceLog
     * @return
     */
    @RequestMapping("updateDeviceLog")
    public Result<?> updateDeviceLog(DeviceLog deviceLog) {
        boolean res = deviceLogService.updateDeviceLog(deviceLog);
        return new Result<>().success(res == true ? "修改成功" : "修改失败");
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("deleteDeviceLog")
    public Result<?> deleteDeviceLog(String id) {
        boolean res = deviceLogService.deleteDeviceLog(id);
        return new Result<>().success(res == true ? "删除成功" : "删除失败");
    }
}
