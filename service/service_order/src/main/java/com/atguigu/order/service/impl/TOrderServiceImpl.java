package com.atguigu.order.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.order.entity.TOrder;
import com.atguigu.order.entity.vo.CourseInfoVo;
import com.atguigu.order.entity.vo.UcenterMember;
import com.atguigu.order.feign.EduClient;
import com.atguigu.order.feign.UcenterClient;
import com.atguigu.order.mapper.TOrderMapper;
import com.atguigu.order.service.TOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author tyl
 * @since 2020-09-04
 */
@Service
public class TOrderServiceImpl extends ServiceImpl<TOrderMapper, TOrder> implements TOrderService {

    @Autowired
    EduClient eduClient;

    @Autowired
    UcenterClient ucenterClient;

    /**
     * 生成一个课程id
     * @param courseId
     * @param userId
     * @return
     */
    @Override
    public String createOrder(String courseId, String userId) {

        //根据课程id查询课程信息
        CourseInfoVo courseInfoOrder = eduClient.getCourseInfoOrder(courseId);


        //根据用户id查询用户信息

        UcenterMember userInfoById = ucenterClient.getUserInfoById(userId);



    }
}
