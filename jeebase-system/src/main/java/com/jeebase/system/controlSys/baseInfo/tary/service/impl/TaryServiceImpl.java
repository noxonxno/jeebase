package com.jeebase.system.controlSys.baseInfo.tary.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jeebase.common.base.BusinessException;
import com.jeebase.system.controlSys.baseInfo.location.entity.LocationEntitly;
import com.jeebase.system.controlSys.baseInfo.tary.entity.TaryEntitly;
import com.jeebase.system.controlSys.baseInfo.tary.mapper.ITaryMapper;
import com.jeebase.system.controlSys.baseInfo.tary.service.ITaryService;
import com.jeebase.system.utils.UUIDUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author DELL
 */
@Service
public class TaryServiceImpl extends ServiceImpl<ITaryMapper, TaryEntitly>
        implements ITaryService {

    @Autowired
    private ITaryMapper taryMapper;

    @Override
    public TaryEntitly getTaryEntityById(TaryEntitly tary) {

        return taryMapper.selectById(tary.getTaryId());
    }

    @Override
    public Page<TaryEntitly> trayList(Page<TaryEntitly> page, TaryEntitly tary) {
        QueryWrapper<TaryEntitly> wrapper = new QueryWrapper<TaryEntitly>();
        //查询条件
        wrapper.eq(tary.getPlanCode() != null && !(("").equals(tary.getPlanCode())), "plan_code", tary.getPlanCode())
                .eq( "tary_state", 0)
                .like(tary.getTaryInfo() != null && !(("").equals(tary.getTaryInfo())), "tary_info", tary.getTaryInfo());

        IPage<TaryEntitly> taryList = taryMapper.selectPage(page,wrapper);

        return (Page<TaryEntitly>) taryList;
    }


    @Override
    public boolean removeTary(String taryId) {
        TaryEntitly tary = taryMapper.selectById(taryId);
        tary.setTaryState(1);
        return saveOrUpdate(tary);
    }

    @Override
    public boolean addTary(TaryEntitly tary) {
        tary.setTaryId(UUIDUtils.getUUID32());
        tary.setTaryState(0);
        return save(tary);
    }

    @Override
    public boolean updateTary(TaryEntitly tary) {
        TaryEntitly t = taryMapper.selectById(tary.getTaryId());
        t.setTaryInfo(tary.getTaryInfo());
        t.setTaryFixNum(tary.getTaryFixNum());
        return saveOrUpdate(tary);
    }

    @Override
    public boolean batchRemove(String ids) {
        String[] strs = ids.split(",");
        List idList = Arrays.asList(strs);
        if (idList.isEmpty()) {
            throw new BusinessException("id不能为空");
        }
        List<TaryEntitly> taryList = taryMapper.selectBatchIds(idList);
        for (TaryEntitly tary : taryList) {
            tary.setTaryState(1);
        }
        return saveOrUpdateBatch(taryList);
    }

    @Override
    public boolean batchUpdate(String taryFixNum) {
        List<TaryEntitly> taryList  = taryMapper.selectList(null);
        for (TaryEntitly tary : taryList) {
            tary.setTaryFixNum(Integer.parseInt(taryFixNum));
        }
        return saveOrUpdateBatch(taryList);
    }

}
