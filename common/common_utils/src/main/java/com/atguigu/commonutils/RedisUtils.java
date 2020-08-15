package com.atguigu.commonutils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class RedisUtils {


    @Autowired
    private StringRedisTemplate redisTemplate;


    public  void set(String key,String value){
        redisTemplate.opsForValue().set(key,value);

    }

    /**
     * 传入key和value，可以设置过期时间
     * @param key
     * @param value
     * @param minutes
     */
    public  void set(String key,String value,Long minutes){
        redisTemplate.opsForValue().set(key,value,minutes, TimeUnit.MINUTES);

    }

    public  String get(String key){
        return redisTemplate.opsForValue().get(key);
    }

    public  void hset(String key,String field,Object value){
        redisTemplate.opsForHash().put(key,field,value);
    }

    public  Object hget(String key,String field){
        return redisTemplate.opsForHash().get(key,field);
    }
}
