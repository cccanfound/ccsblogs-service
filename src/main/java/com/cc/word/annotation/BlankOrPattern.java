package com.cc.word.annotation;


import org.apache.commons.lang3.StringUtils;
import org.hibernate.validator.constraintvalidation.HibernateConstraintValidatorContext;
import org.hibernate.validator.internal.engine.messageinterpolation.util.InterpolationHelper;
import org.hibernate.validator.internal.util.logging.Log;
import org.hibernate.validator.internal.util.logging.LoggerFactory;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.invoke.MethodHandles;
import java.util.regex.Matcher;
import java.util.regex.PatternSyntaxException;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Repeatable(BlankOrPattern.List.class)
@Documented
@Constraint(validatedBy = BlankOrPattern.BlankOrPatternValidator.class)
public @interface BlankOrPattern {


    String regexp();


    Flag[] flags() default { };


    String message() default "{com.eastcom.edufusion.common.annotation.BlankOrPattern.message}";


    Class<?>[] groups() default { };


    Class<? extends Payload>[] payload() default { };

    public static enum Flag {


        UNIX_LINES( java.util.regex.Pattern.UNIX_LINES ),


        CASE_INSENSITIVE( java.util.regex.Pattern.CASE_INSENSITIVE ),


        COMMENTS( java.util.regex.Pattern.COMMENTS ),


        MULTILINE( java.util.regex.Pattern.MULTILINE ),


        DOTALL( java.util.regex.Pattern.DOTALL ),


        UNICODE_CASE( java.util.regex.Pattern.UNICODE_CASE ),


        CANON_EQ( java.util.regex.Pattern.CANON_EQ );


        private final int value;

        private Flag(int value) {
            this.value = value;
        }


        public int getValue() {
            return value;
        }
    }


    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    @Documented
    @interface List {

        BlankOrPattern[] value();
    }


    class BlankOrPatternValidator implements ConstraintValidator<BlankOrPattern, CharSequence> {

        private static final Log LOG = LoggerFactory.make( MethodHandles.lookup() );

        private java.util.regex.Pattern pattern;
        private String escapedRegexp;

        @Override
        public void initialize(BlankOrPattern parameters) {
            Flag[] flags = parameters.flags();
            int intFlag = 0;
            for ( Flag flag : flags ) {
                intFlag = intFlag | flag.getValue();
            }

            try {
                pattern = java.util.regex.Pattern.compile( parameters.regexp(), intFlag );
            }
            catch (PatternSyntaxException e) {
                throw LOG.getInvalidRegularExpressionException( e );
            }

            escapedRegexp = InterpolationHelper.escapeMessageParameter( parameters.regexp() );
        }

        @Override
        public boolean isValid(CharSequence value, ConstraintValidatorContext constraintValidatorContext) {
            if (StringUtils.isBlank(value)) {
                return true;
            }

            if ( constraintValidatorContext instanceof HibernateConstraintValidatorContext) {
                constraintValidatorContext.unwrap( HibernateConstraintValidatorContext.class ).addMessageParameter( "regexp", escapedRegexp );
            }

            Matcher m = pattern.matcher( value );
            return m.matches();
        }
    }
}
