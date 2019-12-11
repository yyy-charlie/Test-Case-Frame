package com.testcase.frame.common.bean;


import lombok.Data;

@Data
public class Attr {

    private String id;

    private String name;

    private String value;

    private String unit;

    private String formatter;

    private String sortable;

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public Attr() {
    }

    public Attr(String id, String value) {
        this.id = id;
        this.value = value;
    }

    public Attr(String id, String name, String value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }

    public Attr(String id, String name, String value, String formatter) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.formatter = formatter;
    }

    public Attr(String id, String name, String value, String formatter, String sortable) {
        this.id = id;
        this.name = name;
        this.value = value;
        this.formatter = formatter;
        this.sortable = sortable;
    }
}
