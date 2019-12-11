package com.testcase.frame.pojo;

import javax.persistence.*;

@Table(name = "step_bind_module")
public class StepBindModule {
    @Column(name = "step_id")
    @Id
    private Integer stepId;

    @Column(name = "module_id")
    private Integer moduleId;

    /**
     * @return step_id
     */
    public Integer getStepId() {
        return stepId;
    }

    /**
     * @param stepId
     */
    public void setStepId(Integer stepId) {
        this.stepId = stepId;
    }

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
}