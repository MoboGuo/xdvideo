package com.xdclass.xdvideo.exception;

import com.xdclass.xdvideo.domain.JsonData;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 异常处理控制器
 * @author 10782
 * @date 2020-04-06 16:05
 **/
@ControllerAdvice
public class XdExceptionHandler {


    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public JsonData Handler(Exception e){
        if(e instanceof  XdException){
            XdException xdException = (XdException) e;
            return JsonData.buildError(xdException.getMsg(),xdException.getCode());
        }else {
            return JsonData.buildError("全局错误，未知异常");
        }
    }
}
