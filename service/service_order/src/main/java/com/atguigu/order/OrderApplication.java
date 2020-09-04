package com.atguigu.order;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.atguigu"})
public class OrderApplication {

    public static void main(String[] args) {

        SpringApplication.run(OrderApplication.class,args);
    }
}
