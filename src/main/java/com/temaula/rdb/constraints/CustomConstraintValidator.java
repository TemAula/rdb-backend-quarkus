package com.temaula.rdb.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * ConstraintValidator criada tratar anotações do tipo {@link CustomConstraint} para ser possível habilitar validações customizadas
 */
public class CustomConstraintValidator implements ConstraintValidator<CustomConstraint, Object> {

    private CustomConstraint constraintAnnotation;

    @Override
    public void initialize(CustomConstraint constraintAnnotation) {
        this.constraintAnnotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        try {
            final Constructor<? extends ConstraintValidator<CustomConstraint, ? extends Object>> constructor =
                    this.constraintAnnotation
                            .delegateTo()
                            .getConstructor(new Class[0]);
            constructor.setAccessible(true);
            final ConstraintValidator constraintValidator =
                    constructor.newInstance(new Object[0]);
            return constraintValidator.isValid(value, context);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            throw new RuntimeException(e);
        }
    }
}
