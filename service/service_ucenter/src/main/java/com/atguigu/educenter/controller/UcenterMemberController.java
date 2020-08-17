package com.atguigu.educenter.controller;


import com.atguigu.commonutils.JWTUtils;
import com.atguigu.commonutils.R;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.service.UcenterMemberService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author tyl
 * @since 2020-08-15
 */
@RestController
@RequestMapping("/educenter/member")
@CrossOrigin
public class UcenterMemberController {



    @Autowired
    private UcenterMemberService memberService;


    /**
     * 用户登陆方法，需要传递手机号和密码
     * @param member
     * @return
     */
    @PostMapping("/login")
    @ApiOperation("用户登陆")
    public R login(@RequestBody UcenterMember member){


        String token= memberService.login(member);

        return R.ok().data("token",token);

    }


    /**
     * 用户注册方法
     * @param registerVo
     * @return
     */
    @ApiOperation("用户注册")
    @PostMapping("/register")
    public R register(@RequestBody RegisterVo registerVo){


        memberService.register(registerVo);
        return R.ok();

    }


    /**
     * 根据token获取用户信息
     * @param request
     * @return
     */
    @GetMapping("/info")
    public R getMemberInfo(HttpServletRequest request){

        String memberIdByJwtToken = JWTUtils.getMemberIdByJwtToken(request);

        UcenterMember byId = memberService.getById(memberIdByJwtToken);
        if (byId==null){
            return R.error().message("未登陆！");
        }
        return R.ok().data("userInfo",byId);


    }

}

