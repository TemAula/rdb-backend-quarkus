package com.temaula.rdb;

import java.time.LocalDate;

public class NovoEventoResponse {

    private final Long id;
    private final String nome;
    private final String descricao;
    private final LocalDate dataInicio;
    private final LocalDate dataFim;

    public NovoEventoResponse(Evento novoEvento) {
        this.id = novoEvento.getId();
        this.nome = novoEvento.getNome();
        this.descricao = novoEvento.getDescricao();
        this.dataInicio = novoEvento.getDataInicio();
        this.dataFim = novoEvento.getDataFim();
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }
}
