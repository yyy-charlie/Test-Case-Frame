package com.testcase.frame.pojo;

import lombok.Data;

import javax.persistence.*;

@Table(name = "test_case_step")
@Data
public class TestCaseStep {

    @Id
    @GeneratedValue(generator = "JDBC")
    @Column(name = "step_id")
    private Integer stepId;

    @Column(name = "step_name")
    private String stepName;

    @Column(name = "step_parent_id")
    private Integer stepParentId;

    @Column(name = "single_step_mark_id")
    private Integer singleStepMarkId;
    /**
     * 生成步骤标记
     */
    @Column(name = "last_step_mark_id")
    private Integer lastStepMarkId;

    @Column(name = "system_id")
    private Integer systemId;

    @Column(name = "module_id")
    private Integer moduleId;

    public TestCaseStep() {
    }

    public TestCaseStep(String stepName) {
        this.stepName = stepName;
    }

    public TestCaseStep(String stepName, Integer stepParentId, Integer systemId, Integer moduleId, Integer singleStepMarkId, Integer lastStepMarkId) {
        this.stepName = stepName;
        this.stepParentId = stepParentId;
        this.systemId = systemId;
        this.moduleId = moduleId;
        this.singleStepMarkId = singleStepMarkId;
        this.lastStepMarkId = lastStepMarkId;
    }

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
     * @return step_name
     */
    public String getStepName() {
        return stepName;
    }

    /**
     * @param stepName
     */
    public void setStepName(String stepName) {
        this.stepName = stepName;
    }

    /**
     * @return step_parent_id
     */
    public Integer getStepParentId() {
        return stepParentId;
    }

    /**
     * @param stepParentId
     */
    public void setStepParentId(Integer stepParentId) {
        this.stepParentId = stepParentId;
    }

    public Integer getSingleStepMarkId() {
        return singleStepMarkId;
    }

    public void setSingleStepMarkId(Integer singleStepMarkId) {
        this.singleStepMarkId = singleStepMarkId;
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

    public Integer getLastStepMarkId() {
        return lastStepMarkId;
    }

    public void setLastStepMarkId(Integer lastStepMarkId) {
        this.lastStepMarkId = lastStepMarkId;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }
}