package com.testcase.frame.service.impl;

import com.testcase.frame.bean.StepModuleBo;
import com.testcase.frame.common.util.ToolUtils;
import com.testcase.frame.mapper.StepBindModuleMapper;
import com.testcase.frame.pojo.StepBindModule;
import com.testcase.frame.pojo.TestCaseStep;
import com.testcase.frame.service.intf.IStepBindModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName StepBindModuleServiceImpl
 * @Description TODO
 * @Author ycn
 * @Date 2019-10-11
 **/
@Service
public class StepBindModuleServiceImpl implements IStepBindModuleService {

    private StepBindModuleMapper stepBindModuleMapper;

    @Autowired
    public StepBindModuleServiceImpl(StepBindModuleMapper stepBindModuleMapper) {
        this.stepBindModuleMapper = stepBindModuleMapper;
    }

    @Override
    public boolean batchAddBindList(StepModuleBo stepModuleBo) {
        Integer moduleId = stepModuleBo.getModuleId();
        List<TestCaseStep> stepList = stepModuleBo.getStepList();
        List<StepBindModule> stepBindModuleList = new ArrayList<>(16);
        for (TestCaseStep testCaseStep : stepList) {
            Integer stepId = testCaseStep.getStepId();
            StepBindModule stepBindModule = new StepBindModule();
            stepBindModule.setModuleId(moduleId);
            stepBindModule.setStepId(stepId);
            stepBindModuleList.add(stepBindModule);
        }
        int i = stepBindModuleMapper.batchInsertBindList(stepBindModuleList);
        return (i != 0);
    }

    @Override
    public boolean batchDelBindList(StepModuleBo stepModuleBo) {
        List<StepBindModule> stepBindModuleList = new ArrayList<>(16);
        List<TestCaseStep> stepList = stepModuleBo.getStepList();
        for (TestCaseStep testCaseStep : stepList) {
            StepBindModule stepBindModule = new StepBindModule();
            stepBindModule.setStepId(testCaseStep.getStepId());
            stepBindModuleList.add(stepBindModule);
        }
        int i = stepBindModuleMapper.batchDeleteBindRelation(stepBindModuleList);
        return (i != 0);
    }

    @Override
    public List<StepBindModule> getBindInfoByCondition(StepBindModule stepBindModule) {
        Example example = new Example(StepBindModule.class);
        Example.Criteria criteria = example.createCriteria();
        Integer stepId = stepBindModule.getStepId();
        Integer moduleId = stepBindModule.getModuleId();
        if (!ToolUtils.isNull(stepId)) {
            criteria.andEqualTo("stepId", stepId);
        }
        if (!ToolUtils.isNull(moduleId)) {
            criteria.andEqualTo("moduleId", moduleId);
        }
        return stepBindModuleMapper.selectByExample(example);
    }

    @Override
    public boolean updateStepBindModule(StepBindModule stepBindModule) {
        int i = stepBindModuleMapper.updateByPrimaryKey(stepBindModule);
        return (i != 0);
    }

    @Override
    public boolean insertOrUpdateBindRelation(StepBindModule stepBindModule) {
        return stepBindModuleMapper.insertOrUpdateBindRelation(stepBindModule);
    }

    @Override
    public boolean deleteStepBindModule(StepBindModule stepBindModule) {
        Example example = new Example(StepBindModule.class);
        Example.Criteria criteria = example.createCriteria();
        Integer stepId = stepBindModule.getStepId();
        if (!ToolUtils.isNull(stepId)) {
            criteria.andEqualTo("stepId", stepId);
        }
        int i = stepBindModuleMapper.deleteByExample(example);
        return (i != 0);
    }

    @Override
    public void batchDeleteBindRelation(List<StepBindModule> stepBindModuleList) {
        stepBindModuleMapper.batchDeleteBindRelation(stepBindModuleList);
    }

    @Override
    public boolean batchUpdateBindRelation(List<Integer> stepIdList, int moduleId) {
        int i = stepBindModuleMapper.batchUpdateBindRelation(stepIdList, moduleId);
        return (i == 1);
    }
}
