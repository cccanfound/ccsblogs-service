package com.cc.word.exception;

import com.cc.word.utils.JsonData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 异常处理类
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private final static Logger logger= LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public JsonData handler(Exception e){

        logger.error("[异常]{}",e);
        if( e instanceof Cexception ){
            Cexception cexception = (Cexception) e;
            return JsonData.buildError(cexception.getMessage());
        }
        else{
            return JsonData.buildError("全局异常，未知错误");

        }
    }
}
