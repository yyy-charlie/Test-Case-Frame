
package com.testcase.frame.common.entity;

import lombok.Data;

import javax.persistence.Id;
import java.io.Serializable;

/**
 * 基础entity
 */
@Data
public class BaseEntity implements Serializable{
    @Id
    private Object id;
    private Boolean deleteFlag;
}
