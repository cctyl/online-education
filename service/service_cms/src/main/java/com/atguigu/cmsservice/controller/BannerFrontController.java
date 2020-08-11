package com.atguigu.cmsservice.controller;

import com.atguigu.cmsservice.entity.CrmBanner;
import com.atguigu.cmsservice.service.CrmBannerService;
import com.atguigu.commonutils.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 前台banner接口
 */
@RestController
@RequestMapping("/educms/bannerfront")
@CrossOrigin
public class BannerFrontController {

    @Autowired
    CrmBannerService crmBannerService;

    /**
     * 前台查询所有banner
     * @return
     */
    @GetMapping("/all")
    public R getAllBanner(){
        List<CrmBanner> crmBannerList = crmBannerService.getAllBanner();


        return R.ok().data("items",crmBannerList);
    }
}
