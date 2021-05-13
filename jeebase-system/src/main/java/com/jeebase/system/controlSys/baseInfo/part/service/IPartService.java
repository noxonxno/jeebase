package com.jeebase.system.controlSys.baseInfo.part.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jeebase.system.controlSys.baseInfo.part.entity.PartEntitly;

/**
 * @author DELL
 */
public interface IPartService extends IService<PartEntitly> {

    /**
     * 通过id查询
     * @param partEntitly
     * @return
     */
    PartEntitly getPartById(PartEntitly partEntitly);

    /**
     *  分页查询
     * @param page
     * @param partEntitly
     * @return
     */
    Page<PartEntitly> partList(Page<PartEntitly> page, PartEntitly partEntitly);

    /**
     * 删除
     * @param partId
     * @return
     */
    boolean removePart(String partId);

    /**
     * 新增
     * @param partEntitly
     * @return
     */
    boolean addPart(PartEntitly partEntitly);

    /**
     * 修改
     * @param partEntitly
     * @return
     */
    boolean updatePart(PartEntitly partEntitly);

}
