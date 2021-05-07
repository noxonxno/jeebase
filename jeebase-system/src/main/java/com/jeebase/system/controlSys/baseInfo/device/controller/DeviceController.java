package com.jeebase.system.controlSys.baseInfo.device.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeebase.common.base.BusinessException;
import com.jeebase.common.base.PageResult;
import com.jeebase.common.base.Result;
import com.jeebase.system.controlSys.baseInfo.device.entity.DeviceEntitly;
import com.jeebase.system.controlSys.baseInfo.device.service.IDeviceService;
import com.jeebase.system.controlSys.baseInfo.location.entity.LocationEntitly;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * @author DELL
 */
@RestController
@RequestMapping("/device")
public class DeviceController {
    @Autowired
    private IDeviceService iDeviceService;


    @RequestMapping("deviceList")
    public PageResult<DeviceEntitly> deviceList(DeviceEntitly deviceEntitly, @ApiIgnore Page<DeviceEntitly> page) {
        Page<DeviceEntitly> devicePage = iDeviceService.deviceList(page, deviceEntitly);
        PageResult<DeviceEntitly> pageResult = new PageResult<>(devicePage.getTotal(), devicePage.getRecords());
        return pageResult;
    }

    @RequestMapping("getDeviceById")
    public Result<?> getDeviceById(String deviceId) {
        return new Result<DeviceEntitly>().success().put(iDeviceService.getDeviceById(deviceId));
    }

    @RequestMapping("addDevice")
    public Result<?>  addDevice(@RequestBody @Valid DeviceEntitly deviceEntitly) {
        boolean result = iDeviceService.addDevice(deviceEntitly);
        if (result) {
            return new Result<>().success("新增成功");
        } else {
            return new Result<>().error("新增失败");
        }
    }

    @RequestMapping("updateDevice")
    public Result<?>  updateDevice(@RequestBody @Valid DeviceEntitly deviceEntitly) {
        boolean result = iDeviceService.updateDevice(deviceEntitly);
        if (result) {
            return new Result<>().success("修改成功");
        } else {
            return new Result<>().error("修改失败");
        }
    }

    /**
     *
     * @param deviceId
     * @return
     */
    @RequestMapping("deleteDevice/{deviceId}")
    public Result<?>  deleteDevice(@PathVariable String deviceId) {
        boolean result = iDeviceService.deleteDevice(deviceId);
        if (result) {
            return new Result<>().success("删除成功");
        } else {
            return new Result<>().error("删除失败");
        }
    }

    @RequestMapping("bacthDevice")
    public Result<?>  bacthDevice(String ids) {

        if (ids.isEmpty() || ("").equals(ids)) {
            throw new BusinessException("id不能为空");
        }
        boolean result = iDeviceService.bacthDevice(ids);
        if (result) {
            return new Result<>().success("删除成功");
        } else {
            return new Result<>().error("删除失败");
        }
    }

    @RequestMapping("selectDeviceCodeMap")
    public Result<?> selectDeviceCodeMap(){
        return new Result<>().success().put(iDeviceService.getDeviceName());
    }
}
