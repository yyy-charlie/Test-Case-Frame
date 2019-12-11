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

    private StepAttrMapper stepAttrMapper;

    private StepModuleMapper stepModuleMapper;

    @Autowired
    public TestCaseStepServiceImpl(ITestCaseStepCrudService testCaseStepCrudService, StepAttrMapper stepAttrMapper, StepModuleMapper stepModuleMapper) {
        this.testCaseStepCrudService = testCaseStepCrudService;
        this.stepAttrMapper = stepAttrMapper;
        this.stepModuleMapper = stepModuleMapper;
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
        TestCaseStep testCaseStep = new TestCaseStep();
        testCaseStep.setSystemId(testCaseStepBo.getSystemId());
        testCaseStep.setModuleId(testCaseStepBo.getModuleId());
        testCaseStep.setStepName(testCaseStepBo.getStepObj() + "输入");
        testCaseStep.setStepParentId(testCaseStepBo.getStepParentId());
        testCaseStep.setSingleStepMarkId(Const.REPEAT_STEP);
        TestCaseStep caseStep = testCaseStepCrudService.addTestCaseStep(testCaseStep);
        if (ToolUtils.isNull(caseStep)) {
            return new ArrayList<>();
        }
        Integer minLength = testCaseStepBo.getMinLength();
        Integer maxLength = testCaseStepBo.getMaxLength();
        Integer systemId = testCaseStepBo.getSystemId();
        Integer moduleId = testCaseStepBo.getModuleId();
        Integer stepParentId = caseStep.getStepId();

        List<StringBuffer> allContent = new ArrayList<>(16);
        StepObj letterObj = new LetterObj();
        List<StringBuffer> letterContent = letterObj.getGenerateContent(minLength, maxLength);
        allContent.addAll(letterContent);

        StepObj numberObj = new NumberObj();
        List<StringBuffer> numberContent = numberObj.getGenerateContent(minLength, maxLength);
        allContent.addAll(numberContent);

        StepObj chineseObj = new ChineseObj();
        List<StringBuffer> chineseContent = chineseObj.getGenerateContent(minLength, maxLength);
        allContent.addAll(chineseContent);

        StepObj symbolObj = new SymbolObj();
        List<StringBuffer> symbolObjGenerateContent = symbolObj.getGenerateContent(minLength, maxLength);
        allContent.addAll(symbolObjGenerateContent);

        allContent.add(new StringBuffer("为空"));
        List<TestCaseStep> testCaseStepList = getAddStepList(allContent, stepParentId, systemId, moduleId);

        List<TestCaseStep> resultList = new ArrayList<>(16);
        resultList.add(caseStep);
        List<TestCaseStep> testCaseStepList1 = testCaseStepCrudService.batchAddTestCaseStepList(testCaseStepList);
        resultList.addAll(testCaseStepList1);
        return resultList;
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
