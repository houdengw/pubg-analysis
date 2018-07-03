package com.evils.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Title: evils
 * CreateDate:  2018/7/3
 *
 * @author houdengw
 * @version 1.0
 */
@Controller
@RequestMapping(value="/home")
public class HomeController {

    @RequestMapping(value="/test",method = RequestMethod.GET)
    public String test(){
        return "test";
    }
}
