package com.jeebase.system.controlSys.baseInfo.apiInfo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeebase.common.base.BusinessException;
import com.jeebase.common.base.PageResult;
import com.jeebase.common.base.Result;
import com.jeebase.system.controlSys.baseInfo.apiInfo.entity.ApiInfoEntitly;
import com.jeebase.system.controlSys.baseInfo.apiInfo.service.IApiService;
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
@RequestMapping("/api")
public class ApiController {

    @Autowired
    private IApiService iApiService;


    @RequestMapping("apiList")
    public PageResult<ApiInfoEntitly> apiList(ApiInfoEntitly apiInfoEntitly,
                                              @ApiIgnore Page<ApiInfoEntitly> page) {

        Page<ApiInfoEntitly> apiInfoEntitlyPage = iApiService.apiList(page,apiInfoEntitly);

        PageResult<ApiInfoEntitly> pageResult = new PageResult<>(apiInfoEntitlyPage.getTotal(), apiInfoEntitlyPage.getRecords());

        return pageResult;
    }

    @RequestMapping("getApiInfoById")
    public Result<?> getApiInfoById(@RequestBody ApiInfoEntitly apiInfoEntitly) {

        return new Result<ApiInfoEntitly>().success().put(iApiService.getApiInfoById(apiInfoEntitly.getApiId()));

    }

    @RequestMapping("addApiInfo")
    public Result<?>  addApiInfo(@RequestBody @Valid ApiInfoEntitly apiInfoEntitly) {

        boolean result = iApiService.addApiInfo(apiInfoEntitly);
        if (result) {
            return new Result<>().success("新增成功");
        } else {
            return new Result<>().error("新增失败");
        }
    }


    @RequestMapping("updateApiInfo")
    public Result<?>  updateApiInfo(@RequestBody @Valid ApiInfoEntitly apiInfoEntitly) {

        boolean result = iApiService.updateApiInfo(apiInfoEntitly);
        if (result) {
            return new Result<>().success("修改成功");
        } else {
            return new Result<>().error("修改失败");
        }
    }


    @RequestMapping("deleteApiInfo/{apiId}")
    public Result<?>  deleteApiInfo(@PathVariable String apiId) {

        boolean result = iApiService.deleteApiInfo(apiId);
        if (result) {
            return new Result<>().success("删除成功");
        } else {
            return new Result<>().error("删除失败");
        }
    }


    @RequestMapping("batchDeleleApiInfo")
    public Result<?>  batchDeleleApiInfo(String apiIds) {

        if (("").equals(apiIds) || apiIds.isEmpty()) {
            throw new BusinessException("id不能为空");
        }
        boolean result = iApiService.batchDeleleApiInfo(apiIds);
        if (result) {
            return new Result<>().success("删除成功");
        } else {
            return new Result<>().error("删除失败");
        }
    }

    @RequestMapping("testApiInfo")
    public Result<?>  testApiInfo(@RequestBody ApiInfoEntitly apiInfoEntitly) throws Exception {

        return new Result<>().success(iApiService.testApiInfo(apiInfoEntitly));
    }

    @RequestMapping("test")
    public Result<?> test(){
        return new Result<>().success();
    }
}
