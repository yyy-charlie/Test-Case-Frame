package com.testcase.frame.common.bean;

import lombok.Data;

import java.io.Serializable;

@Data
public class Limit implements Serializable {

    private static final long serialVersionUID = 6658088256539114784L;

    private int begin;

    private int end;

    public Limit() {
    }

    public Limit(int begin, int end) {
        this.begin = begin;
        this.end = end;
    }
}
