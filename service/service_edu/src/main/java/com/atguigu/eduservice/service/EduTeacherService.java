package com.atguigu.eduservice.service;

import com.atguigu.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author tyl
 * @since 2020-06-19
 */
public interface EduTeacherService extends IService<EduTeacher> {

    List<EduTeacher> getHotTeacher();

    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageBean);
}
