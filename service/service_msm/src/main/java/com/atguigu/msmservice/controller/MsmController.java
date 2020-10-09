package com.atguigu.msmservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.commonutils.RedisUtils;
import com.atguigu.msmservice.service.MsmService;
import com.atguigu.msmservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/edumsm/msm")

public class MsmController {


    @Autowired
    private MsmService msmService;


    @Autowired
    private RedisUtils  redisUtils;

    /**
     * 发送手机验证码
     * @param phone
     * @return
     */
    @GetMapping("/send/{phone}")
    public R sendMsm(@PathVariable("phone") String phone){


        //判断是不是已经发过了，如果已经发过来，就不再发送
        String codeByPhone = redisUtils.get(phone);
        if (!StringUtils.isEmpty(codeByPhone)){

            return R.ok();
        }

        //为空，没发过或者已经超时，重发一份
        String code = RandomUtil.getFourBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code",code);


        boolean result = msmService.sendMessage(param,phone);

        if (result){
            //发送成功，存入redis
            redisUtils.set(phone,code,5l);
            return R.ok();
        }else
            return R.error();

    }
}
