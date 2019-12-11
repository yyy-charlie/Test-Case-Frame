package com.testcase.frame.service.impl;

import com.testcase.frame.bean.StepModuleBo;
import com.testcase.frame.common.Const;
import com.testcase.frame.common.util.ToolUtils;
import com.testcase.frame.pojo.StepAttr;
import com.testcase.frame.pojo.StepBindModule;
import com.testcase.frame.pojo.StepModule;
import com.testcase.frame.pojo.TestCaseStep;
import com.testcase.frame.service.intf.*;
import com.testcase.frame.vo.StepModuleVo;
import com.testcase.frame.vo.TestCaseTree;
import com.testcase.frame.vo.TestCaseVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @ClassName AggrBizServiceImpl
 * @Description TODO
 * @Author ycn
 * @Date 2019-09-26
 **/
@Service
public class AggrBizServiceImpl implements IAggrBizService {

    private ITestCaseStepService testCaseStepService;
    private IStepModuleService stepModuleService;
    private IStepAttrService stepAttrService;
    private IStepBindModuleService stepBindModuleService;

    public AggrBizServiceImpl() {
    }

    @Autowired
    public AggrBizServiceImpl(ITestCaseStepService testCaseStepService, IStepModuleService stepModuleService, IStepAttrService stepAttrService, IStepBindModuleService stepBindModuleService) {
        this.testCaseStepService = testCaseStepService;
        this.stepModuleService = stepModuleService;
        this.stepAttrService = stepAttrService;
        this.stepBindModuleService = stepBindModuleService;
    }

    private void addTestCaseStepList(List<TestCaseStep> testCaseStepList) {
        if (!ToolUtils.judgeList(testCaseStepList)) {
            return;
        }
        Iterator<TestCaseStep> iterator = testCaseStepList.iterator();
        if (iterator.hasNext()) {
            TestCaseStep next = iterator.next();
            TestCaseStep testCaseStep1 = testCaseStepService.addTestCaseStep(next);
            if (iterator.hasNext()) {
                TestCaseStep testCaseStep2 = iterator.next();
                testCaseStep2.setStepParentId(testCaseStep1.getStepId());
                testCaseStepList.remove(0);
                addTestCaseStepList(testCaseStepList);
            }
        }
    }

    @Override
    public StepModule addStepModule(StepModuleBo stepModuleBo) {
        List<TestCaseStep> stepList = stepModuleBo.getStepList();
        TestCaseStep testCaseStep3 = stepList.get(0);
        TestCaseStep testCaseStep1 = testCaseStepService.addTestCaseStep(testCaseStep3);
        Integer stepId = testCaseStep1.getStepId();
        stepList.remove(0);
        if (ToolUtils.judgeList(stepList)) {
            TestCaseStep testCaseStep = stepList.get(0);
            testCaseStep.setStepParentId(stepId);

            addTestCaseStepList(stepList);
        }

        StepModule stepModule = new StepModule();
        stepModule.setFirstStepId(stepId);
        stepModule.setSystemId(stepModuleBo.getSystemId());
        stepModule.setSystemModuleId(stepModuleBo.getSystemModuleId());
        stepModule.setModuleName(stepModuleBo.getModuleName());
        StepModule stepModule1 = stepModuleService.addStepModule(stepModule);
        if (!ToolUtils.isNull(stepModule1)) {
            return stepModule1;
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public TestCaseTree addTestCaseStepAndAttr(TestCaseTree testCaseTree) {
        TestCaseStep testCaseStep = new TestCaseStep();
        testCaseStep.setStepName(testCaseTree.getName());
        testCaseStep.setStepParentId(testCaseTree.getParentId());
        testCaseStep.setLastStepMarkId(testCaseTree.getLastStepMarkId());
        testCaseStep.setSystemId(testCaseTree.getSystemId());
        testCaseStep.setModuleId(testCaseTree.getModuleId());
        testCaseStep.setSingleStepMarkId(testCaseTree.getSingleStepMarkId());
        TestCaseStep testCaseStep1 = testCaseStepService.addTestCaseStep(testCaseStep);
        if (!ToolUtils.isNull(testCaseStep1)) {
            Integer stepId = testCaseStep1.getStepId();
            testCaseTree.setId(stepId);
            StepAttr stepAttr = testCaseTree.getStepAttr();
            if (ToolUtils.isNull(stepAttr)) {
                return testCaseTree;
            }
            stepAttr.setStepId(stepId);
            StepAttr stepAttr1 = stepAttrService.addStepAttr(stepAttr);
            if (!ToolUtils.isNull(stepAttr1)) {
                return testCaseTree;
            }
        }
        return null;
    }

    @Override
    public List<StepModuleVo> getStepModuleVoBySystemId(StepModule stepModule) {
        List<StepModule> stepModuleList = stepModuleService.getStepModuleListByCondition(stepModule);
        List<StepModuleVo> stepModuleVoList = new ArrayList<>(16);
        for (StepModule stepModule1 : stepModuleList) {
            StepModuleVo stepModuleVo = new StepModuleVo();
            stepModuleVo.setModuleId(stepModule1.getModuleId());
            stepModuleVo.setModuleName(stepModule1.getModuleName());
            Integer stepId = stepModule1.getFirstStepId();
            List<TestCaseStep> stepList = testCaseStepService.getChildrenStepList(stepId);
            stepModuleVo.setTestCaseStepList(stepList);
            stepModuleVoList.add(stepModuleVo);
        }
        return stepModuleVoList;
    }

    @Override
    public List<TestCaseVo> previewExcel(TestCaseStep testCaseStep) {
        List<List<TestCaseStep>> singleStepList = testCaseStepService.getSingleStepList(testCaseStep);
        List<TestCaseVo> testCaseVoList = new ArrayList<>();
        for (List<TestCaseStep> caseStepList : singleStepList) {
            TestCaseVo testCaseVo = new TestCaseVo();
            List<String> stepList = new ArrayList<>();
            int i = 1;
            for (TestCaseStep caseStep : caseStepList) {
                Integer stepId = caseStep.getStepId();
                Integer singleStepMarkId = caseStep.getSingleStepMarkId();
//                Integer generateStepMarkId = caseStep.getSingleStepMarkId();
                String stepName;
                //判断是否是自动生成的步骤
                if (Const.SINGLE_STEP.equals(singleStepMarkId)) {
                    String repeatStep = stepList.get(stepList.size() - 1);
                    stepName = repeatStep + caseStep.getStepName();
                    stepList.remove(stepList.size() - 1);
                } else {
                    stepName = "<br>" + i + "." + caseStep.getStepName();
                }

                stepList.add(stepName);
                if (!Const.REPEAT_STEP.equals(singleStepMarkId)) {
                    i++;
                }

                //获取步骤属性
                StepAttr stepAttr = stepAttrService.getStepAttrByStepId(stepId);
                if (!ToolUtils.isNull(stepAttr)) {
                    stepAttrTransferToVo(testCaseVo, stepAttr);
                }
                //获取步骤绑定的模块
                StepBindModule stepBindModule = new StepBindModule();
                stepBindModule.setStepId(stepId);
                List<StepBindModule> stepBindModuleList = stepBindModuleService.getBindInfoByCondition(stepBindModule);
                if (ToolUtils.judgeList(stepBindModuleList)) {
                    Integer moduleId = stepBindModuleList.get(0).getModuleId();
                    StepModule stepModule = new StepModule();
                    stepModule.setModuleId(moduleId);
                    //查询步骤绑定的模块
                    List<StepModule> stepModuleList = stepModuleService.getStepModuleListByCondition(stepModule);
                    if (ToolUtils.judgeList(stepModuleList)) {
                        StepModule stepModule1 = stepModuleList.get(0);
                        Integer firstStepId = stepModule1.getFirstStepId();
                        List<TestCaseStep> childrenStepList = testCaseStepService.getChildrenStepList(firstStepId);
                        for (TestCaseStep caseStep1 : childrenStepList) {
                            String stepName2 = "<br>" + i + "." + caseStep1.getStepName();
                            stepList.add(stepName2);
                            i++;
                        }
                    }
                }
            }
            testCaseVo.setStepList(stepList);
            testCaseVoList.add(testCaseVo);
        }
        return testCaseVoList;
    }

    @Override
    public List<TestCaseVo> getTestCaseVoBySystem(TestCaseStep testCaseStep) {
        List<List<TestCaseStep>> singleStepList = testCaseStepService.getSingleStepList(testCaseStep);
        List<TestCaseVo> testCaseVoList = new ArrayList<>();
        for (List<TestCaseStep> caseStepList : singleStepList) {
            TestCaseVo testCaseVo = new TestCaseVo();
            List<String> stepList = new ArrayList<>();
            int i = 1;
            for (TestCaseStep caseStep : caseStepList) {
                Integer stepId = caseStep.getStepId();
                Integer singleStepMarkId = caseStep.getSingleStepMarkId();
//                Integer generateStepMarkId = caseStep.getSingleStepMarkId();
                String stepName;
                //判断是否是自动生成的步骤
                if (Const.SINGLE_STEP.equals(singleStepMarkId)) {
                    String repeatStep = stepList.get(stepList.size() - 1);
                    stepName = repeatStep + caseStep.getStepName();
                    stepList.remove(stepList.size() - 1);
                } else {
                    stepName = i + "." + caseStep.getStepName();
                }

                stepList.add(stepName);
                if (!Const.REPEAT_STEP.equals(singleStepMarkId)) {
                    i++;
                }

                //获取步骤属性
                StepAttr stepAttr = stepAttrService.getStepAttrByStepId(stepId);
                if (!ToolUtils.isNull(stepAttr)) {
                    stepAttrTransferToVo(testCaseVo, stepAttr);
                }
                //获取步骤绑定的模块
                StepBindModule stepBindModule = new StepBindModule();
                stepBindModule.setStepId(stepId);
                List<StepBindModule> stepBindModuleList = stepBindModuleService.getBindInfoByCondition(stepBindModule);
                if (ToolUtils.judgeList(stepBindModuleList)) {
                    Integer moduleId = stepBindModuleList.get(0).getModuleId();
                    StepModule stepModule = new StepModule();
                    stepModule.setModuleId(moduleId);
                    //查询步骤绑定的模块
                    List<StepModule> stepModuleList = stepModuleService.getStepModuleListByCondition(stepModule);
                    if (ToolUtils.judgeList(stepModuleList)) {
                        StepModule stepModule1 = stepModuleList.get(0);
                        Integer firstStepId = stepModule1.getFirstStepId();
                        List<TestCaseStep> childrenStepList = testCaseStepService.getChildrenStepList(firstStepId);
                        for (TestCaseStep caseStep1 : childrenStepList) {
                            String stepName2 = i + "." + caseStep1.getStepName();
                            stepList.add(stepName2);
                            i++;
                        }
                    }
                }
            }
            testCaseVo.setStepList(stepList);
            testCaseVoList.add(testCaseVo);
        }
        return testCaseVoList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteStepModule(StepModule stepModule) {
        //查询该模块的步骤信息
        List<StepModule> stepModuleList = stepModuleService.getStepModuleListByCondition(stepModule);
        if (ToolUtils.judgeList(stepModuleList)) {
            StepModule stepModule1 = stepModuleList.get(0);
            Integer stepId = stepModule1.getFirstStepId();
            List<TestCaseStep> testCaseStepList = testCaseStepService.getChildrenStepList(stepId);
            if (ToolUtils.judgeList(testCaseStepList)) {
                //删除模块中的步骤
                boolean b = testCaseStepService.batchDeleteStepList(testCaseStepList);
                if (b) {
                    //删除步骤
                    boolean b1 = stepModuleService.deleteStepModule(stepModule);
                    if (b1) {
                        StepBindModule stepBindModule = new StepBindModule();
                        stepBindModule.setModuleId(stepModule.getModuleId());
                        List<StepBindModule> stepBindModuleList = stepBindModuleService.getBindInfoByCondition(stepBindModule);
                        if (ToolUtils.judgeList(stepBindModuleList)) {
                            //删除步骤与模块的绑定关系
                            stepBindModuleService.batchDeleteBindRelation(stepBindModuleList);
                        }
                        return true;
                    }
                }
            }
            return stepModuleService.deleteStepModule(stepModule);
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStep(TestCaseTree testCaseTree) {
        TestCaseStep caseStep = ToolUtils.autoCopyProperties(testCaseTree, TestCaseStep.class);
        Integer stepId = testCaseTree.getId();
        String name = testCaseTree.getName();
        Integer lastStepMarkId = testCaseTree.getLastStepMarkId();
        caseStep.setStepId(stepId);
        caseStep.setModuleId(testCaseTree.getSystemModuleId());
        caseStep.setStepName(name);
        caseStep.setLastStepMarkId(lastStepMarkId);
        //更新步骤
        boolean b = testCaseStepService.updateTestCaseStep(caseStep);
        if (b) {
            //更新属性
            StepAttr stepAttr = testCaseTree.getStepAttr();
            if (!ToolUtils.isNull(stepAttr)) {
                stepAttr.setStepId(stepId);
                StepAttr stepAttr1 = stepAttrService.getStepAttrByStepId(stepId);
                if (ToolUtils.isNull(stepAttr1)) {
                    StepAttr stepAttr2 = stepAttrService.addStepAttr(stepAttr);
                    if (ToolUtils.isNull(stepAttr2)) {
                        return false;
                    }
                } else {
                    boolean b1 = stepAttrService.updateStepAttr(stepAttr);
                    if (!b1) {
                        return false;
                    }
                }
            }

            //新增或更新绑定模块
            Integer moduleId = testCaseTree.getModuleId();
            if (!ToolUtils.isNull(moduleId)) {
                StepBindModule stepBindModule = new StepBindModule();
                stepBindModule.setStepId(stepId);
                stepBindModule.setModuleId(moduleId);
                return stepBindModuleService.insertOrUpdateBindRelation(stepBindModule);
            }
            return true;
        }
        return false;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean batchDeleteStepWithOther(List<TestCaseStep> testCaseStepList) {
        //批量删除步骤
        boolean b = testCaseStepService.batchDeleteStepList(testCaseStepList);
        if (b) {
            List<StepAttr> stepAttrList = new ArrayList<>(16);
            List<StepBindModule> stepBindModuleList = new ArrayList<>(16);
            for (TestCaseStep testCaseStep : testCaseStepList) {
                Integer stepId = testCaseStep.getStepId();
                StepAttr stepAttr = new StepAttr();
                stepAttr.setStepId(stepId);
                stepAttrList.add(stepAttr);

                StepBindModule stepBindModule = new StepBindModule();
                stepBindModule.setStepId(stepId);
                stepBindModuleList.add(stepBindModule);
            }
            //批量删除属性
            stepAttrService.batchDeleteStepAttr(stepAttrList);
            //批量删除步骤绑定关系
            stepBindModuleService.batchDeleteBindRelation(stepBindModuleList);
            return true;
        }
        return false;
    }

    private String getApplicableStage(String applicableStage, Integer stepId) {
        if (!ToolUtils.isNull(applicableStage) || stepId == 0) {
            return applicableStage;
        }
        TestCaseStep testCaseStep = testCaseStepService.getTestCaseStepById(stepId);
        Integer parentId = testCaseStep.getStepParentId();
        StepAttr stepAttr = stepAttrService.getStepAttrByStepId(parentId);
        if (!ToolUtils.isNull(stepAttr)) {
            applicableStage = stepAttr.getApplicableStage();
            if (!ToolUtils.isNull(applicableStage)) {
                return applicableStage;
            }
        }
        return getApplicableStage(applicableStage, parentId);
    }

    private String getExpected(String expected, Integer stepId) {
        if (!ToolUtils.isNull(expected) || stepId == 0) {
            return expected;
        }
        TestCaseStep testCaseStep = testCaseStepService.getTestCaseStepById(stepId);
        Integer parentId = testCaseStep.getStepParentId();
        StepAttr stepAttr = stepAttrService.getStepAttrByStepId(parentId);
        if (!ToolUtils.isNull(stepAttr)) {
            expected = stepAttr.getExpected();
            if (!ToolUtils.isNull(expected)) {
                return expected;
            }
        }
        return getExpected(expected, parentId);
    }

    private String getManageModule(String manageModule, Integer stepId) {
        if (!ToolUtils.isNull(manageModule) || stepId == 0) {
            return manageModule;
        }
        TestCaseStep testCaseStep = testCaseStepService.getTestCaseStepById(stepId);
        Integer parentId = testCaseStep.getStepParentId();
        StepAttr stepAttr = stepAttrService.getStepAttrByStepId(parentId);
        if (!ToolUtils.isNull(stepAttr)) {
            manageModule = stepAttr.getManageModule();
            if (!ToolUtils.isNull(manageModule)) {
                return manageModule;
            }
        }
        return getManageModule(manageModule, parentId);
    }

    private String getPrecondition(String precondition, Integer stepId) {
        if (!ToolUtils.isNull(precondition) || stepId == 0) {
            return precondition;
        }
        TestCaseStep testCaseStep = testCaseStepService.getTestCaseStepById(stepId);
        Integer parentId = testCaseStep.getStepParentId();
        StepAttr stepAttr = stepAttrService.getStepAttrByStepId(parentId);
        if (!ToolUtils.isNull(stepAttr)) {
            precondition = stepAttr.getPrecondition();
            if (!ToolUtils.isNull(precondition)) {
                return precondition;
            }
        }
        return getPrecondition(precondition, parentId);
    }

    private String getPriority(String priority, Integer stepId) {
        if (!ToolUtils.isNull(priority) || stepId == 0) {
            return priority;
        }
        TestCaseStep testCaseStep = testCaseStepService.getTestCaseStepById(stepId);
        Integer parentId = testCaseStep.getStepParentId();
        StepAttr stepAttr = stepAttrService.getStepAttrByStepId(parentId);
        if (!ToolUtils.isNull(stepAttr)) {
            priority = stepAttr.getPriority();
            if (!ToolUtils.isNull(priority)) {
                return priority;
            }
        }
        return getPrecondition(priority, parentId);
    }

    private String getTestCaseTitle(String testCaseTitle, Integer stepId) {
        if (!ToolUtils.isNull(testCaseTitle) || stepId == 0) {
            return testCaseTitle;
        }
        TestCaseStep testCaseStep = testCaseStepService.getTestCaseStepById(stepId);
        Integer parentId = testCaseStep.getStepParentId();
        StepAttr stepAttr = stepAttrService.getStepAttrByStepId(parentId);
        if (!ToolUtils.isNull(stepAttr)) {
            testCaseTitle = stepAttr.getTestCaseTitle();
            if (!ToolUtils.isNull(testCaseTitle)) {
                return testCaseTitle;
            }
        }
        return getTestCaseTitle(testCaseTitle, parentId);
    }

    private String getTestCaseType(String testCaseType, Integer stepId) {
        if (!ToolUtils.isNull(testCaseType) || stepId == 0) {
            return testCaseType;
        }
        TestCaseStep testCaseStep = testCaseStepService.getTestCaseStepById(stepId);
        Integer parentId = testCaseStep.getStepParentId();
        StepAttr stepAttr = stepAttrService.getStepAttrByStepId(parentId);
        if (!ToolUtils.isNull(stepAttr)) {
            testCaseType = stepAttr.getTestCaseType();
            if (!ToolUtils.isNull(testCaseType)) {
                return testCaseType;
            }
        }
        return getTestCaseType(testCaseType, parentId);
    }

    @Override
    public List<TestCaseTree> getTestCaseTreeBySystemId(TestCaseStep testCaseStep) {
        Integer systemId = testCaseStep.getSystemId();
        Integer moduleId = testCaseStep.getModuleId();
        return testCaseStepService.getTestCaseTreeBySystemId(systemId, moduleId);
//        List<TestCaseTree> testCaseTrees = testCaseStepService.getTestCaseTreeBySystemId(systemId);
//        for (TestCaseTree testCaseTree : testCaseTrees) {
//            Integer stepId = testCaseTree.getId();
//            String applicableStage = testCaseTree.getApplicableStage();
//            String testCaseType = testCaseTree.getTestCaseType();
//            String expected = testCaseTree.getExpected();
//            String manageModule = testCaseTree.getManageModule();
//            String precondition = testCaseTree.getPrecondition();
//            String priority = testCaseTree.getPriority();
//            String testCaseTitle = testCaseTree.getTestCaseTitle();
//            testCaseTree.setApplicableStage(getApplicableStage(applicableStage, stepId));
//            testCaseTree.setExpected(getExpected(expected, stepId));
//            testCaseTree.setManageModule(getManageModule(manageModule, stepId));
//            testCaseTree.setPrecondition(getPrecondition(precondition, stepId));
//            testCaseTree.setTestCaseTitle(getTestCaseTitle(testCaseTitle, stepId));
//            testCaseTree.setTestCaseType(getTestCaseType(testCaseType, stepId));
//        }
//        return testCaseTrees;
    }

    @Override
    public TestCaseTree getStepAttrById(Integer stepId) {
        StepAttr stepAttr = stepAttrService.getStepAttrByStepId(stepId);
        TestCaseStep testCaseStep = testCaseStepService.getTestCaseStepById(stepId);
        TestCaseTree testCaseTree = new TestCaseTree();
        testCaseTree.setLastStepMarkId(testCaseStep.getLastStepMarkId());
        testCaseTree.setId(stepId);
        if (ToolUtils.isNull(stepAttr)) {
            testCaseTree.setApplicableStage(getApplicableStage(null, stepId));
            testCaseTree.setExpected(getExpected(null, stepId));
            testCaseTree.setManageModule(getManageModule(null, stepId));
            testCaseTree.setPrecondition(getPrecondition(null, stepId));
            testCaseTree.setPriority(getPriority(null, stepId));
            testCaseTree.setTestCaseTitle(getTestCaseTitle(null, stepId));
            testCaseTree.setTestCaseType(getTestCaseType(null, stepId));
        } else {
            String applicableStage = stepAttr.getApplicableStage();
            String testCaseType = stepAttr.getTestCaseType();
            String expected = stepAttr.getExpected();
            String manageModule = stepAttr.getManageModule();
            String precondition = stepAttr.getPrecondition();
            String priority = stepAttr.getPriority();
            String testCaseTitle = stepAttr.getTestCaseTitle();
            testCaseTree.setApplicableStage(getApplicableStage(applicableStage, stepId));
            testCaseTree.setExpected(getExpected(expected, stepId));
            testCaseTree.setManageModule(getManageModule(manageModule, stepId));
            testCaseTree.setPrecondition(getPrecondition(precondition, stepId));
            testCaseTree.setPriority(getPriority(priority, stepId));
            testCaseTree.setTestCaseTitle(getTestCaseTitle(testCaseTitle, stepId));
            testCaseTree.setTestCaseType(getTestCaseType(testCaseType, stepId));
        }
        StepBindModule stepBindModule = new StepBindModule();
        stepBindModule.setStepId(stepId);
        //查询步骤绑定的模块
        List<StepBindModule> stepBindModuleList = stepBindModuleService.getBindInfoByCondition(stepBindModule);
        if (ToolUtils.judgeList(stepBindModuleList)) {
            Integer moduleId = stepBindModuleList.get(0).getModuleId();
            testCaseTree.setModuleId(moduleId);
        }
        return testCaseTree;
    }

    @Override
    public List<TestCaseTree> getModuleStepListById(StepModule stepModule) {
        List<StepModule> stepModuleList = stepModuleService.getStepModuleListByCondition(stepModule);
        List<TestCaseTree> testCaseTreeList = new ArrayList<>(16);
        if (ToolUtils.judgeList(stepModuleList)) {
            StepModule stepModule1 = stepModuleList.get(0);
            List<TestCaseStep> testCaseStepList = testCaseStepService.getChildrenStepList(stepModule1.getFirstStepId());
            for (TestCaseStep testCaseStep : testCaseStepList) {
                TestCaseTree testCaseTree = new TestCaseTree();
                testCaseTree.setId(testCaseStep.getStepId());
                testCaseTree.setName(testCaseStep.getStepName());
                testCaseTree.setParentId(testCaseStep.getStepParentId());
                testCaseTreeList.add(testCaseTree);
            }
        }
        return testCaseTreeList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public StepModule updateModuleStep(StepModuleBo stepModuleBo) {
        Integer moduleId = stepModuleBo.getModuleId();
        StepModule stepModule = new StepModule();
        stepModule.setModuleId(moduleId);
        //删除模块
        boolean b = stepModuleService.deleteStepModule(stepModule);
        if (b) {
            //新增模块
            StepModule stepModule1 = addStepModule(stepModuleBo);
            StepBindModule stepBindModule = new StepBindModule();
            stepBindModule.setModuleId(moduleId);
            List<StepBindModule> bindInfoByCondition = stepBindModuleService.getBindInfoByCondition(stepBindModule);
            Integer moduleId1 = stepModule1.getModuleId();
            List<Integer> stepIdList = new ArrayList<>(16);
            for (StepBindModule stepBindModule1 : bindInfoByCondition) {
                stepIdList.add(stepBindModule1.getStepId());
            }
            //存在绑定关系
            if(ToolUtils.judgeList(stepIdList)){
                boolean b1 = stepBindModuleService.batchUpdateBindRelation(stepIdList, moduleId1);
                if (b1) {
                    return stepModule1;
                }else {
                    return new StepModule();
                }
            }
            return stepModule1;
        }
        return new StepModule();
    }

    private void stepAttrTransferToVo(TestCaseVo testCaseVo, StepAttr stepAttr) {
        String applicableStage = stepAttr.getApplicableStage();
        String expected = stepAttr.getExpected();
        String manageModule = stepAttr.getManageModule();
        String precondition = stepAttr.getPrecondition();
        String testCaseTitle = stepAttr.getTestCaseTitle();
        String testCaseType = stepAttr.getTestCaseType();
        String priority = stepAttr.getPriority();
        if (!ToolUtils.isNull(applicableStage)) {
            testCaseVo.setApplicableStage(applicableStage);
        }
        if (!ToolUtils.isNull(expected)) {
            testCaseVo.setExpected(expected);
        }
        if (!ToolUtils.isNull(manageModule)) {
            testCaseVo.setManageModule(manageModule);
        }
        if (!ToolUtils.isNull(precondition)) {
            testCaseVo.setPrecondition(precondition);
        }
        if (!ToolUtils.isNull(testCaseTitle)) {
            testCaseVo.setTestCaseTitle(testCaseTitle);
        }
        if (!ToolUtils.isNull(testCaseType)) {
            testCaseVo.setTestCaseType(testCaseType);
        }
        if (!ToolUtils.isNull(priority)) {
            testCaseVo.setPriority(priority);
        }
    }
}
