package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.commonutils.ExceptionUtil;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author tyl
 * @since 2020-07-17
 */
@Service
@Slf4j
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    @Autowired
    EduSubjectMapper eduSubjectMapper;

    /**
     * 保存文件
     * @param file
     */
    @Override
    public void saveSubject(MultipartFile file) {

        try {
            EasyExcel.read(file.getInputStream(), SubjectData.class,new SubjectExcelListener(this)).sheet().doRead();


        } catch (IOException e) {
           log.error(ExceptionUtil.getMessage(e));
        }
    }

    /**
     * 获取组装好的课程信息
     * @return
     */
    @Override
    public List<OneSubject> getAllOrderSubject() {
        //一级分类课程列表，里面会装二级分类
        List<OneSubject> oneSubjects = new ArrayList<>();

        //步骤一：查询所有一级分类，并且转换为 List<OneSubject>
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<EduSubject> pSubjects = eduSubjectMapper.selectList(wrapperOne);
        for (EduSubject pSubject : pSubjects) {
            oneSubjects.add(new OneSubject(pSubject.getId(),pSubject.getTitle()));
        }

        //步骤二：根据id，查询这个一级分类下面的二级分类，并把查询结果封装到 oneSubjects
        for (OneSubject oneSubject : oneSubjects) {
            List<EduSubject> cSubjects = eduSubjectMapper.selectList(new QueryWrapper<EduSubject>().eq("parent_id", oneSubject.getId()));
            List<TwoSubject> twoSubjects = new ArrayList<>();
            for (EduSubject cSubject : cSubjects) {
                //将List<EduSubject> 转换为   List<TwoSubject>
               twoSubjects.add(new TwoSubject(cSubject.getId(),cSubject.getTitle()));

            }

            //将twoSubjects添加到一级分类里面
            oneSubject.setChildren(twoSubjects);

        }


        return oneSubjects;
    }
}
