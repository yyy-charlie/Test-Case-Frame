package com.testcase.frame.bean;

import com.testcase.frame.common.Util;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName LetterObj
 * @Description 字母对象
 * @Author ycn
 * @Date 2019-10-14
 **/
public class LetterObj extends StepObj {

    /**
     * 随机获取最小长度的字母
     *
     * @param minLength
     * @return
     */
    @Override
    public StringBuffer getMinContent(int minLength) {
        return Util.getRandomStr(minLength).append("（等于").append(minLength).append("个字母）");
    }

    /**
     * 随机获取最大长度的字母
     *
     * @param maxLength
     * @return
     */
    @Override
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
    @Override
    public StringBuffer getMiddleContent(int minLength, int maxLength) {
        return Util.getRandomStrBetween(minLength + 1, maxLength - 1).append("（大于").append(minLength).append("个小于").append(maxLength).append("个字母）");
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
        return Util.getRandomStr(minSubOneLength).append("（等于").append(minSubOneLength).append("个字母）");
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
        return Util.getRandomStr(maxAddOneLength).append("（等于").append(maxAddOneLength).append("个字母）");
    }

}
