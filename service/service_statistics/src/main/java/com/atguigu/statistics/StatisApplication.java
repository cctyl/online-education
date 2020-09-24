package com.atguigu.statistics;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan("com.atguigu")
@EnableFeignClients
@MapperScan("com.atguigu.statistics.mapper")
@EnableScheduling
public class StatisApplication {

    public static void main(String[] args) {
        SpringApplication.run(StatisApplication.class,args);
    }
}
