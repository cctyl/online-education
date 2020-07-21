package com.atguigu;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.write.builder.ExcelWriterBuilder;
import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.excel.DemoData;
import com.atguigu.excel.ExcelListener;
import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import org.junit.Test;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class test<T,E> {

    @Test
    public void main1() {

        // 1、创建代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 2、全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        System.out.println(projectPath);
        //设置输出路径，建议写绝对路径，因为不容易出错
        gc.setOutputDir("E:\\IDEAProject\\guli_parent\\service\\service_edu"+ "/src/main/java");
        gc.setAuthor("tyl");//作者
        gc.setOpen(false); //生成后是否打开资源管理器
        gc.setFileOverride(false); //重新生成时文件是否覆盖，如果写true，就会把原有文件里的内容覆盖掉

        /*
         * mp生成service层代码，默认接口名称第一个字母有 I
         * UcenterService
         * */
        gc.setServiceName("%sService");	//去掉Service接口的首字母I，不加默认会有一个IxxxService
        gc.setIdType(IdType.ID_WORKER_STR); //主键策略，因为字段是 char类型，用ID_WORKER_STR（新版有其他方法）
        gc.setDateType(DateType.ONLY_DATE);//定义生成的实体类中日期类型
        gc.setSwagger2(true);//开启Swagger2模式

        mpg.setGlobalConfig(gc);

        // 3、数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        dsc.setUrl("jdbc:mysql://localhost:3306/guli?serverTimezone=GMT%2B8");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("root");
        dsc.setPassword("root");
        dsc.setDbType(DbType.MYSQL);
        mpg.setDataSource(dsc);

        // 4、包配置
        PackageConfig pc = new PackageConfig();
        pc.setModuleName("eduservice"); //子包名，会添加在包名后面，所以不要有横杠，最好就是单独一个单词
        pc.setParent("com.atguigu");//包名
        pc.setController("controller");
        pc.setEntity("entity");
        pc.setService("service");
        pc.setMapper("mapper");
        mpg.setPackageInfo(pc);

        // 5、策略配置
        StrategyConfig strategy = new StrategyConfig();

        strategy.setInclude("edu_course","edu_course_description","edu_chapter","edu_video"); //表的名称，多张表用逗号隔开

        strategy.setNaming(NamingStrategy.underline_to_camel);//数据库表映射到实体的命名策略
        strategy.setTablePrefix(pc.getModuleName() + "_"); //生成实体时去掉表前缀

        strategy.setColumnNaming(NamingStrategy.underline_to_camel);//数据库表字段映射到实体的命名策略
        strategy.setEntityLombokModel(true); // lombok 模型 @Accessors(chain = true) setter链式操作

        strategy.setRestControllerStyle(true); //restful api风格控制器
        strategy.setControllerMappingHyphenStyle(true); //url中驼峰转连字符

        mpg.setStrategy(strategy);

        // 6、执行
        mpg.execute();
    }


    @Test
    public void main2(){
        //1.设置文件路径和文件名称
        String filename = "G:\\write.xlsx";

        //构建数据，等会插入表格中
        List<DemoData> demoDataList = new ArrayList<>();
        demoDataList.add(new DemoData(1,"张三"));
        demoDataList.add(new DemoData(2,"李四"));
        demoDataList.add(new DemoData(3,"王五"));
        demoDataList.add(new DemoData(4,"赵柳"));
        demoDataList.add(new DemoData(5,"钱富"));

        //2.调用easyexcel里面的方法实现写操作
        // 参数1 文件名和路径       参数2 和表格对应的实体类的class
        // sheet 是设置表格底下的分页的名字
        // doWrite 里面传一个List<DemoData>  ,也就是一堆的DemoData对象，直接传进去就会被写入
         EasyExcel.write(filename, DemoData.class).sheet("学生列表").doWrite(demoDataList);

    }

    @Test
    public void main3(){
        //1.设置文件路径和文件名称
        String filename = "G:\\write.xlsx";
        //2.调用方法实现读取
        //参数1： 文件路径     参数2： 对应的实体类 class   参数3：new一个excel监听器
        //后面的.sheet().doRead(); 直接写上去即可
        EasyExcel.read(filename,DemoData.class,new ExcelListener()).sheet().doRead();

    }

    @Test
    public void main5(){
       List<EduChapter> eduChapters = new ArrayList<>();
       eduChapters.add(new EduChapter().setTitle("课程1").setId("1"));
       eduChapters.add(new EduChapter().setTitle("课程2").setId("2"));
       eduChapters.add(new EduChapter().setTitle("课程3").setId("3"));
       eduChapters.add(new EduChapter().setTitle("课程4").setId("4"));
       eduChapters.add(new EduChapter().setTitle("课程5").setId("5"));
       eduChapters.add(new EduChapter().setTitle("课程6").setId("6"));
       eduChapters.add(new EduChapter().setTitle("课程7").setId("7"));
       eduChapters.add(new EduChapter().setTitle("课程8").setId("8"));

       List<ChapterVo> chapterVos = new ArrayList<>();

       //失败，泛型不太会用

    }


    /**
     * 将List<E> 转换为 List<T>
     * @param oldList
     * @param newObj
     * @return
     */
    public List<Object> copyValueToList(List<Object> oldList,Object newObj){
        List<Object> newList = new ArrayList<>();
        for (Object e : oldList) {
            BeanUtils.copyProperties(e,newObj);
            newList.add(newObj);
        }

        return newList;
    }
}

