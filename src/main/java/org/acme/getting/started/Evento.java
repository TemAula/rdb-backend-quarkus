package org.acme.getting.started;


import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Evento implements Serializable
{

	@Id
	@Column(name = "evento_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private LocalDate dataCriacao;
    private boolean ativo;
    private String descricao;    
    //private Pessoa autor;
    private String urlImagem;   

    
    @OneToMany(mappedBy = "evento", cascade = CascadeType.ALL)
    private List<EventoItemDoacao> eventoItemDoacao;

    public Evento() {}

	public Evento(Long id, String nome, LocalDate dataInicio, LocalDate dataFim, LocalDate dataCriacao, boolean ativo,
			String descricao, String urlImagem, List<EventoItemDoacao> eventoItemDoacao) {
		super();
		this.id = id;
		this.nome = nome;
		this.dataInicio = dataInicio;
		this.dataFim = dataFim;
		this.dataCriacao = dataCriacao;
		this.ativo = ativo;
		this.descricao = descricao;
		this.urlImagem = urlImagem;
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

	public LocalDate getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(LocalDate dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(boolean ativo) {
		this.ativo = ativo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getUrlImagem() {
		return urlImagem;
	}

	public void setUrlImagem(String urlImagem) {
		this.urlImagem = urlImagem;
	}

	public List<EventoItemDoacao> getEventoItemDoacao() {
		return eventoItemDoacao;
	}

	public void setEventoItemDoacao(List<EventoItemDoacao> eventoItemDoacao) {
		this.eventoItemDoacao = eventoItemDoacao;
	}
    
    
}