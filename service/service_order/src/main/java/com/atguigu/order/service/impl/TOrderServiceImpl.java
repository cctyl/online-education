package com.atguigu.order.service.impl;

import com.atguigu.commonutils.R;
import com.atguigu.order.entity.TOrder;
import com.atguigu.order.entity.vo.CourseInfoVo;
import com.atguigu.order.entity.vo.UcenterMember;
import com.atguigu.order.feign.EduClient;
import com.atguigu.order.feign.UcenterClient;
import com.atguigu.order.mapper.TOrderMapper;
import com.atguigu.order.service.TOrderService;
import com.atguigu.order.utils.OrderNoUtil;
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
        CourseInfoVo courseDto = eduClient.getCourseInfoOrder(courseId);

        //根据用户id查询用户信息
        UcenterMember ucenterMember = ucenterClient.getUserInfoById(userId);


        TOrder order = new TOrder();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseDto.getTitle());
        order.setCourseCover(courseDto.getCover());
        order.setTeacherName(courseDto.getTeacherName());
        order.setTotalFee(courseDto.getPrice());
        order.setMemberId(userId);
        order.setMobile(ucenterMember.getMobile());
        order.setNickname(ucenterMember.getNickname());
        order.setStatus(0); //支付状态 0 未支付  1 已支付
        order.setPayType(1);// 支付类型 1 微信



        baseMapper.insert(order);

        return order.getOrderNo();

    }
}
