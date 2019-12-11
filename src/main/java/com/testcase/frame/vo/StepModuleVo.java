package com.testcase.frame.vo;

import com.testcase.frame.pojo.TestCaseStep;
import lombok.Data;

import java.util.List;

/**
 * @ClassName StepModuleVo
 * @Description TODO
 * @Author ycn
 * @Date 2019-09-26
 **/
@Data
public class StepModuleVo {

    private Integer moduleId;
    private String moduleName;
    private List<TestCaseStep> testCaseStepList;
}
