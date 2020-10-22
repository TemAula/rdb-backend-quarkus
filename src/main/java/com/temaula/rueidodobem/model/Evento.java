package com.temaula.rueidodobem.model;


import java.io.Serializable;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Evento implements Serializable
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Timestamp dataCriacao;
    private boolean ativo;
    private String descricao;
    private List<ItemDoacao> itensDoacao;
    private Pessoa autor;
    private String urlImagem;

    public Evento()
    {
        this.ativa();
    }

    public Evento(int id, String nome, LocalDate dataInicio, LocalDate dataFim, Timestamp dataCriacao, String descricao, List<ItemDoacao> itensDoacao, Pessoa autor, String urlImagem)
    {
        this();
        this.setId(id);
        this.setNome(nome);
        this.setDataInicio(dataInicio);
        this.setDataFim(dataFim);
        this.setDataCriacao(dataCriacao);
        this.setDescricao(descricao);
        this.setItensDoacao(itensDoacao);
        this.setAutor(autor);
        this.setUrlImagem(urlImagem);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

    public Timestamp getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Timestamp dataCriacao) {
        this.dataCriacao = dataCriacao;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public List<ItemDoacao> getItensDoacao() {
        return itensDoacao;
    }

    public void setItensDoacao(List<ItemDoacao> itensDoacao) {
        this.itensDoacao = itensDoacao;
    }

    public Pessoa getAutor() {
        return autor;
    }

    public void setAutor(Pessoa autor) {
        this.autor = autor;
    }

    public String getUrlImagem() {
        return urlImagem;
    }

    public void setUrlImagem(String urlImagem) {
        this.urlImagem = urlImagem;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}