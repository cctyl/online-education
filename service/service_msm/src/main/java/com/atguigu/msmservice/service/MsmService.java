package com.atguigu.msmservice.service;

import java.util.Map;

public interface MsmService {
    boolean sendMessage(Map<String, Object> param, String phone);
}
