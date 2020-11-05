package org.acme.getting.started;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
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

	@OneToMany(mappedBy = "itemDoacao")
	private List<EventoItemDoacao> eventoItemDoacao;

//	@ManyToOne
//	@JoinColumn(name = "evento_item_doacao_id")
//	private EventoItemDoacao eventoItemDoacao;

	public ItemDoacao() {
		// TODO Auto-generated constructor stub
	}

	public ItemDoacao(Long id, String nome, double valorReferencia, boolean ativo, LocalDate dataCriacao,
			List<EventoItemDoacao> eventoItemDoacao) {
		super();
		this.id = id;
		this.nome = nome;
		this.valorReferencia = valorReferencia;
		this.ativo = ativo;
		this.dataCriacao = dataCriacao;
		this.eventoItemDoacao = eventoItemDoacao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public List<EventoItemDoacao> getEventoItemDoacao() {
		return eventoItemDoacao;
	}

	public void setEventoItemDoacao(List<EventoItemDoacao> eventoItemDoacao) {
		this.eventoItemDoacao = eventoItemDoacao;
	}
}