package com.testcase.frame.controller;

import com.sun.corba.se.impl.oa.toa.TOA;
import com.testcase.frame.bean.TestCaseStepBO;
import com.testcase.frame.common.BaseResponse;
import com.testcase.frame.common.ParseUtils;
import com.testcase.frame.common.Util;
import com.testcase.frame.common.util.ToolUtils;
import com.testcase.frame.pojo.TestCaseStep;
import com.testcase.frame.service.intf.ITestCaseStepService;
import com.testcase.frame.vo.TestCaseTree;
import com.testcase.frame.vo.TestCaseVo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName TestCaseStepController
 * @Description TODO
 * @Author ycn
 * @Date 2019-09-20
 **/
@ResponseBody
@RestController
@RequestMapping("/testCase")
public class TestCaseStepController {
    private static final Logger logger = LogManager.getLogger();

    private ITestCaseStepService testCaseStepService;

    @Autowired
    public TestCaseStepController(ITestCaseStepService testCaseStepService) {
        this.testCaseStepService = testCaseStepService;
    }

    @RequestMapping("/addTestCaseStepBasedOnStepObj")
    public BaseResponse addTestCaseStepBasedOnStepObj(@RequestBody TestCaseStepBO testCaseStep) {
        String description = "根据步骤对象新增测试用例步骤";
        logger.debug(description + "传入参数：[{}]", testCaseStep);
        BaseResponse baseResponse;
        if (ToolUtils.isNull(testCaseStep)) {
            baseResponse = ParseUtils.parse2Response(HttpStatus.PRECONDITION_FAILED, description + "参数为空");
            logger.debug(description + "返回结果：[{}]", baseResponse);
            return baseResponse;
        }
        List<TestCaseStep> testCaseStepList = testCaseStepService.addTestCaseStepBasedOnStepObj(testCaseStep);
        if (ToolUtils.judgeList(testCaseStepList)) {
            baseResponse = ParseUtils.parse2Response(HttpStatus.OK, description + "成功", testCaseStepList);
        } else {
            baseResponse = ParseUtils.parse2Response(HttpStatus.INTERNAL_SERVER_ERROR, description + "失败");
        }
        logger.debug(description + "返回结果：[{}]", baseResponse);
        return baseResponse;
    }


}
