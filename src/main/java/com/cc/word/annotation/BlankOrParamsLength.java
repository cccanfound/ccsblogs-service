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
@Constraint(validatedBy = {BlankOrParamsLength.BlankOrParamsLengthValidator.class})
public @interface BlankOrParamsLength {

    String message() ;

    int min() default 1;

    int max() default 30;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    class BlankOrParamsLengthValidator implements ConstraintValidator<BlankOrParamsLength,String> {

        private int min;
        private int max;

        @Override
        public void initialize(BlankOrParamsLength constraintAnnotation) {
            max = constraintAnnotation.max();
            min = constraintAnnotation.min();
        }

        @Override
        public boolean isValid(String value, ConstraintValidatorContext context) {
            if (StringUtils.isBlank(value)) {
                return true;
            }
            if(value.length()<=max&&value.length()>=min){
                return true;
            }
            return false;
        }
    }

}
