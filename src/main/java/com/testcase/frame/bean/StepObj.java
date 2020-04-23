package com.testcase.frame.bean;

import com.testcase.frame.common.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName StepObj
 * @Description TODO
 * @Author ycn
 * @Date 2019-10-14
 **/
public class StepObj {
    private static final Map<String, StepStrategy> STEP_STRATEGY_MAP = new HashMap<>();

    static {
        STEP_STRATEGY_MAP.put("chinese", new ChineseObj());
        STEP_STRATEGY_MAP.put("letter", new LetterObj());
        STEP_STRATEGY_MAP.put("number", new NumberObj());
        STEP_STRATEGY_MAP.put("symbol", new SymbolObj());
    }

    public StepObj() {
    }

    public List<StringBuffer> getGenerateContent(String type, int minLength, int maxLength) {
        StepStrategy stepStrategy = STEP_STRATEGY_MAP.get(type);

        List<StringBuffer> stringBufferList = new ArrayList<>(5);
        if (minLength > 1) {
            //小于最小值
            StringBuffer minSubOneContent = stepStrategy.getMinSubOneContent(minLength);
            stringBufferList.add(minSubOneContent);
        }

        //最小值
        StringBuffer minContent = stepStrategy.getMinContent(minLength);
        stringBufferList.add(minContent);

        //如果最大值不等于最小值，则获取最大值的内容
        if (maxLength - minLength != 0) {
            //最大值
            StringBuffer maxContent = stepStrategy.getMaxContent(maxLength);
            stringBufferList.add(maxContent);
        }

        //大于最大值
        StringBuffer maxAddOneContent = stepStrategy.getMaxAddOneContent(maxLength);
        stringBufferList.add(maxAddOneContent);

        if (maxLength - minLength > 1) {
            //中间值
            StringBuffer middleContent = stepStrategy.getMiddleContent(minLength, maxLength);
            stringBufferList.add(middleContent);
        }

        return stringBufferList;
    }

}
