package com.temaula.rdb;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.nio.channels.FileChannel;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "evento")
public class Evento extends PanacheEntity {

    @NotNull
    @NotBlank
    public String nome;
    @Lob
    @Column(length = 400)
    @Size(max = 400)
    public String descricao;

    @NotNull
    @Valid
    public Periodo periodoVigencia;

    public static Stream<Evento> streamTodos() {
        return Evento.streamAll();
    }


    /**
     * Atualiza os valores do evento a partir de um outro {@link Evento}
     *
     * @param evento
     *
     * @throws NullPointerException caso evento informado for nulo
     */
    public void atualizar(@NotNull Evento evento) {
        Objects.requireNonNull(evento, "parâmetros informados estão inválidos. Eles não podem ser nulos");
        this.nome = evento.nome;
        this.descricao = evento.descricao;
        this.periodoVigencia = evento.periodoVigencia;
    }
}