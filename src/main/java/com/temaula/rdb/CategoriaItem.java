package com.temaula.rdb;

import java.io.Serializable;

public class CategoriaItem implements Serializable {

    private int id;
    private String nome;
    // private String urlIcone; // Se for necessário um ícone para a categoria

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

}