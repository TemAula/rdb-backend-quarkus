package com.temaula.rdb.eventos;

import com.temaula.rdb.shared.constraints.CustomConstraint;
import com.temaula.rdb.shared.constraints.Unique;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@CustomConstraint(
        delegateTo = NovoEventoPeriodoValidator.class,
        message = "período inválido"
)
public class NovoEventoRequest {

    @NotBlank
    @Unique(entityType = Evento.class,
            fieldName = "nome",
            message = "já tem evento registrado com o nome informado")
    private String nome;
    @Size(max = 400)
    private String descricao;
    @NotNull
    private LocalDate dataInicio;
    private LocalDate dataFim;

    public Evento criarNovoEvento() {
        return new Evento(this.nome,
                this.descricao,
                this.dataInicio,
                this.dataFim);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(LocalDate dataInicio) {
        this.dataInicio = dataInicio;
    }

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setDataFim(LocalDate dataFim) {
        this.dataFim = dataFim;
    }

}
