package com.testcase.frame.common.bean;

import lombok.Data;

@Data
public class State {

    private int code;//状态码

    private String description;//描述

    public State() {
    }

    public State(int code, String description) {
        this.code = code;
        this.description = description;
    }
}
