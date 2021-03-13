package br.com.votacao.votaweb.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "resultado_votacao")
public class ResultadoVotacao implements Serializable {

	private static final long serialVersionUID = 7166606282732995836L;
	
    @Id UUID id;

    private int total;
    
    private int votoSim;
    
    private int votoNao;
    
	public UUID getId() {
		return id;
	}
	public void setId(UUID id) {
		this.id = id;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getVotoSim() {
		return votoSim;
	}
	public void setVotoSim(int votoSim) {
		this.votoSim = votoSim;
	}
	public int getVotoNao() {
		return votoNao;
	}
	public void setVotoNao(int votoNao) {
		this.votoNao = votoNao;
	}
    
}