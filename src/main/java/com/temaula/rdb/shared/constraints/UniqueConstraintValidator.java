package com.temaula.rdb.shared.constraints;

import io.quarkus.hibernate.orm.panache.runtime.JpaOperations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.io.Serializable;

public class UniqueConstraintValidator implements ConstraintValidator<Unique, Serializable> {

    private Unique annotation;

    @Override
    public void initialize(Unique constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(Serializable value, ConstraintValidatorContext context) {

        StringBuilder jpql = new StringBuilder(
                "select e from "
                        + this.annotation.entityType().getName()
                        + " e where ");

        if (value instanceof String && this.annotation.ignoreCase()) {
            jpql.append(" lower(e." + this.annotation.fieldName() + ") = lower(:value)");
        } else {
            jpql.append(" e." + this.annotation.fieldName() + " = :value");
        }

        boolean eventoNaoEncontrado = JpaOperations
                .getEntityManager(this.annotation.entityType())
                .createQuery(jpql.toString(), this.annotation.entityType())
                .setParameter("value", value)
                .setMaxResults(1)
                .getResultStream().count() == 0;

        return eventoNaoEncontrado;
    }
}
