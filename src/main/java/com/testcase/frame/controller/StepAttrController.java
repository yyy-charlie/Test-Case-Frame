package com.testcase.frame.controller;

import com.testcase.frame.common.BaseResponse;
import com.testcase.frame.common.Util;
import com.testcase.frame.pojo.StepAttr;
import com.testcase.frame.service.intf.IStepAttrService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName StepAttrController
 * @Description TODO
 * @Author ycn
 * @Date 2019-09-27
 **/
@RestController
@ResponseBody
@RequestMapping("/stepAttr")
public class StepAttrController {

    private static final Logger logger = LogManager.getLogger();

    private IStepAttrService stepAttrService;

    @Autowired
    public StepAttrController(IStepAttrService stepAttrService) {
        this.stepAttrService = stepAttrService;
    }

    /**
     * 修改步骤属性
     *
     * @param stepAttr
     * @return
     */
    @RequestMapping("/updateStepAttr")
    public BaseResponse updateStepAttr(@RequestBody StepAttr stepAttr) {
        String description = "修改步骤属性";
        logger.debug(description + "传来的参数：[{}]", stepAttr);
        boolean b = stepAttrService.updateStepAttr(stepAttr);
        BaseResponse baseResponse = Util.getBaseResponseByBool(b, description);
        logger.debug(description + "返回的结果：[{}]", baseResponse);
        return baseResponse;
    }


}
