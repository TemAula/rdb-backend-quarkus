package com.temaula.rdb;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Evento extends PanacheEntity {

    /**
     * Cria um evento persistido a partir de um {@link NovoEventoRequest}
     *
     * @param novoEventoRequest não pode ser nulo, caso contrário um {@link NullPointerException} será lançado
     *
     * @return um evento persistido
     *
     * @throws NullPointerException caso novoEventoRequest informado estiver nulo
     */
    public static Evento novoEvento(@NotNull NovoEventoRequest novoEventoRequest) {
        Objects.requireNonNull(novoEventoRequest, "parâmetros informados estão inválidos. Eles não podem ser nulos");
        Evento evento = new Evento();
        evento.dataCadastro = LocalDate.now();
        evento.ativo = true;
        evento.nome = novoEventoRequest.nome;
        evento.descricao = novoEventoRequest.descricao;
        evento.periodoVigencia = Periodo.of(novoEventoRequest.dataInicio, novoEventoRequest.dataFim);
        evento.persist();
        return evento;
    }

    /**
     * Atualiza os valores do evento a partir de um evento base
     *
     * @param evento evento base
     */
    public void atualizar(Evento evento) {
        this.ativo = evento.ativo;
        this.periodoVigencia = evento.periodoVigencia;
        this.nome = evento.nome;
        this.descricao = evento.descricao;
    }

    @NotBlank
    public String nome;
    @Lob
    @Column(length = 400)
    @Size(max = 400)
    public String descricao;
    @NotNull
    @Valid
    public Periodo periodoVigencia;
    @NotNull
    public LocalDate dataCadastro;

    public boolean ativo;

    /**
     * Utilize o metodo {@link Evento#novoEvento(NovoEventoRequest)} <br/> Este construtor padrão é requerido pelo JPA
     */
    public Evento() {

    }
}