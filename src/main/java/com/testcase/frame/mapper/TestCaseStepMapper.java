package com.testcase.frame.mapper;

import com.testcase.frame.pojo.TestCaseStep;
import com.testcase.frame.vo.TestCaseTree;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface TestCaseStepMapper extends Mapper<TestCaseStep> {
    /**
     * 批量新增步骤
     *
     * @param testCaseStepList
     * @return
     */
    Integer batchInsertStepList(@Param("list") List<TestCaseStep> testCaseStepList);

    /**
     * 根据步骤id获取该步骤的所有父步骤
     *
     * @param stepId
     * @return
     */
    List<TestCaseStep> getParentStepList(@Param("stepId") Integer stepId);

    /**
     * 获取步骤下的子步骤
     *
     * @param stepId
     * @return
     */
    List<TestCaseStep> getChildrenStepList(@Param("stepId") Integer stepId);

    /**
     * 批量删除步骤
     *
     * @param testCaseStepList
     * @return
     */
    int batchDeleteStepList(@Param("list") List<TestCaseStep> testCaseStepList);

    /**
     * 获取系统下的步骤树
     *
     * @param systemId
     * @param moduleId
     * @return
     */
    List<TestCaseTree> getTestCaseTreeBySystemId(@Param("systemId") Integer systemId,@Param("moduleId") Integer moduleId);

    /**
     * 获取所有父步骤的步骤和属性
     *
     * @param stepId
     * @return
     */
    List<TestCaseTree> getParentStepAndAttrById(@Param("stepId") Integer stepId);
}