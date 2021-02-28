package com.temaula.rdb;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity(name="item_doacao")
public class ItemDoacao implements Serializable
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private CategoriaItem categoria;

    @Column(length = 32, nullable = false)
    private String nome;
    private double valorReferencia;
    private boolean ativo;

   // @Temporal(TemporalType.DATE)
   // @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime dataCriacao;

    @OneToOne(cascade=CascadeType.ALL)
    private Pessoa autor;
   
    public ItemDoacao(){
        this.ativa();
    }

    public ItemDoacao(
        Long id,
        CategoriaItem categoria,
        String nome,
        double valorReferencia,
        LocalDateTime dataCriacao,
        Pessoa autor){
        this.setId(id);
        this.setCategoria(categoria);
        this.setNome(nome);
        this.setValorReferencia(valorReferencia);
        this.setDataCriacao(dataCriacao);
        this.setAutor(autor);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public boolean getAtivo(){
        return this.ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }

    public void ativa(){
        setAtivo(true);
        
    }

    public void desativa(){
        setAtivo(false);
    }

    public LocalDateTime getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(LocalDateTime dataCriacao) {
     //   if(dataCriacao.equals(null)){
     //       setDataCriacao(LocalDateTime.now());
     //   }
        this.dataCriacao = dataCriacao;
    }

    public Pessoa getAutor() {
        return autor;
    }

    public void setAutor(Pessoa autor) {
        this.autor = autor;
    }

    @Override
    public String toString() {
        
    return "Nome: "+this.nome + ", ativo: "+this.ativo+" pessoa: "+this.autor;
    }

}