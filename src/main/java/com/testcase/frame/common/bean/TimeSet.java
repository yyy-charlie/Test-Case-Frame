package com.testcase.frame.common.bean;


import lombok.Data;

@Data
public class TimeSet {

    private String startTime; //开始时间

    private String endTime; //结束时间

    private String mark;//时间段标识

    public TimeSet() {
    }

    public TimeSet(String startTime, String endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public TimeSet(String startTime, String endTime, String mark) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.mark = mark;
    }
}
