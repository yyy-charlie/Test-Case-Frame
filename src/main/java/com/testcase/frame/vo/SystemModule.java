package com.testcase.frame.vo;

import com.testcase.frame.pojo.StepSystem;
import com.testcase.frame.pojo.StepSystemModule;
import lombok.Data;

import java.util.List;

/**
 * @ClassName SystemModule
 * @Description TODO
 * @Author ycn
 * @Date 2019-10-24
 **/
@Data
public class SystemModule {
    private List<StepSystem> stepSystemList;
    private List<StepSystemModule> stepSystemModuleList;
}
