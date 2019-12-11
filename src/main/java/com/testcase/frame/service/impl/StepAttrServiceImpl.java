package com.testcase.frame.service.impl;

import com.testcase.frame.pojo.StepAttr;
import com.testcase.frame.service.intf.IStepAttrCrudService;
import com.testcase.frame.service.intf.IStepAttrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName StepAttrServiceImpl
 * @Description TODO
 * @Author ycn
 * @Date 2019-09-26
 **/
@Service
public class StepAttrServiceImpl implements IStepAttrService {

    private IStepAttrCrudService stepAttrCrudService;

    @Autowired
    public StepAttrServiceImpl(IStepAttrCrudService stepAttrCrudService) {
        this.stepAttrCrudService = stepAttrCrudService;
    }

    @Override
    public StepAttr addStepAttr(StepAttr stepAttr) {
        return stepAttrCrudService.addStepAttr(stepAttr);
    }

    @Override
    public boolean updateStepAttr(StepAttr stepAttr) {
        return stepAttrCrudService.updateStepAttr(stepAttr);
    }

    @Override
    public StepAttr getStepAttrByStepId(Integer stepId) {
        StepAttr stepAttr = new StepAttr();
        stepAttr.setStepId(stepId);
        return stepAttrCrudService.getStepAttrByStepId(stepAttr);
    }

    @Override
    public void batchDeleteStepAttr(List<StepAttr> stepAttrList) {
        stepAttrCrudService.batchDeleteStepAttr(stepAttrList);
    }

    @Override
    public boolean deleteStepAttr(StepAttr stepAttr) {
        return stepAttrCrudService.deleteStepAttr(stepAttr);
    }
}
