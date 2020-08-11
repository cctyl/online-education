package com.atguigu.cmsservice.controller;


import com.atguigu.cmsservice.entity.CrmBanner;
import com.atguigu.cmsservice.service.CrmBannerService;
import com.atguigu.commonutils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 首页banner表 前端控制器
 * </p>
 *
 * @author tyl
 * @since 2020-08-11
 */
@RestController
@RequestMapping("/educms/banneradmin")
@CrossOrigin
public class BannerAdminController {


    @Autowired
    CrmBannerService crmBannerService;

    /**
     * 分页查询banner
     * @param current
     * @param limit
     * @return
     */
    @GetMapping("/{current}/{limit}")
    public R pageBanner(@ApiParam("当前页") @PathVariable("current") Integer current,
                        @ApiParam("每页显示的记录数") @PathVariable("limit") Integer limit
                        ){


        Page<CrmBanner> crmBannerPage = new Page<>(current, limit);


        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_modified");
        crmBannerService.page(crmBannerPage,wrapper);

        return R.ok().data("items",crmBannerPage.getRecords()).data("total", crmBannerPage.getTotal());
    }







}

