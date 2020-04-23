package com.testcase.frame.bean;

import com.testcase.frame.common.Util;

/**
 * @ClassName SymbolObj
 * @Description TODO
 * @Author ycn
 * @Date 2019-10-18
 **/
public class SymbolObj implements StepStrategy {

    @Override
    public String getType() {
        return "symbol";
    }

    /**
     * 随机获取最小长度的特殊符号
     *
     * @param minLength
     * @return
     */
    @Override
    public StringBuffer getMinContent(int minLength) {
        return Util.getRandomChars(minLength).append("（等于").append(minLength).append("个特殊符号）");
    }

    /**
     * 随机获取最大长度的特殊符号
     *
     * @param maxLength
     * @return
     */
    @Override
    public StringBuffer getMaxContent(int maxLength) {
        return Util.getRandomChars(maxLength).append("（等于").append(maxLength).append("个特殊符号）");
    }

    /**
     * 随机获取中间长度的特殊符号
     *
     * @param minLength
     * @param maxLength
     * @return
     */
    @Override
    public StringBuffer getMiddleContent(int minLength, int maxLength) {
        return Util.getRandomCharsBetween(minLength + 1).append("（大于").append(minLength).append("个小于").append(maxLength).append("个特殊符号）");
    }

    /**
     * 随机获取最小值-1长度的字母
     *
     * @param minLength
     * @return
     */
    @Override
    public StringBuffer getMinSubOneContent(int minLength) {
        int minSubOneLength = minLength - 1;
        return Util.getRandomChars(minSubOneLength).append("（等于").append(minSubOneLength).append("个特殊符号）");
    }

    /**
     * 随机获取最大值+1长度的字母
     *
     * @param maxLength
     * @return
     */
    @Override
    public StringBuffer getMaxAddOneContent(int maxLength) {
        int maxAddOneLength = maxLength + 1;
        return Util.getRandomChars(maxAddOneLength).append("（等于").append(maxAddOneLength).append("个特殊符号）");
    }
}
