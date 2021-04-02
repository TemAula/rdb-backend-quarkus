package com.temaula.rdb;

import com.temaula.rdb.constraints.CustomConstraint;

import javax.persistence.Embeddable;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.Optional;

@Embeddable
@CustomConstraint(
        delegateTo = Periodo.PeriodoValidator.class,
        message = Periodo.ERROR_MESSAGE
)
public class Periodo {

    static final String ERROR_MESSAGE = "período inválido";

    @NotNull(message = "data de início não pode ser nula")
    public LocalDate dataInicio;
    @NotNull(message = "data final não pode ser nula")
    public LocalDate dataFim;

    public static Periodo of(LocalDate dataInicio, LocalDate dataFim) {
        Periodo periodo = new Periodo();
        periodo.dataInicio = dataInicio;
        periodo.dataFim = dataFim;
        if(!Periodo.isValid(periodo))
            throw new IllegalArgumentException(ERROR_MESSAGE);
        return periodo;
    }

    public static boolean isValid(Periodo periodo) {
        var dataInicio = periodo.dataInicio;
        var dataFim = periodo.dataFim;
        if (dataInicio == null
                || dataFim == null) {
            return false;
        }
        return dataInicio.isBefore(dataFim)
                || dataInicio.isEqual(dataFim);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Periodo periodo = (Periodo) o;
        return Objects.equals(dataInicio, periodo.dataInicio) && Objects.equals(dataFim, periodo.dataFim);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dataInicio, dataFim);
    }

    public static class PeriodoValidator implements ConstraintValidator<CustomConstraint, Periodo> {
        @Override
        public boolean isValid(Periodo value, ConstraintValidatorContext context) {
            return Periodo.isValid(value);
        }
    }
}
