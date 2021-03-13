package br.com.votacao.votaweb.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "votacao")
public class Votacao implements Serializable {

	private static final long serialVersionUID = -6622406453767205375L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@OneToOne
	@JoinColumn(name = "id_pauta")
	private Pauta pauta;

	@OneToOne
	@JoinColumn(name = "id_associado")
	private Associado associado;

	@OneToOne
	@JoinColumn(name = "id_sessao")
	private Sessao sessao;

	private int voto;
	
	public Votacao() {

	}

	public Votacao(int id, Associado associado, Pauta pauta,  Sessao sessao, int voto) {
		this.id = id;
		this.associado = associado;
		this.pauta = pauta;
		this.sessao = sessao;
		this.voto = voto;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Pauta getPauta() {
		return pauta;
	}

	public void setPauta(Pauta pauta) {
		this.pauta = pauta;
	}

	public Associado getAssociado() {
		return associado;
	}

	public void setAssociado(Associado associado) {
		this.associado = associado;
	}

	public Sessao getSessao() {
		return sessao;
	}

	public void setSessao(Sessao sessao) {
		this.sessao = sessao;
	}

	public int getVoto() {
		return voto;
	}

	public void setVoto(int voto) {
		this.voto = voto;
	}
	
}
