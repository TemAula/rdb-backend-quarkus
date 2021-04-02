package com.temaula.rdb;

import io.quarkus.hibernate.orm.panache.PanacheEntity;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Item extends PanacheEntity {

    @NotNull(message = "descrição não pode ser nula")
    @NotBlank(message = "descrição não pode estar em branco")
    public String descricao;

    @NotNull(message = "valor referência não pode ser nula")
    @DecimalMin(value = "0.00", message = "o valor de referência deve ser maior ou igual à ZERO")
    public BigDecimal valorReferencia;

}