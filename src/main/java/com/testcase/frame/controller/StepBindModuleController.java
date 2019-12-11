package com.testcase.frame.controller;

import com.testcase.frame.bean.StepModuleBo;
import com.testcase.frame.common.BaseResponse;
import com.testcase.frame.common.ParseUtils;
import com.testcase.frame.common.Util;
import com.testcase.frame.pojo.StepBindModule;
import com.testcase.frame.pojo.StepModule;
import com.testcase.frame.service.intf.IStepBindModuleService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName StepBindModuleController
 * @Description TODO
 * @Author ycn
 * @Date 2019-10-11
 **/
@RestController
@ResponseBody
@RequestMapping("/bind")
public class StepBindModuleController {

    private static final Logger logger = LogManager.getLogger();

    private IStepBindModuleService stepBindModuleService;

    @Autowired
    public StepBindModuleController(IStepBindModuleService stepBindModuleService) {
        this.stepBindModuleService = stepBindModuleService;
    }

    /**
     * 步骤绑定模块
     *
     * @param stepModuleBo
     * @return
     */
    @RequestMapping("/stepBindModule")
    public BaseResponse stepBindModule(@RequestBody StepModuleBo stepModuleBo) {
        String description = "步骤绑定模块";
        logger.debug(description + "传来的参数：[{}]", stepModuleBo);
        boolean b = stepBindModuleService.batchAddBindList(stepModuleBo);
        return Util.getBaseResponse(b, description);
    }

    /**
     * 批量取消绑定步骤与模块的关系
     *
     * @param stepModuleBo
     * @return
     */
    @RequestMapping("/batchDelBindRelation")
    public BaseResponse batchDelBindRelation(@RequestBody StepModuleBo stepModuleBo) {
        String description = "批量取消绑定步骤与模块的关系";
        logger.debug(description + "传来的参数：[{}]", stepModuleBo);
        boolean b = stepBindModuleService.batchDelBindList(stepModuleBo);
        return Util.getBaseResponse(b, description);
    }

    /**
     * 删除步骤与模块的绑定关系
     *
     * @param stepBindModule
     * @return
     */
    @RequestMapping("/deleteBindRelation")
    public BaseResponse deleteBindRelation(@RequestBody StepBindModule stepBindModule) {
        String description = "删除步骤与模块的绑定关系";
        logger.debug(description + "传来的参数：[{}]", stepBindModule);
        boolean b = stepBindModuleService.deleteStepBindModule(stepBindModule);
        return Util.getBaseResponse(b, description);
    }
}
