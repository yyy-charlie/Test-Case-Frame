package com.testcase.frame.common.bean;

import com.testcase.frame.common.constant.Constants;
import com.testcase.frame.common.util.ToolUtils;
import lombok.Data;

import java.util.LinkedHashMap;
import java.util.Map;


@Data
public class Query extends LinkedHashMap<String, Object> {

	private static final long serialVersionUID = 1L;

    private int pageIndex = 1; // 当前页码

    private int pageLine = 10; // 每页条数

    public Query(){

    }

    public Query(String [] keys ,Object ...value){

        for (int i = 0 ; i < keys.length ;i ++ ){

            if(!ToolUtils.isNull(value[i])){

                this.put(keys[i],value[i]);
            }
        }
    }

    public Query(String  key,Object value){

        if(!ToolUtils.isNull(value)){

            this.put(key,value);
        }
    }

    public Query(Map<String, Object> params){

        this.putAll(params);

        Object page = params.get(Constants.PAGE_INDEX) ;

        Object line = params.get(Constants.PAGE_LINE);

        this.pageIndex = page == null ? 1: Integer.parseInt(page.toString());

        this.pageLine  = line == null ? 10 :Integer.parseInt(line.toString());

        this.remove(Constants.PAGE_INDEX);
        this.remove(Constants.PAGE_LINE);
    }
}
