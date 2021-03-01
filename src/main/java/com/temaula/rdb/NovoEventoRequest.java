package com.temaula.rdb;

import com.temaula.rdb.constraints.CustomConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@CustomConstraint(
        delegateTo = NovoEventoRequest.class,
        message = "período inválido"
)
public class NovoEventoRequest implements ConstraintValidator<CustomConstraint,NovoEventoRequest> {

    @NotBlank
    public String nome;
    @Size(max = 400)
    public String descricao;
    @NotNull
    public LocalDate dataInicio;
    public LocalDate dataFim;

    @Override
    public boolean isValid(NovoEventoRequest value, ConstraintValidatorContext context) {
        return Evento.temPeriodoValido(value.dataInicio, value.dataFim);
    }
}
