package com.jeebase.system.controlSys.api;

import com.jeebase.system.controlSys.api.entity.*;
import com.jeebase.system.controlSys.api.service.WMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jeebase.system.utils.HttpUtils;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/base_url")
public class WMSApi {
    @Autowired
    private WMSService wmsService;

    @RequestMapping("/SynSteelPlateInventoryBat")
    public ResponseResult<List<String>> synSteelPlateInventoryBat(String request_uuid, String request_time, Steel request_data) {
        if (request_uuid == null || request_time == null || request_data == null) {
            return Response.makeRsp(100, "参数不能为空");
        }
        ArrayList<String> data = new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            List<Steel> plan_list = request_data.getSteel_list();
            for (Steel steel : plan_list) {
                wmsService.save(steel);
            }
            data.add(request_uuid);
            data.add(formatter.format(date));
            return Response.makeOKRsp(data);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.makeErrRsp("同步仓库物料异常", data);
        }
    }

    /**
     * 同步库存接口
     *
     * @param request_uuid 随机生成32位UUID
     * @param request_time 请求时间，格式参考约束
     * @param request_data 请求业务参数
     * @return
     */
    @RequestMapping("/SynSteelPlateInventory")
    public ResponseResult<List<String>> synchronousInventory(String request_uuid, String request_time, Steel request_data) {
        if (request_uuid == null || request_time == null || request_data == null) {
            return Response.makeRsp(100, "参数不能为空");
        }
        ArrayList<String> data = new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            wmsService.save(request_data);
            data.add(request_uuid);
            data.add(formatter.format(date));
            return Response.makeOKRsp(data);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.makeErrRsp("同步仓库物料异常", data);
        }
    }

    /**
     * 放板或抓取确认接口
     *
     * @param request_uuid 随机生成32位UUID
     * @param request_time 请求时间，格式参考约束
     * @param request_data 请求业务参数
     * @return
     */
    @RequestMapping("/confirmOperation")
    public ResponseResult<List<String>> confirmOperation(String request_uuid, String request_time, String request_data) {
        if (request_uuid.isEmpty() || request_time.isEmpty() || request_data.isEmpty()) {
            return Response.makeRsp(100, "参数不能为空");
        }
        ArrayList<String> data = new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            data.add(request_uuid);
            data.add(formatter.format(date));
            data.add(request_uuid);
            //TODO
            return Response.makeRsp(1, "0", data);
        } catch (Exception e) {
            e.printStackTrace();
            return Response.makeErrRsp("确认出错", data);
        }
    }

    /**
     * 任务执行结果接口
     *
     * @param request_uuid 随机生成32位UUID
     * @param request_time 请求时间，格式参考约束
     * @param request_data 请求业务参数
     * @return
     */
    @RequestMapping("/TaskResults")
    public ResponseResult<List<String>> taskResults(String request_uuid, String request_time, WmsAtion request_data) {
        if (request_uuid == null || request_time == null || request_data == null) {
            return Response.makeRsp(100, "参数不能为空");
        }
        ArrayList<String> data = new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        data.add(request_uuid);
        data.add(formatter.format(date));
        data.add(request_uuid);
        try {
            wmsService.save(request_data);
            return Response.makeOKRsp();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.makeErrRsp("保存执行异常", data);
        }
    }

    public String doWmsPlan(List<String> request_data) {
        String url = "";
        String s="";
        Map<String, String> params = new HashMap<>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        for (String data : request_data) {
            params.put("request_uuid", UUID.randomUUID().toString());
            params.put("request_time", formatter.format(date));
            params.put("func_code", "SyncWmsInterfTask");
            params.put("line_code", "LineWms01");
            params.put("device_code", "CellD01");
            params.put("request_data", data);
            s = HttpUtils.doPost(url, params);
        }
        return s;
    }

    public void cancelWmsPlan(String task_id) {
        String url = "";
        Map<String, String> params = new HashMap<>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        params.put("request_uuid", UUID.randomUUID().toString());
        params.put("request_time", formatter.format(date));
        params.put("func_code", "SyncWmsInterfTask");
        params.put("line_code", "LineWms01");
        params.put("device_code", "CellD01");
        params.put("request_data", task_id);
        HttpUtils.doPost(url, params);
    }
}
