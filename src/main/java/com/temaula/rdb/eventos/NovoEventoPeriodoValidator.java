package com.temaula.rdb.eventos;

import com.temaula.rdb.shared.constraints.CustomConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NovoEventoPeriodoValidator implements ConstraintValidator<CustomConstraint, NovoEventoRequest> {

    @Override
    public boolean isValid(NovoEventoRequest source, ConstraintValidatorContext context) {

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