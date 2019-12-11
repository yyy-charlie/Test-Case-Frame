package com.testcase.frame.pojo;

import javax.persistence.*;

@Table(name = "step_module")
public class StepModule {
    /**
     * ģ??id
     */
    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "module_id")
    private Integer moduleId;

    /**
     * ģ??????
     */
    @Column(name = "module_name")
    private String moduleName;

    /**
     * ??һ???Ĳ???id
     */
    @Column(name = "first_step_id")
    private Integer firstStepId;
    /**
     * 系统id
     */
    @Column(name = "system_id")
    private Integer systemId;
    /**
     * 系统模块id
     */
    @Column(name = "system_module_id")
    private Integer systemModuleId;


    public Integer getSystemModuleId() {
        return systemModuleId;
    }

    public void setSystemModuleId(Integer systemModuleId) {
        this.systemModuleId = systemModuleId;
    }

    public Integer getSystemId() {
        return systemId;
    }

    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

    /**
     * 获取ģ??id
     *
     * @return module_id - ģ??id
     */
    public Integer getModuleId() {
        return moduleId;
    }

    /**
     * 设置ģ??id
     *
     * @param moduleId ģ??id
     */
    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    /**
     * 获取ģ??????
     *
     * @return module_name - ģ??????
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * 设置ģ??????
     *
     * @param moduleName ģ??????
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    public Integer getFirstStepId() {
        return firstStepId;
    }

    public void setFirstStepId(Integer firstStepId) {
        this.firstStepId = firstStepId;
    }
}