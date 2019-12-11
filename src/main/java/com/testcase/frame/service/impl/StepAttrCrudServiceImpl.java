package com.testcase.frame.service.impl;

import com.testcase.frame.mapper.StepAttrMapper;
import com.testcase.frame.pojo.StepAttr;
import com.testcase.frame.service.intf.IStepAttrCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @ClassName StepAttrCrudServiceImpl
 * @Description TODO
 * @Author ycn
 * @Date 2019-09-26
 **/
@Service
public class StepAttrCrudServiceImpl implements IStepAttrCrudService {

    private StepAttrMapper stepAttrMapper;

    @Autowired
    public StepAttrCrudServiceImpl(StepAttrMapper stepAttrMapper) {
        this.stepAttrMapper = stepAttrMapper;
    }

    @Override
    public StepAttr addStepAttr(StepAttr stepAttr) {
        int i = stepAttrMapper.insertStepAttr(stepAttr);
        if (i == 0) {
            return null;
        }
        stepAttr.setStepId(i);
        return stepAttr;
    }

    @Override
    public boolean updateStepAttr(StepAttr stepAttr) {
        int i = stepAttrMapper.updateByPrimaryKeySelective(stepAttr);
        return (i != 0);
    }

    @Override
    public boolean deleteStepAttr(StepAttr stepAttr) {
        int i = stepAttrMapper.deleteByPrimaryKey(stepAttr);
        return (i != 0);
    }

    @Override
    public StepAttr getStepAttrByStepId(StepAttr stepAttr) {
        return stepAttrMapper.selectByPrimaryKey(stepAttr);
    }

    @Override
    public void batchDeleteStepAttr(List<StepAttr> stepAttrList) {
        stepAttrMapper.batchDeleteStepAttr(stepAttrList);
    }
}
