package com.testcase.frame.bean;

import java.util.List;

/**
 * @ClassName StepStrategy
 * @Description TODO
 * @Author ycn
 * @Date 2020-04-23
 **/
public interface StepStrategy {
    /**
     * 获取类型
     *
     * @return
     */
    String getType();

    /**
     * 随机获取最小长度的字母
     *
     * @param minLength
     * @return
     */
    StringBuffer getMinContent(int minLength);

    /**
     * 随机获取最大长度的字母
     *
     * @param maxLength
     * @return
     */
    StringBuffer getMaxContent(int maxLength);

    /**
     * 随机获取中间长度的字母
     *
     * @param minLength
     * @param maxLength
     * @return
     */
    StringBuffer getMiddleContent(int minLength, int maxLength);

    /**
     * 随机获取最小值-1长度的字母
     *
     * @param minLength
     * @return
     */
    StringBuffer getMinSubOneContent(int minLength);

    /**
     * 随机获取最大值+1长度的字母
     *
     * @param maxLength
     * @return
     */
    StringBuffer getMaxAddOneContent(int maxLength);
}
