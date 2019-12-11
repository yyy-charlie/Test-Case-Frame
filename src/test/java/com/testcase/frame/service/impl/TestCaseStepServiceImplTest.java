//package com.testcase.frame.service.impl;
//
//import com.testcase.frame.bean.TestCaseStepBO;
//import com.testcase.frame.service.intf.ITestCaseStepService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//
//import static org.hamcrest.core.Is.is;
//import static org.junit.Assert.*;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest()
//@EnableAutoConfiguration
//@Transactional
//public class TestCaseStepServiceImplTest {
//
//    @Autowired
//    ITestCaseStepService testCaseStepService;
//
//    @Test
//    public void addStepAttr() {
//    }
//
//    @Test
//    public void addStepModule() {
//    }
//
//    @Test
//    public void addTestCaseStep() {
//    }
//
//    @Test
//    public void addTestCaseStepBasedOnStepObj() {
//        TestCaseStepBO testCaseStepBO = new TestCaseStepBO();
//        testCaseStepBO.setMinLength(2);
//        testCaseStepBO.setMaxLength(10);
//        testCaseStepBO.setStepObj("姓名");
//        testCaseStepBO.setStepParentId(1);
//        testCaseStepBO.setSystemId(1001);
//        boolean b = testCaseStepService.addTestCaseStepBasedOnStepObj(testCaseStepBO);
//        assertThat(b, is(Boolean.TRUE));
//    }
//
//    @Test
//    public void batchDeleteStepWithOther() {
//    }
//
//    @Test
//    public void testGetSingleStepList() {
//        List<List<String>> singleStepList = testCaseStepService.getParentStepList();
//        System.out.println(singleStepList);
//    }
//
//}