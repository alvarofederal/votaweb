package br.com.votacao.votaweb.model;

public class VotacaoDto {
	
	public Integer total; 
	public Integer votoSim; 
	public Integer votoNao;
	
	public VotacaoDto(Integer total, Integer votoSim, Integer votoNao) {
		super();
		this.total = total;
		this.votoSim = votoSim;
		this.votoNao = votoNao;
	}
	
	public Integer getTotal() {
		return total;
	}
	
	public void setTotal(Integer total) {
		this.total = total;
	}
	
	public Integer getVotoSim() {
		return votoSim;
	}
	
	public void setVotoSim(Integer votoSim) {
		this.votoSim = votoSim;
	}
	
	public Integer getVotoNao() {
		return votoNao;
	}
	
	public void setVotoNao(Integer votoNao) {
		this.votoNao = votoNao;
	} 
	
}
