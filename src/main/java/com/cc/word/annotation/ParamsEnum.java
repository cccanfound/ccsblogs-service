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
@Constraint(validatedBy = {ParamsEnum.ParamsEnumValidator.class})
public @interface ParamsEnum {

    String message() ;

    String[] paramsEnum();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class ParamsEnumValidator implements ConstraintValidator<ParamsEnum,String> {

        private String[] params;

        @Override
        public void initialize(ParamsEnum constraintAnnotation) {
            params = constraintAnnotation.paramsEnum();
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (StringUtils.isBlank(value)) {
                return false;
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
