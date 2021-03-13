package br.com.votacao.votaweb.model;

public class VotacaoDto {
	
	public int total; 
	public int votoSim; 
	public int votoNao;
	
	public VotacaoDto(int total, int votoSim, int votoNao) {
		super();
		this.total = total;
		this.votoSim = votoSim;
		this.votoNao = votoNao;
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
