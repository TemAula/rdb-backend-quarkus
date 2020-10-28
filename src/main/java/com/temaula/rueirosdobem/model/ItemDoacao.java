package com.temaula.rueirosdobem.model;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

//@Entity(name="item_doacao")
@Entity
@Table(name = "tbl_item_doacao")
public class ItemDoacao implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "item_doacao_id")
	private Long id;

	// private CategoriaItem categoria;

	@Column(length = 32, nullable = false)
	private String nome;
	private double valorReferencia;
	private boolean ativo;

	// @Temporal(TemporalType.DATE)
	private LocalDate dataCriacao;

	// @OneToOne(cascade=CascadeType.ALL)
	 private Pessoa autor;

	@ManyToOne()
	@JoinColumn(name = "evento_id")
	private Evento evento;

	public ItemDoacao() {
		// this.ativa();
	}

	public ItemDoacao(Long id, String nome, double valorReferencia, boolean ativo, LocalDate dataCriacao,
			Evento evento) {
		super();
		this.id = id;
		this.nome = nome;
		this.valorReferencia = valorReferencia;
		this.ativo = ativo;
		this.dataCriacao = dataCriacao;
//		this.evento = evento;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/*
	 * public CategoriaItem getCategoria() { return categoria; }
	 * 
	 * public void setCategoria(CategoriaItem categoria) { this.categoria =
	 * categoria; }
	 */

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

	public void ativa() {
		setAtivo(true);
	}

	public void desativa() {
		setAtivo(false);
	}

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao) {
		if (dataCriacao.equals(null)) {
			dataCriacao = LocalDate.of(2020, 01, 01);
		}
		this.dataCriacao = dataCriacao;
	}

	/*
	 * public Pessoa getAutor() { return autor; }
	 * 
	 * public void setAutor(Pessoa autor) { this.autor = autor; }
	 */

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	@Override
	public String toString() {
		return "ItemDoacao [id=" + id + ", nome=" + nome + ", valorReferencia=" + valorReferencia + ", ativo=" + ativo
				+ ", dataCriacao=" + dataCriacao + "]"; //, evento=" + evento + "
	}

}