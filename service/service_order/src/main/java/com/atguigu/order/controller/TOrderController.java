package com.atguigu.order.controller;


import com.atguigu.commonutils.JWTUtils;
import com.atguigu.commonutils.R;
import com.atguigu.order.entity.TOrder;
import com.atguigu.order.service.TOrderService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author tyl
 * @since 2020-09-04
 */
@RestController
@RequestMapping("/orderservice/order")
@CrossOrigin
public class TOrderController {

    @Autowired
    TOrderService orderService;

    /**
     * 生成订单
     *
     * @param courseId
     * @param request
     * @return
     */
    @PostMapping("/create/{courseId}")
    @ApiOperation("生成订单")
    public R createOrder(@PathVariable("courseId") String courseId, HttpServletRequest request) {
        String userId = JWTUtils.getMemberIdByJwtToken(request);
        if (StringUtils.isEmpty(userId)){

            return R.error().message("未登陆，请登陆");
        }
        String orderId = orderService.createOrder(courseId, userId);
        return R.ok().data("orderId",orderId);
    }


    /**
     * 根据id查询订单信息
     * @param orderId
     * @return
     */
    @GetMapping("/{orderId}")
    @ApiOperation("根据id查询订单信息")
    public R getOrderInfo(@PathVariable("orderId") String  orderId){

        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderId);
        TOrder order = orderService.getOne(wrapper);

        if (order==null){

            return R.error().message("无此订单");
        }
        return R.ok().data("item",order);

    }


    /**
     *
     * 查询用户是否购买此课程
     * @param courseId
     * @param request
     * @return
     */
    @GetMapping("/buystatus/{courseId}")
    @ApiOperation("查询用户是否购买此课程")
    public boolean isBuy(@ApiParam("课程id") @PathVariable("courseId")String courseId, HttpServletRequest request){

        //获取用户id
        String memberId = JWTUtils.getMemberIdByJwtToken(request);

        if (StringUtils.isEmpty(memberId)){
            return false;
        }
        //创建条件
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        wrapper.eq("member_id",memberId);
        wrapper.eq("status",1);


        //查看订单是否存在
        int count = orderService.count(wrapper);

        if (count>0){
            //已经购买
            return true;

        }else {
            return  false;
        }


    }
}

