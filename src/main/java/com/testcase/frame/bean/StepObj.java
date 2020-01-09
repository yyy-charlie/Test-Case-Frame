package com.testcase.frame.bean;

import com.testcase.frame.common.Util;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName StepObj
 * @Description TODO
 * @Author ycn
 * @Date 2019-10-14
 **/
public class StepObj {
    public List<StringBuffer> getGenerateContent(int minLength, int maxLength) {
        List<StringBuffer> stringBufferList = new ArrayList<>(5);
        if (minLength > 1) {
            //小于最小值
            StringBuffer minSubOneContent = getMinSubOneContent(minLength);
            stringBufferList.add(minSubOneContent);
        }

        //最小值
        StringBuffer minContent = getMinContent(minLength);
        stringBufferList.add(minContent);

        //如果最大值不等于最小值，则获取最大值的内容
        if (maxLength - minLength != 0) {
            //最大值
            StringBuffer maxContent = getMaxContent(maxLength);
            stringBufferList.add(maxContent);
        }

        //大于最大值
        StringBuffer maxAddOneContent = getMaxAddOneContent(maxLength);
        stringBufferList.add(maxAddOneContent);

        if (maxLength - minLength > 1) {
            //中间值
            StringBuffer middleContent = getMiddleContent(minLength, maxLength);
            stringBufferList.add(middleContent);
        }

        return stringBufferList;
    }

    /**
     * 随机获取最小长度的字母
     *
     * @param minLength
     * @return
     */
    public StringBuffer getMinContent(int minLength) {
        return Util.getRandomStr(minLength).append("（等于").append(minLength).append("个字母）");
    }

    /**
     * 随机获取最大长度的字母
     *
     * @param maxLength
     * @return
     */
    public StringBuffer getMaxContent(int maxLength) {
        return Util.getRandomStr(maxLength).append("（等于").append(maxLength).append("个字母）");
    }

    /**
     * 随机获取中间长度的字母
     *
     * @param minLength
     * @param maxLength
     * @return
     */
    public StringBuffer getMiddleContent(int minLength, int maxLength) {
        return Util.getRandomStrBetween(minLength + 1, maxLength - 1).append("（大于").append(minLength).append("个小于").append(maxLength).append("个字母）");
    }

    /**
     * 随机获取最小值-1长度的字母
     *
     * @param minLength
     * @return
     */
    public StringBuffer getMinSubOneContent(int minLength) {
        int minSubOneLength = minLength - 1;
        return Util.getRandomStr(minSubOneLength).append("（等于").append(minSubOneLength).append("个字母）");
    }

    /**
     * 随机获取最大值+1长度的字母
     *
     * @param maxLength
     * @return
     */
    public StringBuffer getMaxAddOneContent(int maxLength) {
        int maxAddOneLength = maxLength + 1;
        return Util.getRandomStr(maxAddOneLength).append("（等于").append(maxAddOneLength).append("个字母）");
    }
}
