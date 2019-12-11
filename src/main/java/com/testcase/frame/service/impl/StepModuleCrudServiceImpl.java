package com.testcase.frame.service.impl;

import com.testcase.frame.common.util.ToolUtils;
import com.testcase.frame.mapper.StepModuleMapper;
import com.testcase.frame.pojo.StepModule;
import com.testcase.frame.service.intf.IStepAttrCrudService;
import com.testcase.frame.service.intf.IStepModuleCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @ClassName StepModuleCrudServiceImpl
 * @Description TODO
 * @Author ycn
 * @Date 2019-09-26
 **/
@Service
public class StepModuleCrudServiceImpl implements IStepModuleCrudService {

    private StepModuleMapper stepModuleMapper;

    @Autowired
    public StepModuleCrudServiceImpl(StepModuleMapper stepModuleMapper) {
        this.stepModuleMapper = stepModuleMapper;
    }

    @Override
    public StepModule addStepModule(StepModule stepModule) {
        int i = stepModuleMapper.insertSelective(stepModule);
        if (i == 0) {
            return null;
        }
        return stepModule;
    }

    @Override
    public List<StepModule> getStepModuleListByCondition(StepModule stepModule) {
        Example example = new Example(StepModule.class);
        Example.Criteria criteria = example.createCriteria();
        Integer moduleId = stepModule.getModuleId();
        String moduleName = stepModule.getModuleName();
        Integer systemId = stepModule.getSystemId();
        Integer systemModuleId = stepModule.getSystemModuleId();
        if (!ToolUtils.isNull(moduleId)) {
            criteria.andEqualTo("moduleId", moduleId);
        }
        if (!ToolUtils.isNull(systemModuleId)) {
            criteria.andEqualTo("systemModuleId", systemModuleId);
        }
        if (!ToolUtils.isNull(moduleName)) {
            criteria.andEqualTo("moduleName", moduleName);
        }
        if (!ToolUtils.isNull(systemId)) {
            criteria.andEqualTo("systemId", systemId);
        }
        return stepModuleMapper.selectByExample(example);
    }

    @Override
    public boolean updateModule(StepModule stepModule) {
        int i = stepModuleMapper.updateByPrimaryKey(stepModule);
        return (i == 1);
    }

    @Override
    public boolean deleteStepModule(StepModule stepModule) {
        int i = stepModuleMapper.deleteByPrimaryKey(stepModule);
        return (i != 0);
    }
}
