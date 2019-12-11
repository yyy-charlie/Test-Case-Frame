package com.testcase.frame.common.exception.base;


import com.testcase.frame.common.constant.RestCodeConstants;
import com.testcase.frame.common.constant.ResultCodeEnum;
import com.testcase.frame.common.exception.BaseException;

/**
 * Dao基础异常类
 */
public class DAOException extends BaseException {

    public DAOException(String message) {
        super(message, RestCodeConstants.EX_BUSINESS_BASE_CODE);
    }

    public DAOException(int status,String message) {
        super(message, status);
    }

    public DAOException(ResultCodeEnum codeEnum) {
        super(codeEnum.getMessage(), codeEnum.getHttpStatus().value());
    }
}
