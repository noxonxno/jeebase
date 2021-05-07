package com.jeebase.system.controlSys.log.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeebase.common.base.PageResult;
import com.jeebase.common.base.Result;
import com.jeebase.system.controlSys.log.entitly.ErrLog;
import com.jeebase.system.controlSys.log.entitly.ErrLog;
import com.jeebase.system.controlSys.log.service.ErrlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @author DELL
 */
@RestController
@RequestMapping("/errlog")
public class ErrLogController {
    @Autowired
    private ErrlogService errlogService;


    /**
     * 查询所有
     *
     * @param page
     * @param errLog
     * @return
     */
    @RequestMapping("/listErrLog")
    public PageResult<ErrLog> listErrLog(@ApiIgnore Page<ErrLog> page, ErrLog errLog) {
        Page<ErrLog> errLogPage = errlogService.listErrLog(errLog, page);
        PageResult<ErrLog> pageResult = new PageResult<>(errLogPage.getTotal(), errLogPage.getRecords());
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
        return new Result<ErrLog>().success().put(errlogService.getById(id));
    }

    /**
     * 新增
     * @param errLog
     * @return
     */
    @RequestMapping("insertErrLog")
    public Result<?> insertErrLog(ErrLog errLog) {
        boolean res = errlogService.insertErrLog(errLog);
        return new Result<>().success(res == true ? "新增成功" : "新增失败");
    }

    /**
     * 修改
     * @param errLog
     * @return
     */
    @RequestMapping("updateErrLog")
    public Result<?> updateErrLog(ErrLog errLog) {
        boolean res = errlogService.updateErrLog(errLog);
        return new Result<>().success(res == true ? "修改成功" : "修改失败");
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @RequestMapping("deleteErrLog")
    public Result<?> deleteErrLog(String id) {
        boolean res = errlogService.deleteErrLog(id);
        return new Result<>().success(res == true ? "删除成功" : "删除失败");
    }

}
