package com.atguigu.exceptionhandler;

import com.atguigu.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)//当出现 Exception.class  的异常时，就执行这个方法
    @ResponseBody   //为了返回数据
    public R error(Exception e){
        log.warn("出现了异常，已经被全局异常处理捕获："+e.toString());

        return R.error().message("执行了全局异常处理");
    }
}
