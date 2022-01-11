package com.cc.word.exception;

import com.cc.word.utils.JsonData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class ValidatelExceptionHandler {
    private static Logger logger = LoggerFactory.getLogger(ValidatelExceptionHandler.class);

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public JsonData handleInvalidRequest(MethodArgumentNotValidException de) {
        List<FieldError> bindingResult = de.getBindingResult().getFieldErrors();
        String errMsg = "";
        for (FieldError fieldError : bindingResult) {
            errMsg = fieldError.getDefaultMessage();
        }
        logger.info("参数校验不通过：[{}]", errMsg);
        return JsonData.buildFail(errMsg);
    }

}
