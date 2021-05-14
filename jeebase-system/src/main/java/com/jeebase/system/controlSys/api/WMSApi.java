package com.jeebase.system.controlSys.api;

import com.jeebase.common.base.BusinessException;
import com.jeebase.system.controlSys.api.entity.*;
import com.jeebase.system.controlSys.api.service.WMSService;
import com.jeebase.system.controlSys.taskManage.entity.WmsTaskEntity;
import com.jeebase.system.controlSys.taskManage.service.IWmsTaskService;
import com.jeebase.system.controlSys.taskManage.service.impl.WmsTaskServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.jeebase.system.utils.HttpUtils;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/base_url")
public class WMSApi {
    @Autowired
    private WMSService wmsService;
    @Autowired
    private IWmsTaskService wmsTaskService;

    @PostMapping("/SynSteelPlateInventoryBat")
    public ResponseResult<List<String>> synSteelPlateInventoryBat(@RequestBody WMSApiSynSteelPlateInventoryBat data) {
        if (data.getRequest_uuid() == null || data.getRequest_time() == null || data.getRequest_data() == null) {
            return Response.makeRsp(100, "参数不能为空");
        }
        ArrayList<String> response_data = new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            Steel request_data = data.getRequest_data();
            for (Steel steel : request_data.getSteel_list()) {
                wmsService.save(steel);
            }
            response_data.add(data.getRequest_uuid());
            response_data.add(formatter.format(date));
            return Response.makeOKRsp(response_data);
        } catch (BusinessException e) {
            e.printStackTrace();
            return Response.makeErrRsp("同步仓库物料异常", response_data);
        }
    }

    /**
     * @param data
     * @return
     */
    @RequestMapping("/SynSteelPlateInventory")
    public ResponseResult<List<String>> synchronousInventory(@RequestBody WMSApiSynSteelPlateInventoryBat data) {
        if (data.getRequest_uuid() == null || data.getRequest_time() == null || data.getRequest_data() == null) {
            return Response.makeRsp(100, "参数不能为空");
        }
        ArrayList<String> response_data = new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            Steel request_data = data.getRequest_data();
            wmsService.save(request_data);
            response_data.add(data.getRequest_uuid());
            response_data.add(formatter.format(date));
            return Response.makeOKRsp(response_data);
        } catch (BusinessException e) {
            e.printStackTrace();
            return Response.makeErrRsp("同步仓库物料异常", response_data);
        }
    }

    /**
     * 放板或抓取确认接口
     */
    @RequestMapping("/confirmOperation")
    public ResponseResult<List<String>> confirmOperation(@RequestBody WMSApiConfirmOperation data) {
        if (data.getRequest_uuid() == null || data.getRequest_time() == null || data.getRequest_data() == null) {
            return Response.makeRsp(100, "参数不能为空");
        }
        ArrayList<String> response_data = new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            response_data.add(data.getRequest_uuid());
            response_data.add(formatter.format(date));
            return Response.makeRsp(1, "0", response_data);
        } catch (BusinessException e) {
            e.printStackTrace();
            return Response.makeErrRsp("确认出错", response_data);
        }
    }

    /**
     * 任务执行结果接口
     *
     * @return
     */
    @RequestMapping("/TaskResults")
    public ResponseResult<List<String>> taskResults(@RequestBody WMSApiTaskResults data) {
        if (data.getRequest_uuid() == null || data.getRequest_time() == null || data.getRequest_data() == null) {
            return Response.makeRsp(100, "参数不能为空");
        }
        ArrayList<String> response_data = new ArrayList<>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try {
            WmsAtion wmsAtion_data = data.getRequest_data();
            WmsTaskEntity wmsTaskEntity = new WmsTaskEntity();
            wmsTaskEntity.setId(wmsAtion_data.getTask_id());
            wmsTaskEntity.setPlanCode(wmsAtion_data.getPlan_code());
//            wmsTaskEntity.setTaskType(wmsAtion_data.getTask_tpye());
            wmsTaskEntity.setFplanState(wmsAtion_data.getTask_status());
            wmsTaskEntity.setCreateTime(wmsAtion_data.getTask_sTime());
            wmsTaskEntity.setEndTime(wmsAtion_data.getTask_eTime());
            wmsTaskService.doTaskCallBack(wmsTaskEntity);
            response_data.add(data.getRequest_uuid());
            response_data.add(formatter.format(date));
            return Response.makeOKRsp();
        } catch (BusinessException e) {
            e.printStackTrace();
            return Response.makeErrRsp("保存执行异常", response_data);
        }
    }

    public String doWmsPlan(List<String> requestData) {
        String url = "";
        String s = "";
        Map<String, String> params = new HashMap<>();
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        for (String data : requestData) {
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
