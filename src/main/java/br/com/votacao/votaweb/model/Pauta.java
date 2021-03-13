package br.com.votacao.votaweb.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pauta")
public class Pauta implements Serializable {

	private static final long serialVersionUID = 4234900483575472396L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
    private String nomePauta;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomePauta() {
		return nomePauta;
	}

	public void setNomePauta(String nomePauta) {
		this.nomePauta = nomePauta;
	}
    
}
