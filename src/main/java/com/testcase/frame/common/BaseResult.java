package com.testcase.frame.common;

import com.testcase.frame.common.util.ToolUtils;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName BaseResult
 * @Description TODO
 * @Author ycn
 * @Date 2019-09-20
 **/
@Data
public class BaseResult<T> extends BaseResponse {

    private T data;

    public BaseResult() {

    }

    public BaseResult(T data) {
        this.data = data;
    }

    public BaseResult(int code , String description) {
        super(code,description);
    }

    public BaseResult(int code ,String description,T data) {
        super(code,description);
        this.data = data;
    }

    public BaseResult(HttpStatus httpStatus , String description) {
        super(httpStatus.value(),description);
    }

    public BaseResult(HttpStatus httpStatus ,String description,T data) {
        super(httpStatus.value(),description);
        this.data = data;
    }

    public BaseResult(String description,T data) {

        if(!ToolUtils.isNull(data)){
            super.setCode(HttpStatus.OK.value());
            super.setDescription(description);
            this.data = data;
        }else{
            super.setCode(HttpStatus.NO_CONTENT.value());
            super.setDescription(description);
        }
    }
}
