package com.jeebase.system.controlSys.baseInfo.part.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeebase.common.base.PageResult;
import com.jeebase.common.base.Result;
import com.jeebase.system.controlSys.baseInfo.part.entity.PartEntitly;
import com.jeebase.system.controlSys.baseInfo.part.service.IPartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author DELL
 */
@RestController
@RequestMapping("/part")
public class PartController {

    @Autowired
    private IPartService iPartService;

    /**
     *  mobile分页查询
     * @param page
     * @param partEntitly
     * @return
     */
    @RequestMapping("partList")
    public PageResult<PartEntitly> partList(Page<PartEntitly> page, PartEntitly partEntitly){
        Page<PartEntitly> partEntitlyPage = iPartService.partList(page, partEntitly);
        PageResult<PartEntitly> pageResult = new PageResult<>(partEntitlyPage.getTotal(), partEntitlyPage.getRecords());
        return pageResult;
    }

    /**
     *  mobile分页查询
     * @param page
     * @param partEntitly
     * @return
     */
    @RequestMapping("pcPartList")
    public PageResult<PartEntitly> pcPartList(Page<PartEntitly> page, PartEntitly partEntitly){
        Page<PartEntitly> partEntitlyPage = iPartService.pcPartList(page, partEntitly);
        PageResult<PartEntitly> pageResult = new PageResult<>(partEntitlyPage.getTotal(), partEntitlyPage.getRecords());
        return pageResult;
    }

    /**
     * mb新增
     * @param partEntitly
     * @return
     */
    @RequestMapping("addPart")
    public Result<?> addPart(PartEntitly partEntitly){
        boolean result = iPartService.addPart(partEntitly);
        if (result) {
            return new Result<>().success("新增成功");
        } else {
            return new Result<>().error("新增失败");
        }
    }

    /**
     * mb新增
     * @param partEntitly
     * @return
     */
    @RequestMapping("pcAddPart")
    public Result<?> pcAddPart(@RequestBody @Valid PartEntitly partEntitly){
        boolean result = iPartService.pcAddPart(partEntitly);
        if (result) {
            return new Result<>().success("新增成功");
        } else {
            return new Result<>().error("新增失败");
        }
    }

    /**
     * 修改
     * @param partEntitly
     * @return
     */
    @RequestMapping("updatePart")
    public Result<?>  updatePart(@RequestBody @Valid PartEntitly partEntitly){
        boolean result = iPartService.updatePart(partEntitly);
        if (result) {
            return new Result<>().success("修改成功");
        } else {
            return new Result<>().error("修改失败");
        }
    }



    /**
     * 删除
     * @param partId
     * @return
     */
    @RequestMapping("removePart/{partId}")
    public Result<?>  removePart(@PathVariable("partId")  String partId){
        boolean result = iPartService.removePart(partId);
        if (result) {
            return new Result<>().success("删除成功");
        } else {
            return new Result<>().error("删除失败");
        }
    }
}
