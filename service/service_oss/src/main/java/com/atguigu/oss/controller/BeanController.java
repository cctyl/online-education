package com.atguigu.oss.controller;

import com.atguigu.oss.utils.ConstantProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BeanController {
    @Autowired
    ConstantProperties constantProperties;


    @GetMapping("/getPer")
    public Object getProperties(){

        return constantProperties;
    }

}
