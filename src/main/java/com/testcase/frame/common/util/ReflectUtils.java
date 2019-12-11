package com.testcase.frame.common.util;

import com.testcase.frame.common.constant.ResultCodeEnum;
import com.testcase.frame.common.exception.base.BusinessException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectUtils {

    private static final Logger logger = LogManager.getLogger();


    public   static int  getFiledCountByAnnotation(Class<? extends Annotation> annotationClass, Field[] fields){

        int count = 0;

        for (int i=0; i < fields.length; i++){

            if(fields[i].isAnnotationPresent(annotationClass)){//判断类属性是否有此注解

                count++;
            }
        }

        return count;
    }

    public static Object getFieldValue(String fieldName, Object object) {

        try {

            Field field = object.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            Object invoke = field.get(object);

            return invoke == null ? "" : invoke;

        } catch (Exception e) {

            return "";
        }
    }

    public static<T> void  setFieldValue(T t,String fieldName,Object value){

        try {

            Field f = t.getClass().getDeclaredField(fieldName);
            f.setAccessible(true);
            f.set(t, value);

        } catch (NoSuchFieldException e ) {

            logger.error("无此属性异常"+ t + fieldName + value + e);
            throw new BusinessException(ResultCodeEnum.NO_SUCH_FIELD);
        } catch (IllegalAccessException e) {

            logger.error("反射赋值异常"+ t + fieldName + value + e);
            throw new BusinessException(ResultCodeEnum.NO_SUCH_FIELD);
        }
    }


    public static void setFieldValue(Object obj,String filedName ,Object value ,Class<?> type) {

        Class<?> c = obj.getClass();

        try {
            String methodName  = "set" + filedName.substring(0,1).toUpperCase() + filedName.substring(1);
            Method med = c.getMethod(methodName, type);

            med.invoke(obj, value);

        } catch (Exception e) {
            logger.error("反射赋值异常" + obj + filedName + value + e);
            throw new BusinessException(ResultCodeEnum.NO_SUCH_FIELD);
        }
    }
}
