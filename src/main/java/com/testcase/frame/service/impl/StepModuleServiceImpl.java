package com.testcase.frame.service.impl;

import com.testcase.frame.mapper.StepModuleMapper;
import com.testcase.frame.pojo.StepModule;
import com.testcase.frame.service.intf.IStepModuleCrudService;
import com.testcase.frame.service.intf.IStepModuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName StepModuleServiceImpl
 * @Description TODO
 * @Author ycn
 * @Date 2019-09-26
 **/
@Service
public class StepModuleServiceImpl implements IStepModuleService {

    private IStepModuleCrudService stepModuleCrudService;

    @Autowired
    public StepModuleServiceImpl(IStepModuleCrudService stepModuleCrudService) {
        this.stepModuleCrudService = stepModuleCrudService;
    }

    @Override
    public StepModule addStepModule(StepModule stepModule) {
        return stepModuleCrudService.addStepModule(stepModule);
    }

    @Override
    public List<StepModule> getStepModuleListByCondition(StepModule stepModule) {
        return stepModuleCrudService.getStepModuleListByCondition(stepModule);
    }

    @Override
    public boolean updateModule(StepModule stepModule) {
        return stepModuleCrudService.updateModule(stepModule);
    }

    @Override
    public boolean deleteStepModule(StepModule stepModule) {
        return stepModuleCrudService.deleteStepModule(stepModule);
    }
}
