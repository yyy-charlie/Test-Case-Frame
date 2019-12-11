package com.testcase.frame.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * @ClassName JumpToHtmlController
 * @Description TODO
 * @Author ycn
 * @Date 2019-09-29
 **/
@Controller
public class JumpToHtmlController {

    @RequestMapping("/")
    public String helloHtml() {
        return "/index";
    }

    @RequestMapping("/meterManage")
    public String getMeterManage() {
        return "/meterManage";
    }
}
