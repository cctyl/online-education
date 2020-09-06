package com.atguigu.order.service;

import com.atguigu.order.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author tyl
 * @since 2020-09-04
 */
public interface TOrderService extends IService<TOrder> {

    String createOrder(String courseId, String userId);
}
