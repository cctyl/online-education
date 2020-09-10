package com.atguigu.order.service.impl;

import com.atguigu.commonutils.ExceptionUtil;
import com.atguigu.exceptionhandler.GuliException;
import com.atguigu.order.entity.TOrder;
import com.atguigu.order.entity.TPayLog;
import com.atguigu.order.mapper.TPayLogMapper;
import com.atguigu.order.service.TOrderService;
import com.atguigu.order.service.TPayLogService;
import com.atguigu.order.utils.HttpClientUtil;
import com.atguigu.order.utils.WxPayProperties;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.wxpay.sdk.WXPayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author tyl
 * @since 2020-09-04
 */
@Service
public class TPayLogServiceImpl extends ServiceImpl<TPayLogMapper, TPayLog> implements TPayLogService {

    @Autowired
    private TOrderService orderService;

    @Autowired
    private WxPayProperties wxPayProperties;

    /**
     * 生成微信支付二维码
     *
     * @param orderNo
     * @return
     */
    @Override
    public Map<String, Object> createQrCode(String orderNo) {

        //1 查询订单信息
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        TOrder order = orderService.getOne(wrapper);


        //2. 设置微信支付参数
        Map m = new HashMap();
        m.put("appid", wxPayProperties.getAppid());
        m.put("mch_id", wxPayProperties.getPartner());//商户id
        m.put("nonce_str", WXPayUtil.generateNonceStr());
        m.put("body", order.getCourseTitle());
        m.put("out_trade_no", orderNo);
        m.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue() + ""); //商品价格，转成字符串
        m.put("spbill_create_ip", "127.0.0.1");//项目域名
        m.put("notify_url", wxPayProperties.getNotifyurl());//回调地址
        m.put("trade_type", "NATIVE");


        //3.发送请求
        HttpClientUtil clientUtil = new HttpClientUtil(wxPayProperties.getQrurl());
        try {
            clientUtil.setXmlParam(WXPayUtil.generateSignedXml(m, wxPayProperties.getPartnerkey()));

            clientUtil.setHttps(true);
            clientUtil.post();
            
            
        //4.获得响应
            String xml = clientUtil.getContent();

        //5. 将xml转换为map
            Map<String, String> resultMap = WXPayUtil.xmlToMap(xml);

            Map map = new HashMap<>();
            map.put("out_trade_no", orderNo);
            map.put("course_id", order.getCourseId());
            map.put("total_fee", order.getTotalFee());
            map.put("result_code", resultMap.get("result_code"));
            map.put("code_url", resultMap.get("code_url"));

            return map;
        } catch (Exception e) {
            throw new GuliException(20001, ExceptionUtil.getMessage(e));
        }
    }
}
