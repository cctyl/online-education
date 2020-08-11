package com.atguigu.cmsservice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

@SpringBootApplication
@ComponentScan({"com.atguigu"}) //指定扫描位置
public class CmsApplication {


    public static void main(String[] args) {
        SpringApplication.run(CmsApplication.class,args);
    }
}
