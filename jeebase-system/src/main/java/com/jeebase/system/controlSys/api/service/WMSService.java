package com.jeebase.system.controlSys.api.service;


import com.jeebase.system.controlSys.api.entity.Steel;
import com.jeebase.system.controlSys.api.entity.WmsAtion;
import com.jeebase.system.controlSys.api.mapper.SteelMapper;
import com.jeebase.system.controlSys.api.mapper.WmsAtionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class WMSService {
    @Autowired
    private SteelMapper steelMapper;
    @Autowired
    private WmsAtionMapper wmsAtionMapper;
    public void save(Steel steel) {
        steelMapper.insert(steel);
    }
    public void save(WmsAtion wmsAtion) {
        wmsAtionMapper.insert(wmsAtion);
    }


}
