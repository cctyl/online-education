package com.atguigu.aclservice.controller;


import com.atguigu.aclservice.entity.Permission;
import com.atguigu.aclservice.service.PermissionService;
import com.atguigu.commonutils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限 菜单管理
 * </p>
 *
 * @author testjava
 * @since 2020-10-15
 */
@RestController
@RequestMapping("/admin/acl/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;


    /**
     * 查询所有菜单
     *
     * @return
     */
    @GetMapping("/all")
    @ApiOperation("查询所有菜单")
    public R getAllPermisson() {
        List<Permission> permissionList = permissionService.getAllPermisson();
        return R.ok().data("list", permissionList);

    }


    /**
     * 删除权限以及下面的子权限
     * @param id
     * @return
     */
    @DeleteMapping("/{id}")
    public R deletePermission(@PathVariable("id") String id){

        permissionService.deletePermissionById(id);
        return R.ok();
    }



}

