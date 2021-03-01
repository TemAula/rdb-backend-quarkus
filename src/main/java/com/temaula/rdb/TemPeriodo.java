package com.temaula.rdb;

import com.temaula.rdb.constraints.CustomConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public interface TemPeriodo {

    LocalDate getDataInicio();

    LocalDate getDataFim();

    public static class Validator implements ConstraintValidator<CustomConstraint, TemPeriodo> {

        @Override
        public boolean isValid(TemPeriodo source, ConstraintValidatorContext context) {

            if (source.getDataInicio() == null
                    && source.getDataFim() == null) {
                return false;
            }
            if (source.getDataInicio() == null) {
                return false;
            }
            if (source.getDataFim() == null) {
                return true;
            }

            return source.getDataInicio().isBefore(source.getDataFim())
                    || source.getDataInicio().isEqual(source.getDataFim());
        }
    }
}
