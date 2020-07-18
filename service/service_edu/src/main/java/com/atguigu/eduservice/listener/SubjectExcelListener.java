package com.atguigu.eduservice.listener;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;

import java.util.Map;

public class SubjectExcelListener extends AnalysisEventListener<SubjectData> {

    private EduSubjectService eduSubjectService;

    public SubjectExcelListener() {
    }

    public SubjectExcelListener(EduSubjectService eduSubjectService) {
        this.eduSubjectService = eduSubjectService;
    }

    /**
     * 读取表头的方法
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {


    }

    /**
     * 一行一行的读取表中的数据
     * @param subjectData
     * @param analysisContext
     */
    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {

        //判断数据是否存在
        if (subjectData==null){

            throw new GuliException(20001,"表格数据为空");
        }

        //一行一行读取，每次可以读取到 一级分类   二级分类
        //再将读取到的两列数据存储到数据库中
        //因为一级分类只有一个，而二级分类有多个。所以每次添加时要判断，一级分类是否存在，存在就不添加一级分类

        //下面两个方法确定 这条数据在数据库是独一无二的，最起码在同一个 父分类下没有同样的二级分类
        //判断一级分类是否存在
        EduSubject pSubject = existOneSubject(subjectData.getOneSubjectName());
        if (pSubject==null){
            pSubject = new EduSubject();
            pSubject.setParentId("0");
            pSubject.setTitle(subjectData.getOneSubjectName());
            eduSubjectService.save(pSubject);

        }

        //判断二级分类是否存在,  pSubject如果是空，在保存过程会自动生成id并且返回给pSubject，如果不是空，直接就可以得到数据
        EduSubject cSubject = existTwoSubject(subjectData.getTwoSubjectName(), pSubject.getId());

        if(cSubject==null){

            cSubject=new EduSubject();
            cSubject.setTitle(subjectData.getTwoSubjectName());
            cSubject.setParentId(pSubject.getId());
            eduSubjectService.save(cSubject);
        }


    }


    /**
     * 判断一级分类是否存在
     * @param name
     * @return
     */
    private EduSubject existOneSubject(String name){

        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name).eq("parent_id","0");
        return eduSubjectService.getOne(wrapper);

    }


    /**
     * 判断二级分类是否存在
     * @param name
     * @return
     */
    private EduSubject existTwoSubject(String name,String pid){

        QueryWrapper<EduSubject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name).eq("parent_id",pid);
        return eduSubjectService.getOne(wrapper);

    }


    /**
     * 读取完成后执行的方法
     * @param analysisContext
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
