package com.testcase.frame.service.intf;

import com.testcase.frame.pojo.StepSystem;
import com.testcase.frame.pojo.StepSystemModule;
import com.testcase.frame.vo.SystemModule;

import java.util.List;

/**
 * @ClassName ISystemModuleService
 * @Description TODO
 * @Author ycn
 * @Date 2019-10-24
 **/
public interface ISystemModuleService {
    /**
     * 获取所有的系统和模块
     *
     * @return
     */
    SystemModule getAllSystemAndModule();

    /**
     * 根据系统id获取模块信息
     *
     * @param stepSystem
     * @return
     */
    List<StepSystemModule> getSystemAndModuleBySystemId(StepSystem stepSystem);
}
