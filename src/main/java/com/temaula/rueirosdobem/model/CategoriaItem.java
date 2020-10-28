package com.temaula.rueirosdobem.model;

import java.io.Serializable;

public class CategoriaItem implements Serializable
{

    private int id;
    private String nome;
    // private String urlIcone; // Se for necessário um ícone para a categoria

    public CategoriaItem() {}

    public CategoriaItem(int id, String nome/*, String urlIcone */)
    {
        this();
        this.setId(id);
        this.setNome(nome);
        // this.setUrlIcone(urlIcone);
    }

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

    /* // Desbloquear se necessário
    public String getUrlIcone() {
        return urlIcone;
    }
    public void setUrlIcone(String urlIcone) {
        this.urlIcone = urlIcone;
    }
    */
}