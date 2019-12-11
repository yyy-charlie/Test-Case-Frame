package com.testcase.frame.service.impl;

import com.testcase.frame.common.util.ToolUtils;
import com.testcase.frame.mapper.TestCaseStepMapper;
import com.testcase.frame.pojo.TestCaseStep;
import com.testcase.frame.service.intf.ITestCaseStepCrudService;
import com.testcase.frame.vo.TestCaseTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Condition;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName TestCaseStepCrudServiceImpl
 * @Description TODO
 * @Author ycn
 * @Date 2019-09-26
 **/
@Service
public class TestCaseStepCrudServiceImpl implements ITestCaseStepCrudService {

    private TestCaseStepMapper testCaseStepMapper;

    @Autowired
    public TestCaseStepCrudServiceImpl(TestCaseStepMapper testCaseStepMapper) {
        this.testCaseStepMapper = testCaseStepMapper;
    }

    @Override
    public TestCaseStep addTestCaseStep(TestCaseStep testCaseStep) {
        int i = testCaseStepMapper.insertSelective(testCaseStep);
        if (i == 0) {
            return new TestCaseStep();
        }
        return testCaseStep;
    }

    @Override
    public List<TestCaseStep> batchAddTestCaseStepList(List<TestCaseStep> testCaseStepList) {
        Integer integer = testCaseStepMapper.batchInsertStepList(testCaseStepList);
        if (integer == 0) {
            return new ArrayList<>();
        }
        return testCaseStepList;
    }

    @Override
    public List<TestCaseStep> getTestCaseStepByParentId(TestCaseStep testCaseStep) {
        Example example = new Example(TestCaseStep.class);
        Example.Criteria criteria = example.createCriteria();
        Integer stepId = testCaseStep.getStepId();
        if (!ToolUtils.isNull(stepId)) {
            criteria.andEqualTo("stepParentId", stepId);
        }
        return testCaseStepMapper.selectByExample(example);
    }

    @Override
    public List<TestCaseStep> getChildrenTestCaseStep(TestCaseStep testCaseStep) {
        return null;
    }

    @Override
    public List<TestCaseStep> getTestCaseStepByCondition(TestCaseStep testCaseStep) {
        Example example = new Example(TestCaseStep.class);
        Example.Criteria criteria = example.createCriteria();
        Integer stepId = testCaseStep.getStepId();
        Integer systemId = testCaseStep.getSystemId();
        Integer moduleId = testCaseStep.getModuleId();
        Integer singleStepMarkId = testCaseStep.getSingleStepMarkId();
        Integer lastStepMarkId = testCaseStep.getLastStepMarkId();
        if (!ToolUtils.isNull(stepId)) {
            criteria.andEqualTo("stepId", stepId);
        }
        if (!ToolUtils.isNull(moduleId)) {
            criteria.andEqualTo("moduleId", moduleId);
        }
        if (!ToolUtils.isNull(systemId)) {
            criteria.andEqualTo("systemId", systemId);
        }
        if (!ToolUtils.isNull(singleStepMarkId)) {
            criteria.andEqualTo("singleStepMarkId", singleStepMarkId);
        }
        if (!ToolUtils.isNull(lastStepMarkId)) {
            criteria.andEqualTo("lastStepMarkId", lastStepMarkId);
        }
        example.orderBy("stepId");
        return testCaseStepMapper.selectByExample(example);
    }

    @Override
    public List<TestCaseStep> getTestCaseListBySystemId(TestCaseStep testCaseStep) {
        Example example = new Example(TestCaseStep.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("systemId", testCaseStep.getSystemId());
        return testCaseStepMapper.selectByExample(example);
    }

    @Override
    public boolean deleteTestStep(TestCaseStep testCaseStep) {
        int i = testCaseStepMapper.deleteByPrimaryKey(testCaseStep);
        return (i != 0);
    }

    @Override
    public boolean deleteParentStep(TestCaseStep testCaseStep) {
        Example example = new Example(TestCaseStep.class);
        Example.Criteria criteria = example.createCriteria();
        Integer stepId = testCaseStep.getStepId();
        criteria.andEqualTo("stepParentId", stepId);
        int deleteParentId = testCaseStepMapper.deleteByExample(example);
        return (deleteParentId != 0);
    }

    @Override
    public List<TestCaseStep> getParentStepList(Integer stepId) {
        return testCaseStepMapper.getParentStepList(stepId);
    }

    @Override
    public List<TestCaseStep> getChildrenStepList(Integer stepId) {
        return testCaseStepMapper.getChildrenStepList(stepId);
    }

    @Override
    public boolean updateTestCaseStep(TestCaseStep testCaseStep) {
        int i = testCaseStepMapper.updateByPrimaryKeySelective(testCaseStep);
        return (i != 0);
    }

    @Override
    public boolean batchDeleteStepList(List<TestCaseStep> testCaseStepList) {
        int i = testCaseStepMapper.batchDeleteStepList(testCaseStepList);
        return (i != 0);
    }

    @Override
    public List<TestCaseTree> getTestCaseTreeBySystemId(Integer systemId, Integer moduleId) {
        return testCaseStepMapper.getTestCaseTreeBySystemId(systemId, moduleId);
    }

    @Override
    public List<TestCaseTree> getParentStepAndAttrById(Integer stepId) {
        return testCaseStepMapper.getParentStepAndAttrById(stepId);
    }
}
