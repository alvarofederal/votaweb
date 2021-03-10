package br.com.votacao.votaweb.model;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "resultados_votos")
@Data
public class ResultadoVotos implements Serializable {

	private static final long serialVersionUID = 8249952705726343528L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long countSim;

    private Long countNao;
    
	public ResultadoVotos(Long countSim, Long countNao) {
		this.countSim = countSim;
		this.countNao = countNao;
	}


}
