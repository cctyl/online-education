package com.atguigu.statistics.feign;



import com.atguigu.commonutils.R;
import org.springframework.stereotype.Component;

@Component
public class EduClientFallback  implements EduClient {


    @Override
    public Integer getDailyCourseAddition(String day) {
        return 0;
    }
}
