package com.lzh.security.validator.annotation;

import com.lzh.security.validator.PasswordMatchValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE,ElementType.FIELD,ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchValidator.class)
@Documented
public @interface PasswordMatch {

    String message() default "Invalid Not Match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
