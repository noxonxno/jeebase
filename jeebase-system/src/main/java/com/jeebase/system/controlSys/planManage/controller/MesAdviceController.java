package com.jeebase.system.controlSys.planManage.controller;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeebase.common.annotation.log.AroundLog;
import com.jeebase.common.base.PageResult;
import com.jeebase.common.base.Result;
import com.jeebase.system.controlSys.planManage.entity.MesAdviceEntity;
import com.jeebase.system.controlSys.planManage.service.IMesAdviceService;
import com.jeebase.system.controlSys.planManage.service.IMesPlanService;
import com.jeebase.system.controlSys.planManage.entity.MesPlanEntity;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.List;

@RestController
@RequestMapping("/mesAdvice")
public class MesAdviceController {

    @Autowired
    private IMesPlanService mesPlanService;

    @Autowired
    private IMesAdviceService mesAdviceService;


    /**
     * 按条件查询列表
     * @param mesAdviceEntity
     * @return
     */
    @GetMapping("/list")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "查询通知")
    public PageResult<MesAdviceEntity> mesAdviceList(MesAdviceEntity mesAdviceEntity, Page<MesAdviceEntity> page){
        Page<MesAdviceEntity> mesAdviceEntityPage = mesAdviceService.selectList(page,mesAdviceEntity);
        return new PageResult<>(mesAdviceEntityPage.getTotal(),mesAdviceEntityPage.getRecords());
    }
    /**
     * 详情展示/查看所属通知的所有mes计划
     * @param mesAdviceId
     * @return
     */
    @GetMapping("/details")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "查询详情")
    @AroundLog(name = "查询详情")
    public PageResult<MesPlanEntity> findMesPlanByAdviceId(@NotBlank String mesAdviceId,Page<MesPlanEntity> page){
        MesPlanEntity mesPlanEntity = new MesPlanEntity();
        mesPlanEntity.setMesAdviceId(mesAdviceId);
        Page<MesPlanEntity> mesPlanEntityPage = mesPlanService.selectList(page, mesPlanEntity);
        return new PageResult<>(mesPlanEntityPage.getTotal(),mesPlanEntityPage.getRecords());
    }

    /**
     * 添加通知
     */
    @PostMapping("/create")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "添加通知")
    @AroundLog(name = "添加通知")
    public Result<MesAdviceEntity> create(@RequestBody MesAdviceEntity mesAdviceEntity) {

        boolean result = mesAdviceService.save(mesAdviceEntity);
        if (result) {
            return new Result<MesAdviceEntity>().success("添加成功").put(mesAdviceEntity);
        } else {
            return new Result<MesAdviceEntity>().error("添加失败，请重试");
        }
    }


    /**
     * 修改通知
     */
    @PostMapping("/update")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "更新通知")
    @AroundLog(name = "更新通知")
    public Result<MesAdviceEntity> update(@RequestBody MesAdviceEntity mesAdviceEntity) {

         boolean result = mesAdviceService.updateById(mesAdviceEntity);
        if (result) {
            return new Result<MesAdviceEntity>().success("修改成功").put(mesAdviceEntity);
        } else {
            return new Result<MesAdviceEntity>().error("修改失败");
        }
    }


    /**
     * 删除通知
     */
    @PostMapping("/delete/{mesAdviceId}")
    @RequiresRoles("SYSADMIN")
    @ApiOperation(value = "删除通知")
    @AroundLog(name = "删除通知")
    @ApiImplicitParam(paramType = "path", name = "mesAdviceId", value = "通知id", required = true, dataType = "String")
    public Result<?> delete(@PathVariable("mesAdviceId") String mesAdviceId) {

        boolean result = mesAdviceService.removeById(mesAdviceId);
        if (result) {
            return new Result<>().success("删除成功");
        } else {
            return new Result<>().error("删除失败");
        }
    }

    /**
     * 计划导入byExl
     * @param
     * @return
     */
    @RequestMapping("/importExcel")
    public Result<?> importAndSave(@RequestBody List<MesAdviceEntity> mesAdviceEntities){
        if (mesAdviceEntities.size()<=0){
            return new Result<>().error("批量导入失败,计划为空");
        }
        if (mesAdviceService.saveBatch(mesAdviceEntities)){
            return new Result<>().success("批量导入成功");
        }
        return new Result<>().error("批量导入失败");
    }
}
