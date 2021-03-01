package com.temaula.rdb;

import com.temaula.rdb.constraints.CustomConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.constraints.NotBlank;
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
    public LocalDate dataInicio;
    public LocalDate dataFim;

    @Override
    public boolean isValid(NovoEventoRequest value, ConstraintValidatorContext context) {
        return Periodo.of(value.dataInicio, value.dataFim).isValid();
    }
}
