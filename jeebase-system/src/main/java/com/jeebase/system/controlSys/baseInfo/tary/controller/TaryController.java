package com.jeebase.system.controlSys.baseInfo.tary.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeebase.common.base.BusinessException;
import com.jeebase.common.base.PageResult;
import com.jeebase.common.base.Result;
import com.jeebase.system.controlSys.baseInfo.location.entity.LocationEntitly;
import com.jeebase.system.controlSys.baseInfo.tary.entity.TaryEntitly;
import com.jeebase.system.controlSys.baseInfo.tary.service.ITaryService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;

/**
 * @author DELL
 */
@RestController
@RequestMapping("/tary")
public class TaryController {

    @Autowired
    private ITaryService iTaryService;

    /**
     * 查询所有
     *
     * @param tary
     * @return
     */
    @RequestMapping("/list")
    public PageResult<TaryEntitly> list(TaryEntitly tary, @ApiIgnore Page<TaryEntitly> page) {
        Page<TaryEntitly> pageTray = iTaryService.trayList(page, tary);
        PageResult<TaryEntitly> pageResult = new PageResult<>(pageTray.getTotal(), pageTray.getRecords());
        return pageResult;
    }

    /**
     * 根据id查询
     *
     * @param tary
     * @return
     */
    @RequestMapping(value = "getTaryById", method = RequestMethod.POST)
    public Result<?> getTaryById(@RequestBody TaryEntitly tary) {
        return new Result<TaryEntitly>().success().put(iTaryService.getTaryEntityById(tary));
    }

    /**
     * 修改
     *
     * @param tary
     * @return
     */
    @RequestMapping(value = "updateTary", method = RequestMethod.POST)
    public Result<?> updateTary(@RequestBody @Valid TaryEntitly tary) {
        boolean result = iTaryService.updateTary(tary);
        if (result) {
            return new Result<>().success("删除成功");
        } else {
            return new Result<>().error("删除失败");
        }
    }

    /**
     * 新增
     *
     * @param tary
     * @return
     */
    @RequestMapping(value = "addTary", method = RequestMethod.POST)
    public Result<?> addTary(@RequestBody @Valid TaryEntitly tary) {
        boolean result = iTaryService.addTary(tary);
        if (result) {
            return new Result<>().success("新增成功");
        } else {
            return new Result<>().error("新增失败");
        }
    }

    /**
     * 根据id删除
     *
     * @param taryId
     * @return
     */
    @RequestMapping(value = "/deleteTaryById/{taryId}", method = RequestMethod.POST)
    public Result<?> deleteTaryById(@PathVariable String taryId) {
        boolean result = iTaryService.removeTary(taryId);
        if (result) {
            return new Result<>().success("删除成功");
        } else {
            return new Result<>().error("删除失败");
        }
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "batchDeleteTaryByid", method = RequestMethod.POST)
    public Result<?> batchDeleteTaryByid(String ids) {
        if ("".equals(ids) || ids == null || ids.isEmpty()) {
            throw new BusinessException("id不能为空");
        }
        boolean result = iTaryService.batchRemove(ids);
        if (result) {
            return new Result<>().success("删除成功");
        } else {
            return new Result<>().error("删除失败");
        }
    }


    /**
     * 批量修改阈值
     *
     * @param taryFixNum
     * @return
     */
    @RequestMapping(value = "batchUpdate/{taryFixNum}", method = RequestMethod.POST)
    public Result<?> batchUpdate(@PathVariable String taryFixNum) {
        if ("".equals(taryFixNum) || taryFixNum == null || taryFixNum.isEmpty()) {
            throw new BusinessException("阈值不能为空");
        }
        boolean result = iTaryService.batchUpdate(taryFixNum);
        if (result) {
            return new Result<>().success("修改成功");
        } else {
            return new Result<>().error("修改失败");
        }
    }
}
