package com.temaula.rdb;

import com.temaula.rdb.constraints.CustomConstraint;

import javax.persistence.Embeddable;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Optional;

@Embeddable
@CustomConstraint(
        delegateTo = Periodo.PeriodoValidator.class
)
public class Periodo implements Serializable {

    @NotNull
    public LocalDate dataInicio;
    @NotNull
    public LocalDate dataFim;

    public static Periodo of(LocalDate dataInicio, LocalDate dataFim) {
        Periodo periodo = new Periodo();
        periodo.dataInicio = dataInicio;
        periodo.dataFim = Optional
                .ofNullable(dataFim)
                .orElse(dataInicio);
        return periodo;
    }

    public boolean isValid() {
        if (dataInicio == null
                && dataFim == null) {
            return false;
        }
        if (dataInicio == null) {
            return false;
        }
        if (dataFim == null) {
            return true;
        }
        return dataInicio.isBefore(dataFim)
                || dataInicio.isEqual(dataFim);
    }

    public static class PeriodoValidator implements ConstraintValidator<CustomConstraint, Periodo> {
        @Override
        public boolean isValid(Periodo value, ConstraintValidatorContext context) {
            return value.isValid();
        }
    }
}
