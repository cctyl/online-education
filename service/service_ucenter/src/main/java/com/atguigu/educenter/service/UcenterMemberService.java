package com.atguigu.educenter.service;

import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author tyl
 * @since 2020-08-15
 */
public interface UcenterMemberService extends IService<UcenterMember> {

    String login(UcenterMember member);

    void register(RegisterVo registerVo);

    UcenterMember getByOpenId(String openid);

    //TODO 添加一个日登陆数统计方法，每次登陆把登陆数 +1，存储到 redis中，key是loginNum

    //TODO 添加一个查询日注册人数的方法，通过 查询 gmt_create来实现
}
