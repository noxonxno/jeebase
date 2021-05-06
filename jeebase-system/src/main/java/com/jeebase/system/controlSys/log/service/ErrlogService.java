package com.jeebase.system.controlSys.log.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jeebase.system.controlSys.log.entitly.ErrLog;

/**
 * @author DELL
 */
public interface ErrlogService extends IService<ErrLog> {
    /**
     * 分页查询所有
     *
     * @param errLog
     * @param page
     * @return
     */
    Page<ErrLog> listErrLog(ErrLog errLog, Page<ErrLog> page);

    /**
     * +
     * 通过id获取
     *
     * @param id
     * @return
     */
    ErrLog getById(String id);

    /**
     * 新增
     *
     * @param errLog
     * @return
     */
    boolean insertErrLog(ErrLog errLog);

    /**
     * 修改
     *
     * @param errLog
     * @return
     */
    boolean updateErrLog(ErrLog errLog);

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */
    boolean deleteErrLog(String id);


}
