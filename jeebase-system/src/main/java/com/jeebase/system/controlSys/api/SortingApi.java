package com.jeebase.system.controlSys.api;

import com.alibaba.fastjson.JSON;
import com.jeebase.system.controlSys.api.entity.*;
import com.jeebase.system.utils.HttpUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/base_url")
public class SortingApi {


    /**
     * 中控系统接收套料图解析结果接口
     *
     * @return
     */
    @RequestMapping("/PlanAnalysisResults")
    @ResponseBody
    public ResponseResult<List<String>> synSteelPlateInventoryBat(@RequestBody SortingApiPlanAnalysisResults data) {
        if (data.getRequest_code() == null || data.getRequest_time() == null || data.getRequest_data() == null) {
            return Response.makeRsp(100, "参数不能为空");
        }
        ArrayList<String> response_data = new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

        try {
            //TODO
            response_data.add(data.getRequest_code());
            response_data.add(formatter.format(response_data));
            return Response.makeOKRsp(response_data);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.makeErrRsp("接收计划解析校验结果异常", response_data);
        }
    }



    /**
     * 中控系统接收打码完成反馈接口
     *
     * @param data 请求业务参数
     * @return
     */
    @RequestMapping("/PlanPrintExecutionResults")
    @ResponseBody
    public ResponseResult<List<String>> planPrintExecutionResults(@RequestBody SortingApiPlanPrintExecutionResults data) {
        if (data.getRequest_code() == null || data.getRequest_time() == null || data.getRequest_data() == null) {
            return Response.makeRsp(100, "参数不能为空");
        }
        ArrayList<String> response_data = new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            PrintResults request_data = data.getRequest_data();
            //TODO
            response_data.add(data.getRequest_code());
            response_data.add(formatter.format(date));
            return Response.makeOKRsp(response_data);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.makeErrRsp("接受喷码任务执行结果异常", response_data);
        }
    }

    /**
     * 中控系统接收分拣完成反馈接口
     *
     * @param data 请求业务参数
     * @return
     */
    @RequestMapping("/PlanFJExecutionResults")
    @ResponseBody
    public ResponseResult<List<String>> planFJExecutionResults(@RequestBody SortingApiPlanFJExecutionResults data) {
        if (data.getRequest_code() == null || data.getRequest_time() == null || data.getRequest_data() == null) {
            return Response.makeRsp(100, "参数不能为空");
        }
        ArrayList<String> response_data = new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            response_data.add(formatter.format(date));
            response_data.add(data.getRequest_code());
            return Response.makeOKRsp(response_data);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.makeErrRsp("接受分拣任务执行结果异常", response_data);
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
