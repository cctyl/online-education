package com.atguigu.exceptionhandler;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Api("自定义的异常处理类")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuliException extends RuntimeException {

    @ApiModelProperty("异常状态码")
    private Integer code;

    @ApiModelProperty("异常信息")
    private String message;


}
