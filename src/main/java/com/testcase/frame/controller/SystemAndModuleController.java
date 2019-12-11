package com.testcase.frame.controller;

import com.testcase.frame.common.BaseResponse;
import com.testcase.frame.common.ParseUtils;
import com.testcase.frame.pojo.StepSystem;
import com.testcase.frame.pojo.StepSystemModule;
import com.testcase.frame.service.intf.ISystemModuleService;
import com.testcase.frame.vo.SystemModule;
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
 * @ClassName SystemAndModule
 * @Description TODO
 * @Author ycn
 * @Date 2019-10-24
 **/
@RestController
@ResponseBody
@RequestMapping("/systemAndModule")
public class SystemAndModuleController {
    private static final Logger logger = LogManager.getLogger();

    private ISystemModuleService systemModuleService;

    @Autowired
    public SystemAndModuleController(ISystemModuleService systemModuleService) {
        this.systemModuleService = systemModuleService;
    }

    @RequestMapping("/getAll")
    public BaseResponse getAll() {
        String description = "获取所有的系统和模块";
        SystemModule systemModule = systemModuleService.getAllSystemAndModule();
        BaseResponse baseResponse = ParseUtils.parse2Response(HttpStatus.OK, description + "成功", systemModule);
        logger.debug(description + "返回结果：[{}]", baseResponse);
        return baseResponse;
    }

    /**
     * 根据系统id获取模块信息
     *
     * @param stepSystem
     * @return
     */
    @RequestMapping("/getModuleBySystemId")
    public BaseResponse getSystemAndModuleBySystemId(@RequestBody StepSystem stepSystem) {
        String description = "根据系统id获取模块信息";
        List<StepSystemModule> stepSystemModuleList = systemModuleService.getSystemAndModuleBySystemId(stepSystem);
        BaseResponse baseResponse = ParseUtils.parse2Response(HttpStatus.OK, description + "成功", stepSystemModuleList);
        logger.debug(description + "返回结果：[{}]", baseResponse);
        return baseResponse;
    }
}
