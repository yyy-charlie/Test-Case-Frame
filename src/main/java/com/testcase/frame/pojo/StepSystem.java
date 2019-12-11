package com.testcase.frame.pojo;

import javax.persistence.*;

@Table(name = "step_system")
public class StepSystem {
    @Id
    @Column(name = "system_id")
    private Integer systemId;

    @Column(name = "system_name")
    private String systemName;

    /**
     * @return system_id
     */
    public Integer getSystemId() {
        return systemId;
    }

    /**
     * @param systemId
     */
    public void setSystemId(Integer systemId) {
        this.systemId = systemId;
    }

    /**
     * @return system_name
     */
    public String getSystemName() {
        return systemName;
    }

    /**
     * @param systemName
     */
    public void setSystemName(String systemName) {
        this.systemName = systemName;
    }
}