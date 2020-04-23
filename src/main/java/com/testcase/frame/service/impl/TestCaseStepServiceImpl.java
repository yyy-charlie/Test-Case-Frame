package com.testcase.frame.service.impl;

import com.testcase.frame.bean.*;
import com.testcase.frame.common.Const;
import com.testcase.frame.common.util.ToolUtils;
import com.testcase.frame.mapper.StepAttrMapper;
import com.testcase.frame.mapper.StepModuleMapper;
import com.testcase.frame.pojo.TestCaseStep;
import com.testcase.frame.service.intf.ITestCaseStepCrudService;
import com.testcase.frame.service.intf.ITestCaseStepService;
import com.testcase.frame.vo.TestCaseTree;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName TestCaseStepServiceImpl
 * @Description TODO
 * @Author ycn
 * @Date 2019-09-20
 **/
@Service
public class TestCaseStepServiceImpl implements ITestCaseStepService {

    private ITestCaseStepCrudService testCaseStepCrudService;

    @Autowired
    public TestCaseStepServiceImpl(ITestCaseStepCrudService testCaseStepCrudService) {
        this.testCaseStepCrudService = testCaseStepCrudService;
    }

    public TestCaseStepServiceImpl() {
    }

    @Override
    public TestCaseStep addTestCaseStep(TestCaseStep testCaseStep) {
        return testCaseStepCrudService.addTestCaseStep(testCaseStep);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<TestCaseStep> addTestCaseStepBasedOnStepObj(TestCaseStepBO testCaseStepBo) {
        TestCaseStep testCaseStep = ToolUtils.autoCopyProperties(testCaseStepBo, TestCaseStep.class);
        testCaseStep.setStepName(testCaseStepBo.getStepObj() + "输入");
        testCaseStep.setSingleStepMarkId(Const.REPEAT_STEP);

        Integer minLength = testCaseStepBo.getMinLength();
        Integer maxLength = testCaseStepBo.getMaxLength();
        Integer systemId = testCaseStepBo.getSystemId();
        Integer moduleId = testCaseStepBo.getModuleId();

        if (minLength > maxLength) {
            return new ArrayList<>();
        }

        TestCaseStep caseStep = testCaseStepCrudService.addTestCaseStep(testCaseStep);
        if (ToolUtils.isNull(caseStep)) {
            return new ArrayList<>();
        }

        Integer stepParentId = caseStep.getStepId();

        //获取所有的用例内容
        List<StringBuffer> allContent = getAllContent(minLength, maxLength);
        //将用例报装内容成对象
        List<TestCaseStep> testCaseStepList = getAddStepList(allContent, stepParentId, systemId, moduleId);

        List<TestCaseStep> resultList = new ArrayList<>(16);
        List<TestCaseStep> testCaseStepList1 = testCaseStepCrudService.batchAddTestCaseStepList(testCaseStepList);
        //如姓名输入
        resultList.add(caseStep);
        //具体用例步骤
        resultList.addAll(testCaseStepList1);
        return resultList;
    }

    /**
     * 获取所有的用例内容
     *
     * @param minLength
     * @param maxLength
     * @return
     */
    private List<StringBuffer> getAllContent(Integer minLength, Integer maxLength) {
        List<StringBuffer> allContent = new ArrayList<>(16);

        StepObj stepObj = new StepObj();
        List<StringBuffer> chineseContent = stepObj.getGenerateContent("chinese", minLength, maxLength);
        List<StringBuffer> letterContent = stepObj.getGenerateContent("letter", minLength, maxLength);
        List<StringBuffer> numberContent = stepObj.getGenerateContent("number", minLength, maxLength);
        List<StringBuffer> symbolObjGenerateContent = stepObj.getGenerateContent("symbol", minLength, maxLength);
        allContent.addAll(chineseContent);
        allContent.addAll(letterContent);
        allContent.addAll(numberContent);
        allContent.addAll(symbolObjGenerateContent);

        allContent.add(new StringBuffer("为空"));
        return allContent;
    }

    private List<TestCaseStep> getAddStepList(List<StringBuffer> content, Integer stepParentId, Integer systemId, Integer moduleId) {
        List<TestCaseStep> testCaseStepList = new ArrayList<>(16);
        for (StringBuffer stringBuffer : content) {
            TestCaseStep testCaseStep = new TestCaseStep(stringBuffer.toString(), stepParentId, systemId, moduleId, Const.SINGLE_STEP, Const.LAST_STEP);
            testCaseStepList.add(testCaseStep);
        }
        return testCaseStepList;
    }

    @Override
    public List<TestCaseStep> getTestCaseListBySystemId(TestCaseStep testCaseStep) {
        return testCaseStepCrudService.getTestCaseListBySystemId(testCaseStep);
    }

    @Override
    public boolean deleteTestStep(TestCaseStep testCaseStep) {
        List<TestCaseStep> testCaseStepList = testCaseStepCrudService.getTestCaseStepByParentId(testCaseStep);
        //删除子步骤
        if (ToolUtils.judgeList(testCaseStepList)) {
            boolean b = testCaseStepCrudService.deleteParentStep(testCaseStep);
            if (!b) {
                return false;
            }
        }

        //删除该步骤
        return testCaseStepCrudService.deleteTestStep(testCaseStep);
    }

    @Override
    public List<List<TestCaseStep>> getSingleStepList(TestCaseStep testCaseStep) {
        List<TestCaseStep> testCaseStepList = getAllSingleStep(testCaseStep);
        List<List<TestCaseStep>> lists = new ArrayList<>(16);
        for (TestCaseStep testCaseStep1 : testCaseStepList) {
            Integer stepId = testCaseStep1.getStepId();
            List<TestCaseStep> singleStepList = testCaseStepCrudService.getParentStepList(stepId);
            lists.add(singleStepList);
        }
        return lists;
    }

    @Override
    public List<TestCaseStep> getChildrenStepList(Integer stepId) {
        return testCaseStepCrudService.getChildrenStepList(stepId);
    }

    @Override
    public List<TestCaseStep> getParentStepList(Integer stepId) {
        return testCaseStepCrudService.getParentStepList(stepId);
    }

    @Override
    public boolean updateTestCaseStep(TestCaseStep testCaseStep) {
        return testCaseStepCrudService.updateTestCaseStep(testCaseStep);
    }

    @Override
    public boolean batchDeleteStepList(List<TestCaseStep> testCaseStepList) {
        return testCaseStepCrudService.batchDeleteStepList(testCaseStepList);
    }

    @Override
    public List<TestCaseTree> getTestCaseTreeBySystemId(Integer systemId, Integer moduleId) {
        return testCaseStepCrudService.getTestCaseTreeBySystemId(systemId, moduleId);
    }

    @Override
    public TestCaseStep getTestCaseStepById(Integer stepId) {
        TestCaseStep step = new TestCaseStep();
        step.setStepId(stepId);
        List<TestCaseStep> testCaseStepList = testCaseStepCrudService.getTestCaseStepByCondition(step);
        if (ToolUtils.judgeList(testCaseStepList)) {
            return testCaseStepList.get(0);
        }
        return null;
    }

    @Override
    public List<TestCaseTree> getParentStepAndAttrById(Integer stepId) {
        return testCaseStepCrudService.getParentStepAndAttrById(stepId);
    }

    /**
     * 获取某个系统下所有的单个操作
     *
     * @param testCaseStep
     * @return
     */
    private List<TestCaseStep> getAllSingleStep(TestCaseStep testCaseStep) {
        testCaseStep.setLastStepMarkId(Const.LAST_STEP);
        return testCaseStepCrudService.getTestCaseStepByCondition(testCaseStep);
    }

}
