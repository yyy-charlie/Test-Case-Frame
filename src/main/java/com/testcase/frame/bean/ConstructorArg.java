package com.testcase.frame.bean;

import java.lang.reflect.Field;

/**
 * @ClassName ConstructorArg
 * @Description TODO
 * @Author ycn
 * @Date 2020-03-07
 **/
public class ConstructorArg {

    private boolean isRef;
    private Class type;
    private Object arg;

    private ConstructorArg(Builder builder) {
        this.isRef = builder.isRef;
        this.type = builder.type;
        this.arg = builder.arg;
    }

    public class Builder {
        private boolean isRef;
        private Class type;
        private Object arg;

        public ConstructorArg build() {
            if (isRef && type != null) {
                throw new IllegalArgumentException();
            }
            if (!isRef && (type == null || arg == null)) {
                throw new IllegalArgumentException();
            }
            if (this.isRef &&(arg.getClass() != String.class && type != null)){
                throw new IllegalArgumentException();
            }
            return new ConstructorArg(this);
        }
    }


}
