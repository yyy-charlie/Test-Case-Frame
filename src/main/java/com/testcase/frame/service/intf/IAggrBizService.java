package com.testcase.frame.service.intf;

import com.testcase.frame.bean.StepModuleBo;
import com.testcase.frame.pojo.StepAttr;
import com.testcase.frame.pojo.StepModule;
import com.testcase.frame.pojo.TestCaseStep;
import com.testcase.frame.vo.StepModuleVo;
import com.testcase.frame.vo.TestCaseTree;
import com.testcase.frame.vo.TestCaseVo;

import java.util.List;

/**
 * @ClassName IAggrBizService
 * @Description TODO
 * @Author ycn
 * @Date 2019-09-26
 **/
public interface IAggrBizService {
    /**
     * 新增步骤模块
     *
     * @param stepModuleBo
     * @return
     */
    StepModule addStepModule(StepModuleBo stepModuleBo);

    /**
     * 新增步骤和属性
     *
     * @param testCaseTree
     * @return
     */
    TestCaseTree addTestCaseStepAndAttr(TestCaseTree testCaseTree);

    /**
     * 根据系统id获取步骤模块
     *
     * @param stepModule
     * @return
     */
    List<StepModuleVo> getStepModuleVoBySystemId(StepModule stepModule);

    /**
     * 预览生成的Excel
     *
     * @param testCaseStep
     * @return
     */
    List<TestCaseVo> previewExcel(TestCaseStep testCaseStep);

    /**
     * 获取系统的测试用例Vo
     *
     * @param testCaseStep
     * @return
     */
    List<TestCaseVo> getTestCaseVoBySystem(TestCaseStep testCaseStep);

    /**
     * 删除步骤模块
     *
     * @param stepModule
     * @return
     */
    boolean deleteStepModule(StepModule stepModule);

    /**
     * 更新步骤（步骤和属性）
     *
     * @param testCaseTree
     * @return
     */
    boolean updateStep(TestCaseTree testCaseTree);

    /**
     * 批量删除步骤（包括属性和绑定的模块）
     *
     * @param testCaseStepList
     * @return
     */
    boolean batchDeleteStepWithOther(List<TestCaseStep> testCaseStepList);

    /**
     * 获取系统的测试用例步骤树
     *
     * @param testCaseStep
     * @return
     */
    List<TestCaseTree> getTestCaseTreeBySystemId(TestCaseStep testCaseStep);

    /**
     * 根据步骤id查询步骤属性 + 步骤绑定的模块
     *
     * @param stepId
     * @return
     */
    TestCaseTree getStepAttrById(Integer stepId);

    /**
     * 根据模块id获取模块的步骤
     *
     * @param stepModule
     * @return
     */
    List<TestCaseTree> getModuleStepListById(StepModule stepModule);

    /**
     * 更新模块步骤
     *
     * @param stepModuleBo
     * @return
     */
    StepModule updateModuleStep(StepModuleBo stepModuleBo);
}
