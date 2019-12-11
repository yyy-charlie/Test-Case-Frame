package com.testcase.frame.pojo;

import javax.persistence.*;

@Table(name = "step_attr")
public class StepAttr {
    /**
     * ????????????
     */
    @Column(name = "test_case_type")
    private String testCaseType;

    /**
     * ????????????
     */
    @Column(name = "test_case_title")
    private String testCaseTitle;

    /**
     * ????id
     */
    @Column(name = "step_id")
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer stepId;

    /**
     * ???ȼ?
     */
    @Column(name = "priority")
    private String priority;

    /**
     * ǰ??????
     */
    @Column(name = "precondition")
    private String precondition;

    /**
     * ??һ????ģ??id
     */
    @Column(name = "manage_module")
    private String manageModule;

    /**
     * Ԥ??
     */
    @Column(name = "expected")
    private String expected;

    /**
     * ʹ?ý׶?
     */
    @Column(name = "applicable_stage")
    private String applicableStage;

    /**
     * 获取????????????
     *
     * @return test_case_type - ????????????
     */
    public String getTestCaseType() {
        return testCaseType;
    }

    /**
     * 设置????????????
     *
     * @param testCaseType ????????????
     */
    public void setTestCaseType(String testCaseType) {
        this.testCaseType = testCaseType;
    }

    /**
     * 获取????????????
     *
     * @return test_case_title - ????????????
     */
    public String getTestCaseTitle() {
        return testCaseTitle;
    }

    /**
     * 设置????????????
     *
     * @param testCaseTitle ????????????
     */
    public void setTestCaseTitle(String testCaseTitle) {
        this.testCaseTitle = testCaseTitle;
    }

    /**
     * 获取????id
     *
     * @return step_id - ????id
     */
    public Integer getStepId() {
        return stepId;
    }

    /**
     * 设置????id
     *
     * @param stepId ????id
     */
    public void setStepId(Integer stepId) {
        this.stepId = stepId;
    }

    /**
     * 获取???ȼ?
     *
     * @return priority - ???ȼ?
     */
    public String getPriority() {
        return priority;
    }

    /**
     * 设置???ȼ?
     *
     * @param priority ???ȼ?
     */
    public void setPriority(String priority) {
        this.priority = priority;
    }

    /**
     * 获取ǰ??????
     *
     * @return precondition - ǰ??????
     */
    public String getPrecondition() {
        return precondition;
    }

    /**
     * 设置ǰ??????
     *
     * @param precondition ǰ??????
     */
    public void setPrecondition(String precondition) {
        this.precondition = precondition;
    }

    public String getManageModule() {
        return manageModule;
    }

    public void setManageModule(String manageModule) {
        this.manageModule = manageModule;
    }

    /**
     * 获取Ԥ??
     *
     * @return expected - Ԥ??
     */
    public String getExpected() {
        return expected;
    }

    /**
     * 设置Ԥ??
     *
     * @param expected Ԥ??
     */
    public void setExpected(String expected) {
        this.expected = expected;
    }

    public String getApplicableStage() {
        return applicableStage;
    }

    public void setApplicableStage(String applicableStage) {
        this.applicableStage = applicableStage;
    }
}