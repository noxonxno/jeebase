package com.jeebase.system.controlSys.api;

import com.alibaba.fastjson.JSON;
import com.jeebase.system.controlSys.api.entity.*;
import com.jeebase.system.controlSys.api.service.CutAnalysisService;
import com.jeebase.system.utils.HttpUtils;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.swing.plaf.synth.SynthOptionPaneUI;
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
     * @return
     */
    @RequestMapping(value = "/PlanCutAnalysisResults", method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseResult<List<String>> planCutAnalysisResults(@RequestBody CutAnalysisApiPlanCutAnalysisResults data) {
        if (data.getRequest_uuid() == null || data.getRequest_time() == null || data.getRequest_data() == null) {
            return Response.makeRsp(100, "参数不能为空");
        }
        ArrayList<String> response_data = new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            CutTask request_data = data.getRequest_data();
            for (CutTask cutTask : request_data.getPlan_list()) {
                cutAnalysisservice.save(cutTask);
            }
            response_data.add(data.getRequest_uuid());
            response_data.add(formatter.format(date));
            return Response.makeOKRsp(response_data);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.makeErrRsp("保存接收计划解析校验结果异常", response_data);
        }
    }

    /**
     * 切割任务执行结果接口
     *
     * @return
     */
    @RequestMapping(value = "/PlanCutExecutionResults",method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseResult<List<String>> planCutExecutionResults( @RequestBody CutAnalysisApiPlanCutExecutionResults data) {
        if (data.getRequest_uuid() == null || data.getRequest_time() == null || data.getRequest_data() == null) {
            return Response.makeRsp(100, "参数不能为空");
        }
        ArrayList<String> response_data = new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            CutAtion request_data = data.getRequest_data();
            //TODO
            response_data.add(data.getRequest_uuid());
            response_data.add(formatter.format(date));
            return Response.makeOKRsp(response_data);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.makeErrRsp("保存接收计划解析校验结果异常", response_data);
        }
    }
    /**
     * 接收切割机状态接口
     *

     * @return
     */
    @RequestMapping(value = "/CutMachineStatus",method = RequestMethod.POST, consumes = "application/json")
    @ResponseBody
    public ResponseResult<List<String>> cutMachineStatus( @RequestBody CutAnalysisApiCutMachineStatus data) {
        if (data.getRequest_uuid() == null || data.getRequest_time() == null || data.getRequest_data() == null) {
            return Response.makeRsp(100, "参数不能为空");
        }
        ArrayList<String> response_data = new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            List<MachineStatus> request_data = data.getRequest_data();
            for (MachineStatus request_datum : request_data) {
                //TODO
            }
            response_data.add(data.getRequest_uuid());
            response_data.add(formatter.format(date));
            return Response.makeOKRsp(response_data);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.makeErrRsp("保存单零件喷码执行结果异常", response_data);
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
    public void doCutPlan(String request_data){
        String url = "";
        Map<String,String> params = new HashMap<>();
        List<CutTask> catPlanList = cutAnalysisservice.selcetCatfileList();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        for (CutTask catPlan : catPlanList) {
            params.put("request_uuid",UUID.randomUUID().toString());
            params.put("request_data", JSON.toJSONString(catPlan));
            params.put("request_time",formatter.format(date));
            String result = HttpUtils.doPost(url, params);
        }
    }
}
