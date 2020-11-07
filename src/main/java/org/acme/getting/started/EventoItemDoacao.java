package org.acme.getting.started;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class EventoItemDoacao implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private int qtdObtida;
	private int qtdNecessario;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "evento_id")
	private Evento evento;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "item_doacao_id")
	private ItemDoacao itemDoacao;

	public EventoItemDoacao() {
		// TODO Auto-generated constructor stub
	}

	public EventoItemDoacao(Long id, int qtdObtida, int qtdNecessario, Evento evento, ItemDoacao itemDoacao) {
		super();
		this.id = id;
		this.qtdObtida = qtdObtida;
		this.qtdNecessario = qtdNecessario;
		this.evento = evento;
		this.itemDoacao = itemDoacao;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getQtdObtida() {
		return qtdObtida;
	}

	public void setQtdObtida(int qtdObtida) {
		this.qtdObtida = qtdObtida;
	}

	public int getQtdNecessario() {
		return qtdNecessario;
	}

	public void setQtdNecessario(int qtdNecessario) {
		this.qtdNecessario = qtdNecessario;
	}

	public Evento getEvento() {
		return evento;
	}

	public void setEvento(Evento evento) {
		this.evento = evento;
	}

	public ItemDoacao getItemDoacao() {
		return itemDoacao;
	}

	public void setItemDoacao(ItemDoacao itemDoacao) {
		this.itemDoacao = itemDoacao;
	}

}
