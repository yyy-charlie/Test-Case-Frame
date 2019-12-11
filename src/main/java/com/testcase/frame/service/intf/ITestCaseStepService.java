package com.testcase.frame.service.intf;

import com.testcase.frame.bean.TestCaseStepBO;
import com.testcase.frame.pojo.StepAttr;
import com.testcase.frame.pojo.StepModule;
import com.testcase.frame.pojo.TestCaseStep;
import com.testcase.frame.vo.TestCaseTree;

import java.util.List;

/**
 * @ClassName ITestCaseStepService
 * @Description TODO
 * @Author ycn
 * @Date 2019-09-20
 **/
public interface ITestCaseStepService {

    /**
     * 新增测试步骤
     *
     * @param testCaseStep
     * @return
     */
    TestCaseStep addTestCaseStep(TestCaseStep testCaseStep);

    /**
     * 根据步骤对象新增测试用例步骤
     *
     * @param testCaseStep
     * @return
     */
    List<TestCaseStep> addTestCaseStepBasedOnStepObj(TestCaseStepBO testCaseStep);

    /**
     * 根据系统id查询用例步骤
     *
     * @param testCaseStep
     * @return
     */
    List<TestCaseStep> getTestCaseListBySystemId(TestCaseStep testCaseStep);

    /**
     * 删除测试步骤
     *
     * @param testCaseStep
     * @return
     */
    boolean deleteTestStep(TestCaseStep testCaseStep);

    /**
     * 根据步骤id获取该步骤的所有父步骤
     *
     * @param testCaseStep
     * @return
     */
    List<List<TestCaseStep>> getSingleStepList(TestCaseStep testCaseStep);

    /**
     * 获取步骤下的子步骤
     *
     * @param stepId
     * @return
     */
    List<TestCaseStep> getChildrenStepList(Integer stepId);

    /**
     * 根据步骤id获取该步骤的所有父步骤
     *
     * @param stepId
     * @return
     */
    List<TestCaseStep> getParentStepList(Integer stepId);

    /**
     * 更新步骤
     *
     * @param testCaseStep
     * @return
     */
    boolean updateTestCaseStep(TestCaseStep testCaseStep);

    /**
     * 批量删除步骤
     *
     * @param testCaseStepList
     * @return
     */
    boolean batchDeleteStepList(List<TestCaseStep> testCaseStepList);

    /**
     * 获取系统下的步骤树
     *
     * @param systemId
     * @param moduleId
     * @return
     */
    List<TestCaseTree> getTestCaseTreeBySystemId(Integer systemId, Integer moduleId);

    /**
     * 根据步骤id获取步骤信息
     *
     * @param stepId
     * @return
     */
    TestCaseStep getTestCaseStepById(Integer stepId);

    /**
     * 获取所有父步骤的步骤和属性
     *
     * @param stepId
     * @return
     */
    List<TestCaseTree> getParentStepAndAttrById(Integer stepId);
}
