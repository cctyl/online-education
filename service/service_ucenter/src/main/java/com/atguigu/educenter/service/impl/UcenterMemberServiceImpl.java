package com.atguigu.educenter.service.impl;


import com.atguigu.commonutils.JWTUtils;

import com.atguigu.commonutils.MD5;
import com.atguigu.commonutils.RedisUtils;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.mapper.UcenterMemberMapper;
import com.atguigu.educenter.service.UcenterMemberService;
import com.atguigu.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private RedisUtils redisUtils;


    /**
     * 用户登陆
     *
     * @param loginmember
     * @return token
     */
    @Override
    public String login(UcenterMember loginmember) {


        String mobile = loginmember.getMobile();

        String password = MD5.encrypt(loginmember.getPassword());   //先进行md5加密在对比

        Integer isDisabled = loginmember.getIsDisabled();

        //手机号和密码都不能为空
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new GuliException(20001, "登陆失败");

        }


        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        wrapper.eq("mobile", mobile);

        UcenterMember ucenterMember = baseMapper.selectOne(wrapper);
        //手机号是否存在
        if (ucenterMember == null) {

            throw new GuliException(20001, "登陆失败");
        }

        //密码是否正确
        if (!ucenterMember.getPassword().equals(password)) {
            throw new GuliException(20001, "登陆失败");
        }


        //判断用户是否被禁用
        if (ucenterMember.getIsDisabled() == 1) {
            throw new GuliException(20001, "登陆失败");
        }


        //到这里之后，登陆成功


        //记录登陆数
        addDailyLoginNum();


        //生成token,把用户id和用户昵称作为数据生成token
        String jwtToken = JWTUtils.getJwtToken(ucenterMember.getId(), ucenterMember.getNickname());


        return jwtToken;
    }


    /**
     * 用户注册
     *
     * @param registerVo
     */
    @Override
    public void register(RegisterVo registerVo) {

        String code = registerVo.getCode();
        String mobile = registerVo.getMobile();
        String nickname = registerVo.getNickname();
        String password = registerVo.getPassword();

        //非空判断
        if (StringUtils.isEmpty(code) || StringUtils.isEmpty(mobile) || StringUtils.isEmpty(nickname) || StringUtils.isEmpty(password)) {

            throw new GuliException(20001, "注册失败");
        }

        String codeCheck = redisUtils.get(mobile);
        if (codeCheck == null) {
            throw new GuliException(20001, "验证码错误");
        }
        if (!codeCheck.equals(code)) {

            throw new GuliException(20001, "验证码错误");
        }

        //判断手机号是否重复
        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();

        wrapper.eq("mobile", mobile);
        Integer integer = baseMapper.selectCount(wrapper);
        //只要记录条数大于等于1，就认为这个号码重复了
        if (integer > 0) {

            throw new GuliException(20001, "手机号已存在！");
        }

        //存入数据库

        UcenterMember member = new UcenterMember();
        member.setNickname(nickname);
        member.setMobile(mobile);
        member.setPassword(MD5.encrypt(password));
        member.setAvatar("http://thirdwx.qlogo.cn/mmopen/vi_32/DYAIOgq83eoj0hHXhgJNOTSOFsS4uZs8x1ConecaVOB8eIl115xmJZcT4oCicvia7wMEufibKtTLqiaJeanU2Lpg3w/132");

        baseMapper.insert(member);

    }

    /**
     * 根据openid查询用户是否曾经用微信登陆
     *
     * @param openid
     */
    @Override
    public UcenterMember getByOpenId(String openid) {

        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();

        wrapper.eq("openid", openid);

        UcenterMember member = baseMapper.selectOne(wrapper);

        return member;
    }

    /**
     * 查询日注册人数
     *
     * @param day
     * @return
     */
    @Override
    public Integer getDailyRegister(String day) {

        return baseMapper.getDailyRegister(day);

    }

    /**
     * 只要被调用一次，就把redis中的日登陆人数+1
     */
    @Override
    public void addDailyLoginNum() {

        //从redis中拿 loginNum
        String loginNumStr = redisUtils.get("loginNum");
        //判断有没有
        if (StringUtils.isEmpty(loginNumStr)) {
            loginNumStr = "0";//没有就赋值
        }

        //转成int
        int loginNum = Integer.parseInt(loginNumStr);

        //数字加1
        loginNum++;

        //重新放回redis
        redisUtils.set("loginNum", loginNum + "");


    }


    /**
     * 从redis中拿出日登陆人数返回，并且重设日登陆人数
     *
     * @return
     */
    @Override
    public Integer getDailyLoginNum() {

        String loginNumStr = redisUtils.get("loginNum");

        //判断有没有,是空的，就直接返回0，并且重设redis
        if (StringUtils.isEmpty(loginNumStr)) {
            redisUtils.set("loginNum", "0");
            return 0;
        }
        //不为空，转换成int
        int loginNum = Integer.parseInt(loginNumStr);

        //重设记录数
        redisUtils.set("loginNum", "0");

        //返回日登陆数
        return loginNum;
    }


}
