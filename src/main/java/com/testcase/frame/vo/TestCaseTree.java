package com.testcase.frame.vo;

import com.testcase.frame.pojo.StepAttr;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * @ClassName TestCaseTree
 * @Description TODO
 * @Author ycn
 * @Date 2019-09-27
 **/
@Data
public class TestCaseTree {
    private Integer id;
    private Integer parentId;
    private String name;
    private Integer singleStepMarkId;
    /**
     * 最后一步步骤标记（0代表不是最后一步，1代表最后一步步骤）
     */
    private Integer lastStepMarkId;

    private StepAttr stepAttr;
    /**
     * 系统模块id
     */
    private Integer systemModuleId;

    /**
     * 步骤模块id
     */
    private Integer moduleId;
    /**
     * 绑定的模块
     */
    private String moduleName;
    /**
     * 系统ID
     */
    private Integer systemId;
    /**
     * 用例标题
     */
    private String testCaseTitle;
    /**
     * 优先级
     */
    private String priority;
    /**
     * 前置条件
     */
    private String precondition;
    /**
     * 所属模块
     */
    private String manageModule;

    /**
     * 预期
     */
    private String expected;
    /**
     * 用例阶段
     * 功能测试
     * 性能测试
     * 配置相关
     * 安装部署
     * 安全相关
     * 接口测试
     * 其他
     */
    private String testCaseType;
    /**
     * 适用阶段
     * 单元测试阶段
     * 功能测试阶段
     * 集成测试阶段
     * 系统测试阶段
     * 冒烟测试阶段
     * 版本验证阶段
     */
    private String applicableStage;
}
