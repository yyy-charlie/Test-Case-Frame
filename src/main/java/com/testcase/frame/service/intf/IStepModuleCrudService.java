package com.testcase.frame.service.intf;

import com.testcase.frame.pojo.StepModule;

import java.util.List;

/**
 * @ClassName IStepModuleCrudService
 * @Description TODO
 * @Author ycn
 * @Date 2019-09-26
 **/
public interface IStepModuleCrudService {

    /**
     * 新增步骤模块
     *
     * @param stepModule
     * @return
     */
    StepModule addStepModule(StepModule stepModule);

    /**
     * 根据条件查询模块集合
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
