package com.testcase.frame.controller;

import com.testcase.frame.bean.StepModuleBo;
import com.testcase.frame.common.BaseResponse;
import com.testcase.frame.common.ParseUtils;
import com.testcase.frame.pojo.StepModule;
import com.testcase.frame.service.intf.IAggrBizService;
import com.testcase.frame.service.intf.IStepModuleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName StepModuleController
 * @Description TODO
 * @Author ycn
 * @Date 2019-09-26
 **/
@RestController
@ResponseBody
@RequestMapping("/stepModule")
public class StepModuleController {

    private static final Logger logger = LogManager.getLogger();

    private IStepModuleService stepModuleService;

    @Autowired
    public StepModuleController(IStepModuleService stepModuleService) {
        this.stepModuleService = stepModuleService;
    }

    /**
     * 查询步骤模块
     *
     * @param stepModule
     * @return
     */
    @RequestMapping("/getStepModule")
    public BaseResponse getStepModule(@RequestBody StepModule stepModule) {
        String description = "查询步骤模块表格";
        logger.debug(description + "传入参数：[{}]", stepModule);
        List<StepModule> stepModuleList = stepModuleService.getStepModuleListByCondition(stepModule);
        BaseResponse baseResponse = ParseUtils.parse2Response(HttpStatus.OK, description + "成功", stepModuleList);
        logger.debug(description + "返回结果：[{}]", baseResponse);
        return baseResponse;
    }


}
