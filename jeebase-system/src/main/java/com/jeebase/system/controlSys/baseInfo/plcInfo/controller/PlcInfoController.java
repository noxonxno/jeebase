package com.jeebase.system.controlSys.baseInfo.plcInfo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeebase.common.base.BusinessException;
import com.jeebase.common.base.PageResult;
import com.jeebase.common.base.Result;
import com.jeebase.system.controlSys.baseInfo.location.entity.LocationEntitly;
import com.jeebase.system.controlSys.baseInfo.plcInfo.entity.PlcinfoEntitly;
import com.jeebase.system.controlSys.baseInfo.plcInfo.service.IPlcInfoService;
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
@RequestMapping("/plc")
public class PlcInfoController {

    @Autowired
    private IPlcInfoService iPlcInfoService;

    /**
     * 查询所有
     * @param plcEntity
     * @param page
     * @return
     */
    @RequestMapping("listPlcInfo")
    public PageResult<PlcinfoEntitly> listPlcInfo(PlcinfoEntitly plcEntity, @ApiIgnore Page<PlcinfoEntitly> page) {
        Page<PlcinfoEntitly> pageLocation = iPlcInfoService.listPlcInfo(page,plcEntity);
        PageResult<PlcinfoEntitly> pageResult = new PageResult<>(pageLocation.getTotal(), pageLocation.getRecords());
        return pageResult;
    }

    /**
     * 通过id获取
     *
     * @param plcinfoEntitly
     * @return
     */
    @RequestMapping("getPlcInfoById")
    public Result<?> getPlcInfoById(@RequestBody PlcinfoEntitly plcinfoEntitly) {

        return new Result<PlcinfoEntitly>().success().put(iPlcInfoService.getPlcInfoById(plcinfoEntitly.getPlcId()));
    }

    /**
     * +
     * 新增
     *
     * @param plcinfoEntitly
     * @return
     */
    @RequestMapping("addPlcInfo")
    public Result<?>  addPlcInfo(@RequestBody @Valid PlcinfoEntitly plcinfoEntitly) {
        boolean result = iPlcInfoService.addPlcInfo(plcinfoEntitly);
        if (result) {
            return new Result<>().success("新增成功");
        } else {
            return new Result<>().error("新增失败");
        }
    }

    /**
     * 修改
     *
     * @param plcinfoEntitly
     * @return
     */
    @RequestMapping("updatePlcInfo")
    public Result<?>  updatePlcInfo(@RequestBody @Valid PlcinfoEntitly plcinfoEntitly) {
        boolean result = iPlcInfoService.updatePlcInfo(plcinfoEntitly);
        if (result) {
            return new Result<>().success("修改成功");
        } else {
            return new Result<>().error("修改失败");
        }
    }

    /**
     * 删除
     *
     * @param plcId
     * @return
     */
    @RequestMapping("deletePlcInfo/{plcId}")
    public Result<?>  deletePlcInfo(@PathVariable String plcId) {
        boolean result = iPlcInfoService.deletePlcInfo(plcId);
        if (result) {
            return new Result<>().success("删除成功");
        } else {
            return new Result<>().error("删除失败");
        }
    }

    /**
     * 批量删除
     *
     * @param plcIds
     * @return
     */
    @RequestMapping("bacthDeletePlcInfo")
    public Result<?>  bacthDeletePlcInfo(String plcIds) {
        if (plcIds.isEmpty() || ("").equals(plcIds)) {
            throw new BusinessException("id不能为空");
        }
        boolean result = iPlcInfoService.bacthDeletePlcInfo(plcIds);
        if (result) {
            return new Result<>().success("删除成功");
        } else {
            return new Result<>().error("删除失败");
        }
    }
}
