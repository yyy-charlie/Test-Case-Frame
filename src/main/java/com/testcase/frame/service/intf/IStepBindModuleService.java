package com.testcase.frame.service.intf;

import com.testcase.frame.bean.StepModuleBo;
import com.testcase.frame.pojo.StepBindModule;

import java.util.List;

/**
 * @ClassName IStepBindModuleService
 * @Description TODO
 * @Author ycn
 * @Date 2019-10-11
 **/
public interface IStepBindModuleService {
    /**
     * 批量新增步骤绑定模块
     *
     * @param stepModuleBo
     * @return
     */
    boolean batchAddBindList(StepModuleBo stepModuleBo);

    /**
     * 批量取消绑定步骤与模块的关系
     *
     * @param stepModuleBo
     * @return
     */
    boolean batchDelBindList(StepModuleBo stepModuleBo);

    /**
     * 根据条件获取绑定信息
     *
     * @param stepBindModule
     * @return
     */
    List<StepBindModule> getBindInfoByCondition(StepBindModule stepBindModule);

    /**
     * 更新步骤绑定模块
     *
     * @param stepBindModule
     * @return
     */
    boolean updateStepBindModule(StepBindModule stepBindModule);

    /**
     * 新增或更新绑定关系
     *
     * @param stepBindModule
     * @return
     */
    boolean insertOrUpdateBindRelation(StepBindModule stepBindModule);

    /**
     * 删除步骤绑定模块
     *
     * @param stepBindModule
     * @return
     */
    boolean deleteStepBindModule(StepBindModule stepBindModule);

    /**
     * 批量删除步骤与模块的绑定关系
     *
     * @param stepBindModuleList
     */
    void batchDeleteBindRelation(List<StepBindModule> stepBindModuleList);

    /**
     * 批量修改绑定关系
     *
     * @param stepIdList
     * @param moduleId
     */
    boolean batchUpdateBindRelation(List<Integer> stepIdList, int moduleId);
}
