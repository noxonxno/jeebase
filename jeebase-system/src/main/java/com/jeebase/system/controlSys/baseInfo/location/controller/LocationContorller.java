package com.jeebase.system.controlSys.baseInfo.location.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeebase.common.base.BusinessException;
import com.jeebase.common.base.PageResult;
import com.jeebase.common.base.Result;
import com.jeebase.system.controlSys.baseInfo.location.entity.LocationEntitly;
import com.jeebase.system.controlSys.baseInfo.location.service.ILocationService;
import com.jeebase.system.utils.GetUserUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/location")
public class LocationContorller {

    @Autowired
    private ILocationService iLocationService;

    /**
     * 分页查询所有
     *
     * @param locationEntitly
     * @return
     */
    @RequestMapping("locationList")
    public PageResult<LocationEntitly> locationList(LocationEntitly locationEntitly, @ApiIgnore Page<LocationEntitly> page) {
        Page<LocationEntitly> pageLocation = iLocationService.locationList(page, locationEntitly);
        PageResult<LocationEntitly> pageResult = new PageResult<>(pageLocation.getTotal(), pageLocation.getRecords());
        return pageResult;
    }

    /**
     * 通过id查找
     *
     * @param locationEntitly
     * @return
     */
    @RequestMapping(value = "getLocationById", method = RequestMethod.POST)
    public Result<?> getLocationById(@RequestBody LocationEntitly locationEntitly) {
        return new Result<LocationEntitly>().success().put(iLocationService.getLocationById(locationEntitly));
    }

    /**
     * 删除当前库位
     *
     * @param locationId
     * @return
     */
    @RequestMapping("/removeLocation/{locationId}")
    public Result<?> removeLocation(@PathVariable("locationId") String locationId) {
        boolean result = iLocationService.removeLocation(locationId);
        if (result) {
            return new Result<>().success("删除成功");
        } else {
            return new Result<>().error("删除失败");
        }
    }

    /**
     * 新增库位
     *
     * @param locationEntitly
     * @return
     */
    @RequestMapping("addLocation")
    public Result<?> addLocation(@RequestBody LocationEntitly locationEntitly) {
        boolean result = iLocationService.addLocation(locationEntitly);
        if (result) {
            return new Result<>().success("新增成功");
        } else {
            return new Result<>().error("新增失败");
        }
    }

    /**
     * 修改库位
     *
     * @param locationEntitly
     * @return
     */
    @RequestMapping("updateLocation")
    public Result<?> updateLocation(@RequestBody LocationEntitly locationEntitly) {
        boolean result = iLocationService.updateLocation(locationEntitly);
        if (result) {
            return new Result<>().success("修改成功");
        } else {
            return new Result<>().error("修改失败");
        }
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping("batchRemove")
    public Result<?> batchRemove(String ids) {
        if ("".equals(ids) || ids == null || ids.isEmpty()) {
            throw new BusinessException("id不能为空");
        }
        boolean result = iLocationService.batchRemove(ids);
        if (result) {
            return new Result<>().success("修改成功");
        } else {
            return new Result<>().error("修改失败");
        }
    }
}
