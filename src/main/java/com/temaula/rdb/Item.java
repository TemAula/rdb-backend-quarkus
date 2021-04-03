package com.temaula.rdb;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import java.math.BigDecimal;
import java.util.Objects;
import javax.persistence.Entity;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
public class Item extends PanacheEntity {

    public static Item criarItem(final String descricao, final BigDecimal valorReferencia) {
        Item item = new Item();
        item.descricao = descricao;
        item.valorReferencia = valorReferencia;
        item.persist();
        return item;
    }

    @NotNull(message = "descrição não pode ser nula")
    @NotBlank(message = "descrição não pode estar em branco")
    public String descricao;
    @NotNull(message = "valor referência não pode ser nula")
    @DecimalMin(value = "0.00", message = "o valor de referência deve ser maior ou igual à ZERO")
    public BigDecimal valorReferencia;

    public void editar(final Item item) {
        this.descricao = item.descricao;
        this.valorReferencia = item.valorReferencia;
        this.persist();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Item)) {
            return false;
        }
        final Item item = (Item) o;

        return Objects.equals(id, item.id);

    }

    @Override
    public int hashCode() {
        return Objects.hash(id, descricao, valorReferencia);
    }

    @Override
    public void delete() {
        ItemEvento.deleteByItem(this);
        super.delete();
    }

    public static void removerTodos() {
        ItemEvento.deleteAll();
        Item.deleteAll();
    }
}