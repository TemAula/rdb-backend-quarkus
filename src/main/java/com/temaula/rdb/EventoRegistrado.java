package com.temaula.rdb;

public class EventoRegistrado {

    public Long id;
    public String nome;
    public String descricao;
    public Periodo periodoVigencia;

    public EventoRegistrado(Evento novoEvento) {
        this.id = novoEvento.id;
        this.nome = novoEvento.nome;
        this.descricao = novoEvento.descricao;
        this.periodoVigencia = novoEvento.periodoVigencia;
    }

}
