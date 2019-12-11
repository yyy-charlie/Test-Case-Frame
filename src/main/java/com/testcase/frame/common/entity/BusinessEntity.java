package com.testcase.frame.common.entity;

import lombok.Data;

import java.util.Date;

@Data
public class BusinessEntity extends BaseEntity {

    private String crtUserId;
    private Date crtTime;

    private String updUserId;
    private Date updTime;
}
