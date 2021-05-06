package com.jeebase.system.controlSys.api.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jeebase.system.controlSys.api.entity.CutTask;
import com.jeebase.system.controlSys.reportAction.entity.CutActionEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CutAtinoMapper extends BaseMapper<CutActionEntity> {
}
