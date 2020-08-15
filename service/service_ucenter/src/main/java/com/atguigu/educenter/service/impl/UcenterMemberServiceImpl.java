package com.atguigu.educenter.service.impl;

import com.atguigu.commonutils.JWTUtils;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.mapper.UcenterMemberMapper;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.management.Query;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author tyl
 * @since 2020-08-15
 */
@Service
public class UcenterMemberServiceImpl extends ServiceImpl<UcenterMemberMapper, UcenterMember> implements UcenterMemberService {

    /**
     * 用户登陆
     * @param loginmember
     * @return token
     */
    @Override
    public String login(UcenterMember loginmember) {


        String mobile = loginmember.getMobile();
        String password = loginmember.getPassword();
        Boolean isDisabled = loginmember.getIsDisabled();

        //手机号和密码都不能为空
        if (StringUtils.isEmpty(mobile)|| StringUtils.isEmpty(password)){
            throw new GuliException(20001,"登陆失败");

        }


        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile",mobile);

        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);
        //手机号是否存在
        if (ucenterMember==null){

            throw new GuliException(20001,"登陆失败");
        }

       //密码是否正确
        if ( !ucenterMember.getPassword().equals(password)) {
            throw new GuliException(20001,"登陆失败");
        }


        //判断用户是否被禁用
        if (ucenterMember.getIsDisabled()){
            throw new GuliException(20001,"登陆失败");
        }


        //到这里之后，登陆成功
        //生成token,把用户id和用户昵称作为数据生成token
        String jwtToken = JWTUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());


        return jwtToken;
    }
}
