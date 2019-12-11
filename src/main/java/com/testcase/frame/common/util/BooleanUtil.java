package com.testcase.frame.common.util;

public class BooleanUtil {
    /**
     * boolean是
     */
    public static final String BOOLEAN_TRUE = "1";
    /**
     * boolean否
     */
    public static final String BOOLEAN_FALSE = "0";

    public static String switchValue(Boolean value){
        if(value == null) {
            return BOOLEAN_FALSE;
        }
        return value? BOOLEAN_TRUE: BOOLEAN_FALSE;
    }
}
