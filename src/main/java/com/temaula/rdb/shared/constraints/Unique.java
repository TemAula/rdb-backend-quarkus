package com.temaula.rdb.shared.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.CONSTRUCTOR,
        ElementType.PARAMETER,
        ElementType.FIELD,
        ElementType.TYPE,
        ElementType.METHOD})
public @interface Unique {

    String message() default "valor informado já cadastrado para a entidade informada";

    Class<? extends Payload>[] payload() default {};

    Class<?>[] groups() default {};

    Class<?> entityType();

    String fieldName();

    boolean ignoreCase() default true;
}
