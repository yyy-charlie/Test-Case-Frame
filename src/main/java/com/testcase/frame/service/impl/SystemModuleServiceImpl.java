package com.testcase.frame.service.impl;

import com.testcase.frame.common.util.ToolUtils;
import com.testcase.frame.mapper.StepSystemMapper;
import com.testcase.frame.mapper.StepSystemModuleMapper;
import com.testcase.frame.pojo.StepSystem;
import com.testcase.frame.pojo.StepSystemModule;
import com.testcase.frame.service.intf.ISystemModuleService;
import com.testcase.frame.vo.SystemModule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName SystemModuleServiceImpl
 * @Description TODO
 * @Author ycn
 * @Date 2019-10-24
 **/
@Service
public class SystemModuleServiceImpl implements ISystemModuleService {

    private StepSystemMapper stepSystemMapper;
    private StepSystemModuleMapper stepSystemModuleMapper;

    @Autowired
    public SystemModuleServiceImpl(StepSystemMapper stepSystemMapper, StepSystemModuleMapper stepSystemModuleMapper) {
        this.stepSystemMapper = stepSystemMapper;
        this.stepSystemModuleMapper = stepSystemModuleMapper;
    }

    @Override
    public SystemModule getAllSystemAndModule() {
        List<StepSystem> stepSystemList = stepSystemMapper.selectAll();
        if (ToolUtils.judgeList(stepSystemList)) {
            StepSystem stepSystem = stepSystemList.get(0);
            Integer systemId = stepSystem.getSystemId();
            Example example = new Example(StepSystemModule.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("systemId", systemId);
            List<StepSystemModule> stepSystemModuleList = stepSystemModuleMapper.selectByExample(example);
            SystemModule systemModule = new SystemModule();
            systemModule.setStepSystemList(stepSystemList);
            systemModule.setStepSystemModuleList(stepSystemModuleList);
            return systemModule;
        }

        return new SystemModule();
    }

    @Override
    public List<StepSystemModule> getSystemAndModuleBySystemId(StepSystem stepSystem) {
        Example example = new Example(StepSystemModule.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("systemId", stepSystem.getSystemId());
        return stepSystemModuleMapper.selectByExample(example);
    }
}
