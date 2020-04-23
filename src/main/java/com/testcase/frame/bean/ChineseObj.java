package com.testcase.frame.bean;

import com.testcase.frame.common.Util;

/**
 * @ClassName ChineseObj
 * @Description TODO
 * @Author ycn
 * @Date 2019-10-14
 **/
public class ChineseObj implements StepStrategy {

    @Override
    public String getType() {
        return "chinese";
    }

    /**
     * 随机获取最小长度的汉字
     *
     * @param minLength
     * @return
     */
    @Override
    public StringBuffer getMinContent(int minLength) {
        return Util.getRandomChinese(minLength).append("（等于").append(minLength).append("个汉字）");
    }

    /**
     * 随机获取最大长度的汉字
     *
     * @param maxLength
     * @return
     */
    @Override
    public StringBuffer getMaxContent(int maxLength) {
        return Util.getRandomChinese(maxLength).append("（等于").append(maxLength).append("个汉字）");
    }

    /**
     * 随机获取中间长度的汉字
     *
     * @param minLength
     * @param maxLength
     * @return
     */
    @Override
    public StringBuffer getMiddleContent(int minLength, int maxLength) {
        return Util.getRandomChineseBetween(minLength + 1).append("（大于").append(minLength).append("个小于").append(maxLength).append("个汉字）");
    }

    /**
     * 随机获取最小值-1长度的汉字
     *
     * @param minLength
     * @return
     */
    @Override
    public StringBuffer getMinSubOneContent(int minLength) {
        int minSubOneLength = minLength - 1;
        return Util.getRandomChinese(minSubOneLength).append("（等于").append(minSubOneLength).append("个汉字）");
    }

    /**
     * 随机获取最大值+1长度的汉字
     *
     * @param maxLength
     * @return
     */
    @Override
    public StringBuffer getMaxAddOneContent(int maxLength) {
        int maxAddOneLength = maxLength + 1;
        return Util.getRandomChinese(maxAddOneLength).append("（等于").append(maxAddOneLength).append("个汉字）");
    }
}
