package com.testcase.frame.service.intf;

import com.testcase.frame.pojo.TestCaseStep;
import com.testcase.frame.vo.TestCaseTree;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @ClassName ITestCaseStepCrudService
 * @Description TODO
 * @Author ycn
 * @Date 2019-09-26
 **/
public interface ITestCaseStepCrudService {

    /**
     * 新增测试步骤
     *
     * @param testCaseStep
     * @return
     */
    TestCaseStep addTestCaseStep(TestCaseStep testCaseStep);

    /**
     * 批量新增步骤
     *
     * @param testCaseStepList
     * @return
     */
    List<TestCaseStep> batchAddTestCaseStepList(List<TestCaseStep> testCaseStepList);

    /**
     * 获取父步骤为stepId的步骤
     *
     * @param testCaseStep
     * @return
     */
    List<TestCaseStep> getTestCaseStepByParentId(TestCaseStep testCaseStep);

    /**
     * 获取步骤下的子步骤
     *
     * @param testCaseStep
     * @return
     */
    List<TestCaseStep> getChildrenTestCaseStep(TestCaseStep testCaseStep);

    /**
     * 根据条件获取步骤
     *
     * @param testCaseStep
     * @return
     */
    List<TestCaseStep> getTestCaseStepByCondition(TestCaseStep testCaseStep);


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
     * 删除父步骤
     *
     * @param testCaseStep
     * @return
     */
    boolean deleteParentStep(TestCaseStep testCaseStep);

    /**
     * 根据步骤id获取该步骤的所有父步骤
     *
     * @param stepId
     * @return
     */
    List<TestCaseStep> getParentStepList(Integer stepId);

    /**
     * 获取步骤下的子步骤
     *
     * @param stepId
     * @return
     */
    List<TestCaseStep> getChildrenStepList(Integer stepId);

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
     * 获取所有父步骤的步骤和属性
     *
     * @param stepId
     * @return
     */
    List<TestCaseTree> getParentStepAndAttrById(Integer stepId);
}
