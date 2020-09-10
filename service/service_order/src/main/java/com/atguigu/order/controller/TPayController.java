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


    /**
     * 查询订单支付状态
     * @param orderNo
     * @return
     */
    @GetMapping("/status/{orderNo}")
    @ApiOperation("获取支付状态")
    public R getOrderStatus(@PathVariable("orderNo") String orderNo){
        //向微信服务器查询订单状态
        Map<String,String> map = payService.queryPayStatus(orderNo);

        if (map==null){
            return R.error().message("支付出错");
        }

        //解析返回的数据，如果支付成功则返回提示
        if (map.get("trade_state").equals("SUCCESS")){
            payService.updateOrdersStatus(map);
            return R.ok().message("支付成功");
        }

        //到这里说明支付暂喂成功，返回等待的提示
        return R.ok().message("支付中");
    }



}

