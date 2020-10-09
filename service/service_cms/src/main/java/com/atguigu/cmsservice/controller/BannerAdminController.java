package com.atguigu.cmsservice.controller;


import com.atguigu.cmsservice.entity.CrmBanner;
import com.atguigu.cmsservice.service.CrmBannerService;
import com.atguigu.commonutils.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
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
    @ApiOperation("分页查询banner")
    public R pageBanner(@ApiParam("当前页") @PathVariable("current") Integer current,
                        @ApiParam("每页显示的记录数") @PathVariable("limit") Integer limit
                        ){


        Page<CrmBanner> crmBannerPage = new Page<>(current, limit);


        QueryWrapper<CrmBanner> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("gmt_modified");
        crmBannerService.page(crmBannerPage,wrapper);

        return R.ok().data("items",crmBannerPage.getRecords()).data("total", crmBannerPage.getTotal());
    }


    /**
     * 添加banner
     * @param banner
     * @return
     */
    @ApiOperation("添加banner")
    @PostMapping("/")
    public R addBanner(@ApiParam("banner数据")@RequestBody  CrmBanner banner){

        boolean save = crmBannerService.save(banner);
        if (save)
            return R.ok();
        else
            return R.error().message("添加失败");
    }


    /**
     * 删除banner
     * @param id
     * @return
     */
    @ApiOperation("删除banner")
    @DeleteMapping("/{id}")
    public R deleteBanner(@ApiParam("删除banner") @PathVariable("id") String id ){

        boolean b = crmBannerService.removeById(id);
        if (b)
            return R.ok();
        else
            return R.error().message("删除失败");
    }




    /**
     * 修改banner
     * @param banner
     * @return
     */
    @ApiOperation("修改banner")
    @PutMapping("/")
    public R updateBanner(@ApiParam("banner数据")@RequestBody  CrmBanner banner){

        boolean update = crmBannerService.updateById(banner);
        if (update)
            return R.ok();
        else
            return R.error().message("添加失败");
    }


    /**
     * 根据id查询banner
     * @param id
     * @return
     */
    @ApiOperation("查询banner")
    @GetMapping("/{id}")
    public R getBannerById(@ApiParam("banner id") @PathVariable("id") String id ){

        CrmBanner byId = crmBannerService.getById(id);
        return R.ok().data("item",byId);

    }
}

