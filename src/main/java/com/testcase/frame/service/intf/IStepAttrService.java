package com.testcase.frame.service.intf;

import com.testcase.frame.pojo.StepAttr;

import java.util.List;

/**
 * @ClassName IStepAttrService
 * @Description 步骤属性服务
 * @Author ycn
 * @Date 2019-09-26
 **/
public interface IStepAttrService {
    /**
     * 新增步骤属性
     *
     * @param stepAttr
     * @return
     */
    StepAttr addStepAttr(StepAttr stepAttr);

    /**
     * 修改步骤属性
     *
     * @param stepAttr
     * @return
     */
    boolean updateStepAttr(StepAttr stepAttr);

    /**
     * 根据步骤id获取步骤属性
     *
     * @param stepId
     * @return
     */
    StepAttr getStepAttrByStepId(Integer stepId);

    /**
     * 批量删除属性
     *
     * @param stepAttrList
     */
    void batchDeleteStepAttr(List<StepAttr> stepAttrList);

    /**
     * 删除步骤属性
     *
     * @param stepAttr
     * @return
     */
    boolean deleteStepAttr(StepAttr stepAttr);
}
