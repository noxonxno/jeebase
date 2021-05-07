package com.jeebase.system.controlSys.log.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeebase.system.controlSys.log.entitly.ErrLog;
import com.jeebase.system.controlSys.log.mapper.ErrLogMapper;
import com.jeebase.system.controlSys.log.service.ErrlogService;
import com.jeebase.system.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author DELL
 */
@Service
public class ErrlogServiceImpl extends ServiceImpl<ErrLogMapper, ErrLog>
        implements ErrlogService {

    @Autowired
    private ErrLogMapper errLogMapper;

    @Override
    public Page<ErrLog> listErrLog(ErrLog errLog, Page<ErrLog> page) {
        QueryWrapper<ErrLog> queryWrapper = new QueryWrapper<>();

        IPage<ErrLog> iPage = errLogMapper.selectPage(page, queryWrapper);
        return (Page<ErrLog>) iPage;
    }

    @Override
    public ErrLog getById(String id) {

        return errLogMapper.selectById(id);
    }

    @Override
    public boolean insertErrLog(ErrLog errLog) {
        errLog.setLogId(UUIDUtils.getUUID32());
        return save(errLog);
    }

    @Override
    public boolean updateErrLog(ErrLog errLog) {
        return saveOrUpdate(errLog);
    }

    @Override
    public boolean deleteErrLog(String id) {
        return errLogMapper.deleteById(id) > 0 ? true : false;
    }
}
