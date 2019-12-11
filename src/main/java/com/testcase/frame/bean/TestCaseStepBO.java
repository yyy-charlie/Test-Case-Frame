package com.testcase.frame.bean;


import lombok.Data;

/**
 * @author XXW
 * @version 1.0
 * @created 20-九月-2019 14:07:31
 */
@Data
public class TestCaseStepBO {

    private Integer maxLength;
    private Integer minLength;
    private Integer stepParentId;
    private String stepObj;
    private Integer systemId;
    private Integer moduleId;


    public TestCaseStepBO() {

    }

}