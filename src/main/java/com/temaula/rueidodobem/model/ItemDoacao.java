package com.temaula.rueidodobem.model;

import java.io.Serializable;
import java.sql.Timestamp;

public class ItemDoacao implements Serializable
{

    private int id;
    private CategoriaItem categoria;
    private String nome;
    private double valorReferencia;
    private boolean ativo;
    private Timestamp dataCriacao;
    private Pessoa autor;

    public ItemDoacao()
    {
        this.ativa();
    }

    public ItemDoacao(int id, CategoriaItem categoria, String nome, double valorReferencia, Timestamp dataCriacao, Pessoa autor)
    {
        this();
        this.setId(id);
        this.setCategoria(categoria);
        this.setNome(nome);
        this.setValorReferencia(valorReferencia);
        this.setDataCriacao(dataCriacao);
        this.setAutor(autor);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CategoriaItem getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaItem categoria) {
        this.categoria = categoria;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public double getValorReferencia() {
        return valorReferencia;
    }

    public void setValorReferencia(double valorReferencia) {
        this.valorReferencia = valorReferencia;
    }

    public boolean isAtivo() {
        return ativo;
    }

    private void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public void ativa()
    {
        setAtivo(true);
    }

    public void desativa()
    {
        setAtivo(false);
    }

    public Timestamp getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Timestamp dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Pessoa getAutor() {
        return autor;
    }

    public void setAutor(Pessoa autor) {
        this.autor = autor;
    }


}