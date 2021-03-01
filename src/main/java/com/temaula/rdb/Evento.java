package com.temaula.rdb;

import com.temaula.rdb.constraints.CustomConstraint;
import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.util.Optional;

@CustomConstraint(
        delegateTo = TemPeriodo.Validator.class,
        message = "período inválido"
)
@Entity
public class Evento extends PanacheEntityBase implements TemPeriodo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String nome;
    @Lob
    @Column(length = 400)
    @Size(max = 400)
    private String descricao;
    @NotNull
    private LocalDate dataInicio;
    @NotNull
    private LocalDate dataFim;
    @NotNull
    private LocalDate dataCadastro;

    private boolean ativo;

    /**
     * Não utilizar, é requerido pelo JPA
     */
    @Deprecated
    public Evento() {
    }

    public Evento(
            @NotBlank
                    String nome,
            @Size(max = 400)
                    String descricao,
            @NotNull
                    LocalDate dataInicio,
            LocalDate dataFim) {
        this.dataCadastro = LocalDate.now();
        this.ativo = true;
        this.setNome(nome);
        this.setDescricao(descricao);
        this.setPeriodo(dataInicio, dataFim);
    }

    public Long getId() {
        return id;
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

    public LocalDate getDataFim() {
        return dataFim;
    }

    public void setPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        this.dataInicio = dataInicio;
        this.dataFim = Optional
                .ofNullable(dataFim)
                .orElse(this.dataInicio);
    }

    public LocalDate getDataCadastro() {
        return dataCadastro;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}