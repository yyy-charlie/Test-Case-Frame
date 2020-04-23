package com.testcase.frame.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.testcase.frame.common.exception.base.BusinessException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;

/**
 * @ClassName ToolUtils
 * @Description TODO
 * @Author ycn
 * @Date 2019-09-20
 **/
public class ToolUtils {
    private static final Logger logger = LogManager.getLogger();

    /*
     * 判断对象是否为空
     * */
    public static boolean isNull(Object obj) {
        return null == obj;
    }

    public static boolean isNull(Object... objs) {

        if (null == objs) {
            return true;
        }

        for (Object obj : objs) {
            if ((null == obj) || ("".equals(obj))) {
                return true;
            }
        }
        return false;
    }

    public static boolean judgeList(List list) {
        boolean flag = !isNull((Object) list) && list.size() >= 1;
        if (flag && list.size() == 1) {
            return !isNull(list.get(0));
        } else {
            return flag;
        }
    }

    /**
     * 判断字符串是否为空  或者空字符串
     *
     * @param obj
     * @return
     */
    public static boolean isEmpty(String obj) {

        return (obj == null) || (obj.trim().length() == 0);
    }
    public static void closeIO(Closeable... closeables) {

        try {

            if (closeables != null) {

                for (Closeable close : closeables) {

                    if (close != null) {
                        close.close();
                    }
                }
            }
        } catch (IOException e) {
            logger.error("关流异常" + e);
            throw new BusinessException("关流异常");
        }
    }

    public static boolean isStr2Double(String str) {

        if ((null == str) || ("".equals(str))) {
            return false;
        }

        try {
            Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return false;
        }
        return true;
    }


    public static <T, K> K copyProperties(T source, K target) {
        BeanUtils.copyProperties(source, target);
        return target;
    }

    public static <T, K> K autoCopyProperties(T source, Class<K> target) {

        return JSONObject.parseObject(JSON.toJSONString(source), target);
    }
}
