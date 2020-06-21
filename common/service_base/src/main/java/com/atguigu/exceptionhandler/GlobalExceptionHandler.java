package com.atguigu.exceptionhandler;

import com.atguigu.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)//当出现 Exception.class  的异常时，就执行这个方法
    @ResponseBody   //为了返回数据
    public R error(Exception e){
        log.warn("出现了异常，已经被全局异常处理捕获："+e.toString());

        return R.error().message("啊哦，出错了");
    }


    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    public R methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e){
        log.warn("出现了类型转换异常，已经被全局异常处理捕获："+e.toString());

        return R.error().message("啊哦，出错了，你输入的数据格式不对");
    }


    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public R guliException(GuliException e){
        String message = e.getMessage();
        Integer code = e.getCode();
        log.warn("出现了自定义异常，已经被捕获："+e.toString());

        return R.error().message(message).data("code",code);
    }
}
