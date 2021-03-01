package com.temaula.rdb.constraints;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = CustomConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.CONSTRUCTOR,
        ElementType.FIELD,
        ElementType.TYPE,
        ElementType.METHOD})
public @interface CustomConstraint {

    String message() default "invalid";

    Class<? extends Payload>[] payload() default {};

    Class<?>[] groups() default {};

    Class<? extends ConstraintValidator<CustomConstraint, ? extends Object>> delegateTo();

}
