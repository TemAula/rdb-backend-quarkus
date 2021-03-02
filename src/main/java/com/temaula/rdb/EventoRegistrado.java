package com.temaula.rdb;

import java.util.Objects;

public class EventoRegistrado {

    public Long id;
    public String nome;
    public String descricao;
    public Periodo periodoVigencia;

    /**
     * Não utilizar é pra ser usado só pelos testes
     */
    @Deprecated
    protected EventoRegistrado() {}

    public EventoRegistrado(Evento novoEvento) {
        this.id = novoEvento.id;
        this.nome = novoEvento.nome;
        this.descricao = novoEvento.descricao;
        this.periodoVigencia = novoEvento.periodoVigencia;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventoRegistrado that = (EventoRegistrado) o;
        return Objects.equals(id, that.id) && Objects.equals(nome, that.nome) && Objects.equals(descricao,
                that.descricao) && Objects
                .equals(periodoVigencia, that.periodoVigencia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nome, descricao, periodoVigencia);
    }
}
