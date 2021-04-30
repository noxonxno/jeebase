package com.jeebase.system.controlSys.api;

import com.alibaba.fastjson.JSON;
import com.jeebase.system.controlSys.api.entity.*;
import com.jeebase.system.controlSys.api.service.CutAnalysisService;
import com.jeebase.system.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/base_url")
public class CutAnalysisApi {
    @Autowired
    private CutAnalysisService cutAnalysisservice;

    /**
     * 保存接收计划解析校验结果
     *
     * @param request_uuid 随机生成32位UUID
     * @param request_time 请求时间，格式参考约束
     * @param request_data 请求业务参数
     * @return
     */
    @RequestMapping(value = "/PlanCutAnalysisResults", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseResult<List<String>> planCutAnalysisResults(String request_uuid, String request_time, @RequestBody CutTask request_data) {
        if (request_uuid == null || request_time == null || request_data == null) {
            return Response.makeRsp(100, "参数不能为空");
        }
        ArrayList<String> data = new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            List<CutTask> plan_list = request_data.getPlan_list();
            for (CutTask cutTask : plan_list) {
                cutAnalysisservice.save(cutTask);
            }
            data.add(request_uuid);
            data.add(formatter.format(date));
            return Response.makeOKRsp(data);
        } catch (Exception e) {
            e.printStackTrace();
            data.add(request_uuid);
            data.add(formatter.format(date));
            return Response.makeErrRsp("保存接收计划解析校验结果异常", data);
        }
    }

    /**
     * 切割任务执行结果接口
     *
     * @param request_uuid 随机生成32位UUID
     * @param request_time 请求时间，格式参考约束
     * @param request_data 请求业务参数
     * @return
     */
    @RequestMapping(value = "/PlanCutExecutionResults",method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseResult<List<String>> planCutExecutionResults(String request_uuid, String request_time, @RequestBody CutAtion request_data) {
        if (request_uuid == null || request_time == null || request_data == null) {
            return Response.makeRsp(100, "参数不能为空");
        }
        ArrayList<String> data = new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
//            cutAnalysisservice.save(planCutResults);
            //TODO
            data.add(request_uuid);
            data.add(formatter.format(date));
            return Response.makeOKRsp(data);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.makeErrRsp("保存单零件喷码执行结果异常", data);
        }
    }
    /**
     * 接收切割机状态接口
     *
     * @param request_uuid 随机生成32位UUID
     * @param request_time 请求时间，格式参考约束
     * @param request_data 请求业务参数
     * @return
     */
    @RequestMapping(value = "/CutMachineStatus",method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseResult<List<String>> cutMachineStatus(String request_uuid, String request_time, @RequestBody CutAtion request_data) {
        if (request_uuid == null || request_time == null || request_data == null) {
            return Response.makeRsp(100, "参数不能为空");
        }
        ArrayList<String> data = new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
//            cutAnalysisservice.save(planCutResults);
            //TODO
            data.add(request_uuid);
            data.add(formatter.format(date));
            return Response.makeOKRsp(data);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.makeErrRsp("保存单零件喷码执行结果异常", data);
        }
    }
    public void analyzeVerifyCuttPlan(String request_data){
        String url = "";
        Map<String,String> params = new HashMap<>();
        List<CutTask> catPlanLists = cutAnalysisservice.selcetCatfileList();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        for (CutTask catPlanList : catPlanLists) {
            params.put("request_uuid",UUID.randomUUID().toString());
            params.put("request_data", JSON.toJSONString(catPlanList));
            params.put("request_time",formatter.format(date));
            String result = HttpUtils.doPost(url, params);
        }
    }
    public void doCuttPlan(String request_data){
        String url = "";
        Map<String,String> params = new HashMap<>();
        List<CutTask> catPlanLists = cutAnalysisservice.selcetCatfileList();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        for (CutTask catPlanList : catPlanLists) {
            params.put("request_uuid",UUID.randomUUID().toString());
            params.put("request_data", JSON.toJSONString(catPlanList));
            params.put("request_time",formatter.format(date));
            String result = HttpUtils.doPost(url, params);
        }
    }
}
