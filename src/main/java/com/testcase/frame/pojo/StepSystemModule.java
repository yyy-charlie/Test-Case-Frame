package com.testcase.frame.pojo;

import javax.persistence.*;

@Table(name = "step_system_module")
public class StepSystemModule {
    @Id
    @Column(name = "module_id")
    private Integer moduleId;

    @Column(name = "module_name")
    private String moduleName;

    @Column(name = "system_id")
    private Integer systemId;

    /**
     * @return module_id
     */
    public Integer getModuleId() {
        return moduleId;
    }

    /**
     * @param moduleId
     */
    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    /**
     * @return module_name
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * @param moduleName
     */
    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

    /**
     * @return system_id
     */
    public Integer getSystemId() {
        return systemId;
    }

    /**
     * @param systemId
     */
    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }
}