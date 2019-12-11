package com.testcase.frame.common.exception.base;


import com.testcase.frame.common.constant.RestCodeConstants;
import com.testcase.frame.common.constant.ResultCodeEnum;
import com.testcase.frame.common.exception.BaseException;

public class BusinessException extends BaseException {

    public BusinessException(String message) {
        super(message, RestCodeConstants.EX_BUSINESS_BASE_CODE);
    }

    public BusinessException(int status,String message) {
        super(message, status);
    }

    public BusinessException(ResultCodeEnum codeEnum) {
        super(codeEnum.getMessage(), codeEnum.getHttpStatus().value());
    }
}
