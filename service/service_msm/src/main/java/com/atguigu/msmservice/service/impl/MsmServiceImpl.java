package com.atguigu.msmservice.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.client.naming.utils.StringUtils;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.atguigu.commonutils.ExceptionUtil;
import com.atguigu.exceptionhandler.GuliException;
import com.atguigu.msmservice.service.MsmService;
import org.springframework.stereotype.Service;

import java.rmi.ServerException;
import java.util.Map;

@Service
public class MsmServiceImpl implements MsmService {

    /**
     * 调用阿里云服务发送验证码
     * @param param
     * @param phone
     * @return
     */
    @Override
    public boolean sendMessage(Map<String, Object> param, String phone) {

        if(StringUtils.isEmpty(phone)) return false;

        DefaultProfile profile =
                DefaultProfile.getProfile("default", "yourkeyId", "yourSecret");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        //request.setProtocol(ProtocolType.HTTPS);
        request.setMethod(MethodType.POST);//固定
        request.setDomain("dysmsapi.aliyuncs.com");//固定
        request.setVersion("2017-05-25");//固定
        request.setAction("SendSms");//固定

        //下面代码中，第一个参数不能改
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "签名名字");
        request.putQueryParameter("TemplateCode", "模板CODE");
        request.putQueryParameter("TemplateParam", JSONObject.toJSONString(param));

        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            return response.getHttpResponse().isSuccess();
        } catch (ClientException e) {
            throw new GuliException(20001, ExceptionUtil.getMessage(e));
        }


    }
}
