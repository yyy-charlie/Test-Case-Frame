package com.testcase.frame.service.intf;

import com.testcase.frame.pojo.StepModule;

import java.util.List;

/**
 * @ClassName IStepModuleService
 * @Description 步骤模块服务
 * @Author ycn
 * @Date 2019-09-26
 **/
public interface IStepModuleService {
    /**
     * 新增步骤模块
     *
     * @param stepModule
     * @return
     */
    StepModule addStepModule(StepModule stepModule);

    /**
     * 根据条件查询步骤集合
     *
     * @param stepModule
     * @return
     */
    List<StepModule> getStepModuleListByCondition(StepModule stepModule);

    /**
     * 更新模块
     *
     * @param stepModule
     * @return
     */
    boolean updateModule(StepModule stepModule);

    /**
     * 删除步骤模块
     *
     * @param stepModule
     * @return
     */
    boolean deleteStepModule(StepModule stepModule);

}
