package com.atguigu.educenter.controller;


import com.atguigu.commonutils.JWTUtils;
import com.atguigu.commonutils.R;
import com.atguigu.educenter.entity.UcenterMember;
import com.atguigu.educenter.entity.vo.RegisterVo;
import com.atguigu.educenter.service.UcenterMemberService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
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
        if (StringUtils.isEmpty(memberIdByJwtToken)){
            return R.error().message("未登陆！");
        }

        QueryWrapper<UcenterMember> wrapper = new QueryWrapper<>();
        //根据id查询
        wrapper.eq("id",memberIdByJwtToken);
        //只查询指定的列
        wrapper.select("id","openid","mobile","nickname","sex","age","avatar","sign");

        UcenterMember byId = memberService.getOne(wrapper);
        if (byId==null){
            return R.error().message("未登陆！");
        }
        return R.ok().data("userInfo",byId);


    }


    /**
     * 根据用户id获取用户信息
     * @param id
     * @return
     */
    @PostMapping("/info/{id}")
    @ApiOperation("根据用户id获取用户信息")
    public UcenterMember getUserInfoById(@PathVariable("id") String id){
        UcenterMember byId = memberService.getById(id);

        return byId;
    }


    /**
     * 查询今日注册人数，返回给调用者
     * @param day
     * @return
     */
    @GetMapping("/count/{day}")
    public R getDailyRegister(@ApiParam("查询日期") @PathVariable("day") String day){

        Integer count =  memberService.getDailyRegister(day);
        return R.ok().data("count",count);
    }

    /**
     * 获取今日登陆人数
     * @return 今日登陆人数
     */
    @GetMapping("/count/loginNum")
    public Integer getDailyLoginNum(){


    }

}

