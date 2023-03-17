package com.cc.word.annotation;

import org.apache.commons.lang3.StringUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {BlankOrParamsEnum.BlankOrParamsEnumValidator.class})
public @interface BlankOrParamsEnum {

    String message() ;

    String[] paramsEnum();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class BlankOrParamsEnumValidator implements ConstraintValidator<BlankOrParamsEnum,String> {

        private String[] params;

        @Override
        public void initialize(BlankOrParamsEnum constraintAnnotation) {
            params = constraintAnnotation.paramsEnum();
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (StringUtils.isBlank(value)) {
                return true;
            }
            for (String param : params) {
                if (value.equals(param)) {
                    return true;
                }
            }
            return false;
        }
    }

}
