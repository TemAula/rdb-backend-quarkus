package org.acme.getting.started;

import java.io.Serializable;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Evento implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	private LocalDate dataInicio;
	private LocalDate dataFim;
	private LocalDate dataCriacao;
	private boolean ativo;
	private String descricao;
//    private List<ItemDoacao> itensDoacao;
//    private Pessoa autor;
	private String urlImagem;

	public Evento() {
	}

	public Evento(Long id, String nome, LocalDate dataInicio, LocalDate dataFim, LocalDate dataCriacao, boolean ativo,
			String descricao, String urlImagem) {
		// super();
		this.id = id;
		this.nome = nome;
		setDataInicio(dataInicio);
		setDataFim(dataFim);
		setDataCriacao(dataCriacao);
		this.ativo = ativo;
		this.descricao = descricao;
		this.urlImagem = urlImagem;
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
//		if(dataCriacao.isBefore(dataInicio) || dataCriacao.isEqual(dataInicio))
		this.dataCriacao = dataCriacao;
//		else
//			System.out.println("Errrou");
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
}