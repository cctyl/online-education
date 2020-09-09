package com.atguigu.order.controller;


import com.atguigu.commonutils.R;
import com.atguigu.order.service.TOrderService;
import com.atguigu.order.service.TPayLogService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author tyl
 * @since 2020-09-04
 */
@RestController
@RequestMapping("/orderservice/pay")
@CrossOrigin
public class TPayController {


    @Autowired
    private TPayLogService payService;

    /**
     * 获取微信支付二维码
     * @param orderNo 订单号
     * @return
     */
    @GetMapping("/wx/qr/{orderNo}")
    @ApiOperation("获取微信支付二维码")
    public R getWxQRCode(@PathVariable("orderNo") String orderNo){

       Map<String,Object> map =  payService.createQrCode(orderNo);
       return R.ok().data(map);
    }


}

