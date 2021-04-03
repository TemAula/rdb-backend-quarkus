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
import java.awt.event.ItemEvent;
import java.nio.channels.FileChannel;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
public class Evento extends PanacheEntity {

    public static Evento criarEvento(String nome, String descricao, Periodo periodoVigencia) {
        Evento evento = new Evento();
        evento.nome = nome;
        evento.descricao = descricao;
        evento.periodoVigencia = periodoVigencia;
        evento.persist();
        return evento;
    }

    @NotNull(message = "nome não pode ser nulo")
    @NotBlank(message = "nome não pode estar em branco")
    public String nome;
    @Lob
    @Column(length = 400)
    @Size(max = 400, message = "descrição deve ter no máximo 400 caracteres")
    public String descricao;

    @NotNull(message = "período de vigência não pode ser nulo")
    @Valid
    public Periodo periodoVigencia;

    public static Stream<Evento> streamTodos() {
        return Evento.streamAll();
    }


    /**
     * Atualiza os valores do evento a partir de um outro {@link Evento}
     *
     * @param evento Evento para atualização
     *
     * @throws NullPointerException caso evento informado for nulo
     */
    public void atualizar(@NotNull Evento evento) {
        Objects.requireNonNull(evento, "parâmetros informados estão inválidos. Eles não podem ser nulos");
        this.nome = evento.nome;
        this.descricao = evento.descricao;
        this.periodoVigencia = evento.periodoVigencia;
    }

    @Override
    public void delete() {
        ItemEvento.deleteByEvento(this);
        super.delete();
    }

    public static void removerTodos() {
        ItemEvento.deleteAll();
        Evento.deleteAll();
    }
}