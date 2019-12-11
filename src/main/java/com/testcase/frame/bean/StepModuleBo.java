package com.testcase.frame.bean;

import com.testcase.frame.pojo.TestCaseStep;
import lombok.Data;

import java.util.List;

/**
 * @ClassName StepModuleBo
 * @Description TODO
 * @Author ycn
 * @Date 2019-09-26
 **/
@Data
public class StepModuleBo {
    /**
     * 模块id
     */
    private Integer moduleId;
    /**
     * 模块名称
     */
    private String moduleName;
    /**
     * 父步骤id
     */
    private Integer stepParentId;
    /**
     * 系统id
     */
    private Integer systemId;
    /**
     * 系统模块id
     */
    private Integer systemModuleId;
    /**
     * 模块步骤集合
     */
    private List<TestCaseStep> stepList;
}
