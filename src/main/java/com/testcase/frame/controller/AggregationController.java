package com.testcase.frame.controller;

import com.testcase.frame.bean.StepModuleBo;
import com.testcase.frame.common.BaseResponse;
import com.testcase.frame.common.ParseUtils;
import com.testcase.frame.common.Util;
import com.testcase.frame.common.document.IExportUtils;
import com.testcase.frame.common.util.ToolUtils;
import com.testcase.frame.pojo.StepModule;
import com.testcase.frame.pojo.TestCaseStep;
import com.testcase.frame.service.intf.IAggrBizService;
import com.testcase.frame.vo.StepModuleVo;
import com.testcase.frame.vo.TestCaseTree;
import com.testcase.frame.vo.TestCaseVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @ClassName AggregationController
 * @Description TODO
 * @Author ycn
 * @Date 2019-09-26
 **/
@ResponseBody
@RestController
@RequestMapping("/aggregation")
public class AggregationController {

    private static final Logger logger = LogManager.getLogger();

    private IAggrBizService aggrBizService;
    private IExportUtils iExportUtils;


//    @Autowired
//    HttpServletResponse httpServletResponse;

    @Autowired
    public AggregationController(IAggrBizService aggrBizService, IExportUtils iExportUtils) {
        this.aggrBizService = aggrBizService;
        this.iExportUtils = iExportUtils;
    }

    @RequestMapping("/addStep")
    public BaseResponse addStep(@RequestBody TestCaseTree testCaseTree) {
        String description = "添加测试用例步骤";
        logger.debug(description + "传入参数：[{}]", testCaseTree);
        BaseResponse baseResponse;
        if (ToolUtils.isNull(testCaseTree)) {
            baseResponse = ParseUtils.parse2Response(HttpStatus.PRECONDITION_FAILED, description + "参数为空");
            logger.debug(description + "返回结果：[{}]", baseResponse);
            return baseResponse;
        }

        TestCaseTree testCaseTree1 = aggrBizService.addTestCaseStepAndAttr(testCaseTree);
        if (ToolUtils.isNull(testCaseTree1)) {
            baseResponse = ParseUtils.parse2Response(HttpStatus.INTERNAL_SERVER_ERROR, description + "失败");
        } else {
            baseResponse = ParseUtils.parse2Response(HttpStatus.OK, description + "成功", testCaseTree1);
        }
        logger.debug(description + "返回结果：[{}]", baseResponse);
        return baseResponse;
    }

    /**
     * 查询系统的步骤树
     *
     * @param testCaseStep
     * @return
     */
    @RequestMapping("/getTestCaseTreeBySystemId")
    public BaseResponse getTestCaseListBySystemId(@RequestBody TestCaseStep testCaseStep) {
        String description = "根据系统id查询测试步骤";
        logger.debug(description + "传入参数：[{}]", testCaseStep);
        BaseResponse baseResponse;
        if (ToolUtils.isNull(testCaseStep)) {
            baseResponse = ParseUtils.parse2Response(HttpStatus.PRECONDITION_FAILED, description + "参数为空");
            logger.debug(description + "返回结果：[{}]", baseResponse);
            return baseResponse;
        }
        Integer systemId = testCaseStep.getSystemId();
        Integer moduleId = testCaseStep.getModuleId();
        if (ToolUtils.isNull(systemId, moduleId)) {
            baseResponse = ParseUtils.parse2Response(HttpStatus.PRECONDITION_FAILED, description + "参数为空");
            logger.debug(description + "返回结果：[{}]", baseResponse);
            return baseResponse;
        }
        List<TestCaseTree> testCaseTreeList = aggrBizService.getTestCaseTreeBySystemId(testCaseStep);
        baseResponse = ParseUtils.parse2Response(HttpStatus.OK, description + "成功", testCaseTreeList);
        logger.debug(description + "返回结果：[{}]", baseResponse);
        return baseResponse;
    }

    /**
     * 根据步骤id查询步骤属性
     *
     * @param testCaseStep
     * @return
     */
    @RequestMapping("/getStepAttrById")
    public BaseResponse getStepAttrById(@RequestBody TestCaseStep testCaseStep) {
        String description = "根据步骤id查询步骤属性";
        logger.debug(description + "传入参数：[{}]", testCaseStep);
        BaseResponse baseResponse;
        if (ToolUtils.isNull(testCaseStep)) {
            baseResponse = ParseUtils.parse2Response(HttpStatus.PRECONDITION_FAILED, description + "参数为空");
            logger.debug(description + "返回结果：[{}]", baseResponse);
            return baseResponse;
        }
        Integer stepId = testCaseStep.getStepId();
        if (ToolUtils.isNull(stepId)) {
            baseResponse = ParseUtils.parse2Response(HttpStatus.PRECONDITION_FAILED, description + "参数为空");
            logger.debug(description + "返回结果：[{}]", baseResponse);
            return baseResponse;
        }
        TestCaseTree testCaseTree = aggrBizService.getStepAttrById(stepId);
        baseResponse = ParseUtils.parse2Response(HttpStatus.OK, description + "成功", testCaseTree);
        logger.debug(description + "返回结果：[{}]", baseResponse);
        return baseResponse;
    }

    /**
     * 更新步骤（步骤+属性）
     *
     * @param testCaseTree
     * @return
     */
    @RequestMapping("/updateStep")
    public BaseResponse updateStep(@RequestBody TestCaseTree testCaseTree) {
        String description = "更新步骤";
        logger.debug(description + "传入参数：[{}]", testCaseTree);
        boolean b = aggrBizService.updateStep(testCaseTree);
        return Util.getBaseResponse(b, description);
    }

    /**
     * 删除测试用例步骤
     *
     * @param testCaseStepList
     * @return
     */
    @RequestMapping("/deleteTestStep")
    public BaseResponse deleteTestStep(@RequestBody List<TestCaseStep> testCaseStepList) {
        String description = "删除测试用例步骤";
        BaseResponse baseResponse;
        if (ToolUtils.isNull(testCaseStepList)) {
            baseResponse = ParseUtils.parse2Response(HttpStatus.PRECONDITION_FAILED, description + "参数为空");
            logger.debug(description + "返回结果：[{}]", baseResponse);
            return baseResponse;
        }
        boolean b = aggrBizService.batchDeleteStepWithOther(testCaseStepList);
        baseResponse = Util.getBaseResponseByBool(b, description);
        logger.debug(description + "返回结果：[{}]", baseResponse);
        return baseResponse;
    }

    /**
     * 新增步骤模块
     *
     * @param stepModuleBo
     * @return
     */
    @RequestMapping("/addStepModule")
    public BaseResponse addStepModule(@RequestBody StepModuleBo stepModuleBo) {
        String description = "新增步骤模块";
        logger.debug(description + "传入参数：[{}]", stepModuleBo);
        BaseResponse baseResponse;
        StepModule stepModule = aggrBizService.addStepModule(stepModuleBo);
        if (ToolUtils.isNull(stepModule)) {
            baseResponse = ParseUtils.parse2Response(HttpStatus.INTERNAL_SERVER_ERROR, description + "失败");
            logger.debug(description + "返回结果：[{}]", baseResponse);
            return baseResponse;
        }
        baseResponse = ParseUtils.parse2Response(HttpStatus.OK, description + "成功", stepModule);
        logger.debug(description + "返回结果：[{}]", baseResponse);
        return baseResponse;
    }

    /**
     * 根据系统id获取模块步骤
     *
     * @param stepModule
     * @return
     */
    @RequestMapping("/getStepModuleVoBySystemId")
    public BaseResponse getStepModuleVoBySystemId(@RequestBody StepModule stepModule) {
        String description = "根据系统id获取模块步骤";
        logger.debug(description + "传入参数：[{}]", stepModule);
        List<StepModuleVo> stepModuleVoList = aggrBizService.getStepModuleVoBySystemId(stepModule);
        BaseResponse baseResponse = ParseUtils.parse2Response(HttpStatus.OK, description + "成功", stepModuleVoList);
        logger.debug(description + "返回结果：[{}]", baseResponse);
        return baseResponse;
    }

    /**
     * 预览生成的Excel
     *
     * @param testCaseStep
     * @return
     */
    @RequestMapping("/getTestCaseVoBySystem")
    public BaseResponse getTestCaseVoBySystem(@RequestBody TestCaseStep testCaseStep) {
        String description = "获取系统下的测试用例";
        logger.debug(description + "传入参数：[{}]", testCaseStep);
        List<TestCaseVo> testCaseVoList = aggrBizService.previewExcel(testCaseStep);
        BaseResponse baseResponse = ParseUtils.parse2Response(HttpStatus.OK, description + "成功", testCaseVoList);
        logger.debug(description + "返回结果：[{}]", baseResponse);
        return baseResponse;
    }

    /**
     * 生成测试用例Excel
     *
     * @param testCaseStep
     */
    @RequestMapping("/generateTestCasesExcel")
    public void generateTestCasesExcel(TestCaseStep testCaseStep, HttpServletResponse response) {
        String description = "生成测试用例Excel";
        logger.debug(description + "传入参数：[{}]", testCaseStep);
        List<TestCaseVo> testCaseVoList = aggrBizService.getTestCaseVoBySystem(testCaseStep);
        iExportUtils.exportSimpleExcelFile("xx系统的测试用例", testCaseVoList, (short) 25, response, true, false);
    }

    /**
     * 根据模块id获取模块步骤
     *
     * @param stepModule
     * @return
     */
    @RequestMapping("/getModuleStepListById")
    public BaseResponse getModuleStepListById(@RequestBody StepModule stepModule) {
        String description = "根据模块id获取模块步骤";
        logger.debug(description + "传入参数：[{}]", stepModule);
        List<TestCaseTree> testCaseTreeList = aggrBizService.getModuleStepListById(stepModule);
        BaseResponse baseResponse = ParseUtils.parse2Response(HttpStatus.OK, description + "成功", testCaseTreeList);
        logger.debug(description + "返回结果：[{}]", baseResponse);
        return baseResponse;
    }

    @RequestMapping("/updateModuleStep")
    public BaseResponse updateModuleStep(@RequestBody StepModuleBo stepModuleBo) {
        String description = "更新模块步骤";
        logger.debug(description + "传入参数：[{}]", stepModuleBo);
        StepModule stepModule = aggrBizService.updateModuleStep(stepModuleBo);
        BaseResponse baseResponse;
        if (ToolUtils.isNull(stepModule)) {
            baseResponse = ParseUtils.parse2Response(HttpStatus.INTERNAL_SERVER_ERROR, description + "失败");
        } else {
            baseResponse = ParseUtils.parse2Response(HttpStatus.OK, description + "成功", stepModule);
        }
        logger.debug(description + "返回结果：[{}]", baseResponse);
        return baseResponse;
    }

    /**
     * 删除步骤模块
     *
     * @param stepModule
     * @return
     */
    @RequestMapping("/deleteStepModule")
    public BaseResponse deleteStepModule(@RequestBody StepModule stepModule) {
        String description = "删除步骤模块";
        logger.debug(description + "传入参数：[{}]", stepModule);
        boolean b = aggrBizService.deleteStepModule(stepModule);
        BaseResponse baseResponse = Util.getBaseResponseByBool(b, description);
        logger.debug(description + "返回结果：[{}]", baseResponse);
        return baseResponse;
    }

}
