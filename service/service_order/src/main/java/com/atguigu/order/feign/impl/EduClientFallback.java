package com.atguigu.order.feign.impl;


import com.atguigu.order.entity.vo.CourseInfoVo;
import com.atguigu.order.feign.EduClient;
import org.springframework.stereotype.Component;

@Component
public class EduClientFallback  implements EduClient {


    @Override
    public CourseInfoVo getCourseInfoOrder(String id) {
        return null;
    }
}
