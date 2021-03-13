package br.com.votacao.votaweb.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "associado")
public class Associado implements Serializable {

	private static final long serialVersionUID = -7431142185740707611L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
	
    private String nomeAssociado;
    
    private String cpf;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNomeAssociado() {
		return nomeAssociado;
	}

	public void setNomeAssociado(String nomeAssociado) {
		this.nomeAssociado = nomeAssociado;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
    
}
