package com.atguigu.msmservice.controller;

import com.atguigu.commonutils.R;
import com.atguigu.msmservice.service.MsmService;
import com.atguigu.msmservice.utils.RandomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/edumsm/msm")
@CrossOrigin
public class MsmController {


    @Autowired
    private MsmService msmService;


    /**
     * 发送手机验证码
     * @param phone
     * @return
     */
    @GetMapping("/send/{phone}")
    public R sendMsm(@PathVariable("phone") String phone){
        String code = RandomUtil.getFourBitRandom();

        Map<String,Object> param = new HashMap<>();
        param.put("code",code);


        boolean result = msmService.sendMessage(param,phone);

        if (result){

            return R.ok();
        }else
            return R.error();

    }
}
