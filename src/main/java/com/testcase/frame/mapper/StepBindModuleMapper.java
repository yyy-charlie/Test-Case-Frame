package com.testcase.frame.mapper;

import com.testcase.frame.pojo.StepBindModule;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface StepBindModuleMapper extends Mapper<StepBindModule> {
    /**
     * 批量新增步骤绑定模块
     *
     * @param stepBindModuleList
     * @return
     */
    int batchInsertBindList(@Param("list") List<StepBindModule> stepBindModuleList);

    /**
     * 批量删除步骤绑定关系
     *
     * @param stepBindModuleList
     * @return
     */
    int batchDeleteBindRelation(@Param("list") List<StepBindModule> stepBindModuleList);

    /**
     * 批量修改绑定关系
     *
     * @param stepIdList
     * @param moduleId
     * @return
     */
    int batchUpdateBindRelation(@Param("list") List<Integer> stepIdList, @Param("moduleId") int moduleId);

    /**
     * 新增或更新绑定关系
     *
     * @param stepBindModule
     * @return
     */
    boolean insertOrUpdateBindRelation(@Param("stepBindModule") StepBindModule stepBindModule);
}