package com.atguigu.order.controller;


import com.atguigu.commonutils.JWTUtils;
import com.atguigu.commonutils.R;
import com.atguigu.order.service.TOrderService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

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
        String orderId = orderService.createOrder(courseId, userId);
        return R.ok().data("orderId",orderId);
    }

}

