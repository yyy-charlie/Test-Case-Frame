package com.testcase.frame.mapper;

import com.testcase.frame.pojo.StepAttr;
import com.testcase.frame.pojo.TestCaseStep;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface StepAttrMapper extends Mapper<StepAttr> {
    /**
     * 新增步骤属性
     *
     * @param stepAttr
     * @return
     */
    int insertStepAttr(@Param("stepAttr") StepAttr stepAttr);

    /**
     * 批量删除步骤属性
     *
     * @param stepAttrList
     * @return
     */
    Integer batchDeleteStepAttr(@Param("list") List<StepAttr> stepAttrList);
}