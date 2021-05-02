package com.jeebase.system.controlSys.api;

import com.alibaba.fastjson.JSON;
import com.jeebase.system.controlSys.api.entity.*;
import com.jeebase.system.controlSys.api.service.SortingService;
import com.jeebase.system.utils.HttpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/base_url")
public class SortingApi {
    @Autowired
    private SortingService sortingService;


    /**
     * 分拣系统反馈任务解析结果给中控系统
     *
     * @param request_code 随机生成32位UUID
     * @param request_time 请求时间，格式参考约束
     * @param request_data 请求业务参数
     * @return
     */
    @RequestMapping("/PlanAnalysisResults")
    @ResponseBody
    public ResponseResult<List<String>> synSteelPlateInventoryBat(String request_code, String request_name, String request_time, @RequestBody FJPlanList request_data) {
        if (request_code == null || request_time == null || request_data == null) {
            return Response.makeRsp(100, "参数不能为空");
        }
        ArrayList<String> data = new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        try {
            //TODO
            data.add(request_code);
            data.add(formatter.format(date));
            return Response.makeOKRsp(data);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.makeErrRsp("接收计划解析校验结果异常", data);
        }
    }

//    /**
//     * 单零件喷码执行结果接口
//     *
//     * @param request_uuid 随机生成32位UUID
//     * @param request_time 请求时间，格式参考b约束
//     * @param request_data 请求业务参数
//     * @return
//     */
//    @RequestMapping("/PartPrintExecutionResults")
//    @ResponseBody
//    public ResponseResult<List<String>> partPrintExecutionResults(String request_uuid, String request_time, @RequestBody String request_data) {
//        if (request_uuid.isEmpty() || request_time.isEmpty() || request_data.isEmpty()) {
//            return Response.makeRsp(100, "参数不能为空");
//        }
//        ArrayList<String> data = new ArrayList<>();
//        Date date = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//        try {
////            Results results = JSON.parseObject(request_data, Results.class);
////            sortingService.save(results);
//            data.add(request_uuid);
//            data.add(formatter.format(date));
//            return Response.makeOKRsp(data);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Response.makeErrRsp("保存单零件喷码执行结果异常", data);
//        }
//    }

    /**
     * 接收打码完成反馈接口
     *
     * @param request_code 随机生成32位UUID
     * @param request_time 请求时间
     * @param request_data 请求业务参数
     * @return
     */
    @RequestMapping("/PlanPrintExecutionResults")
    @ResponseBody
    public ResponseResult<List<String>> planPrintExecutionResults(String request_code,String request_name, String request_time, @RequestBody PrintResults request_data) {
        if (request_code == null || request_time == null || request_data == null) {
            return Response.makeRsp(100, "参数不能为空");
        }
        ArrayList<String> data = new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            //TODO
            data.add(request_code);
            data.add(formatter.format(date));
            return Response.makeOKRsp(data);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.makeErrRsp("喷码任务执行结果异常", data);
        }
    }

//    /**
//     * 单零件分拣执行结果接口
//     *
//     * @param request_uuid 随机生成32位UUID
//     * @param request_time 请求时间
//     * @param request_data 请求业务参数
//     * @return
//     */
//    @RequestMapping("/PartFJExecutionResults")
//    @ResponseBody
//    public ResponseResult<List<String>> partFJExecutionResults(String request_uuid, String request_time, @RequestBody String request_data) {
//        if (request_uuid.isEmpty() || request_time.isEmpty() || request_data.isEmpty()) {
//            return Response.makeRsp(100, "参数不能为空");
//        }
//        ArrayList<String> data = new ArrayList<>();
//        Date date = new Date();
//        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
//        data.add(request_uuid);
//        try {
//            PartFJResults partFJResults = JSON.parseObject(request_data, PartFJResults.class);
////            sortingService.save(partFJResults);
//            data.add(formatter.format(date));
//            data.add(request_uuid);
//            return Response.makeOKRsp(data);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return Response.makeErrRsp("同步仓库物料异常", data);
//        }
//    }

    /**
     * 分拣任务执行结果接口
     *
     * @param request_code 随机生成32位UUID
     * @param request_time 请求时间
     * @param request_data 请求业务参数
     * @return
     */
    @RequestMapping("/PlanFJExecutionResults")
    @ResponseBody
    public ResponseResult<List<String>> planFJExecutionResults(String request_code, String request_time, @RequestBody String request_data) {
        if (request_code == null || request_time == null || request_data == null) {
            return Response.makeRsp(100, "参数不能为空");
        }
        ArrayList<String> data = new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            PlanFJResults planFJResults = JSON.parseObject(request_data, PlanFJResults.class);
//            sortingService.save(planFJResults);
            data.add(formatter.format(date));
            data.add(request_code);
            return Response.makeOKRsp(data);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.makeErrRsp("同步仓库物料异常", data);
        }
    }

    public void analyzeVerifyFJPlan(List<String> catPlanLists) {
        String url = "";
        Map<String, String> params = new HashMap<>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        for (String catPlanList : catPlanLists) {
            params.put("request_data", JSON.toJSONString(catPlanList));
            params.put("request_time", formatter.format(date));
            String result = HttpUtils.doPost(url, params);
        }
    }

    public String doFJPlan(String request_data) {
        String url = "";
        Map<String, String> params = new HashMap<>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        params.put("request_code", UUID.randomUUID().toString());
        params.put("request_data", JSON.toJSONString(request_data));
        params.put("request_time", formatter.format(date));
        return  HttpUtils.doPost(url, params);
    }
}
