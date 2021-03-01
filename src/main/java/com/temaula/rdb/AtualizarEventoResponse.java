package com.temaula.rdb;

import java.time.LocalDate;

public class AtualizarEventoResponse {

    public Long id;
    public String nome;
    public String descricao;
    public LocalDate dataInicio;
    public LocalDate dataFim;

    public AtualizarEventoResponse(Evento novoEvento) {
        this.id = novoEvento.id;
        this.nome = novoEvento.nome;
        this.descricao = novoEvento.descricao;
        this.dataInicio = novoEvento.periodoVigencia.dataInicio;
        this.dataFim = novoEvento.periodoVigencia.dataFim;
    }

}
