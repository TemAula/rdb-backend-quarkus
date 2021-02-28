package com.temaula.rdb.eventos;


import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

import javax.persistence.*;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Optional;

@Entity
public class Evento extends PanacheEntityBase {

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
        this.nome = nome;
        this.descricao = descricao;
        this.dataInicio = dataInicio;
        this.dataFim = Optional
                .ofNullable(dataFim)
                .orElse(this.dataInicio);
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