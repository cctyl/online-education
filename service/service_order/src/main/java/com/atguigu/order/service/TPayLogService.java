package com.atguigu.order.service;

import com.atguigu.order.entity.TPayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author tyl
 * @since 2020-09-04
 */
public interface TPayLogService extends IService<TPayLog> {

    Map<String, Object> createQrCode(String orderNo);
}
