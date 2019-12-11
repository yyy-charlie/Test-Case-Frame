package com.testcase.frame.common;

import org.springframework.http.HttpStatus;

import java.util.List;

/**
 * @ClassName ParseUtils
 * @Description TODO
 * @Author ycn
 * @Date 2019-09-20
 **/
public class ParseUtils {
    public static BaseResponse parse2Response(int code, String discription) {

        return new BaseResponse(code, discription);
    }

    public static BaseResponse parse2Response(HttpStatus httpStatus, String discription) {

        return new BaseResponse(httpStatus, discription);
    }

    public static <T> BaseResponse parse2Response(int code, String discription, T data) {

        return new BaseResult<>(code, discription, data);
    }

    public static <T> BaseResponse parse2Response(String discription, T data) {

        return new BaseResult<>(HttpStatus.OK, discription, data);
    }

    public static <T> BaseResponse parse2Response(HttpStatus httpStatus, String discription, T data) {

        return new BaseResult<>(httpStatus, discription, data);
    }

    public static <T> BaseResponse parse2Response(int code, String discription, List<T> data) {

        return new BaseResult<>(code, discription, data);
    }

    public static <T> BaseResponse parse2Response(String discription, List<T> data) {

        return new BaseResult<>(HttpStatus.OK, discription, data);
    }


    public static <T> BaseResponse parse2Response(HttpStatus httpStatus, String discription, List<T> data) {

        return new BaseResult<>(httpStatus, discription, data);
    }

}
