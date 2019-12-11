package com.testcase.frame.vo;

import com.testcase.frame.common.annotation.AttrName;
import com.testcase.frame.pojo.StepAttr;
import lombok.Data;

import java.util.List;

/**
 * @ClassName TestCaseVo
 * @Description TODO
 * @Author ycn
 * @Date 2019-09-27
 **/
@Data
public class TestCaseVo {
    /**
     * 所属模块
     */
    @AttrName("所属模块")
    private String manageModule;
    /**
     * 用例标题
     */
    @AttrName("用例标题")
    private String testCaseTitle;
    /**
     * 前置条件
     */
    @AttrName("前置条件")
    private String precondition;
    /**
     * 用例步骤
     */
    @AttrName("步骤")
    private List<String> stepList;
    /**
     * 期望
     */
    @AttrName("预期")
    private String expected;
    /**
     * 优先级
     */
    @AttrName("优先级")
    private String priority;
    /**
     * 用例类型
     */
    @AttrName("用例类型")
    private String testCaseType;
    /**
     * 适用阶段
     */
    @AttrName("适用阶段")
    private String applicableStage;

}
