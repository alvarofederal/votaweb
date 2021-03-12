package br.com.votacao.votaweb.model;

import java.io.Serializable;
import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "resultado_votacao")
@Data
public class ResultadoVotacao implements Serializable {

	private static final long serialVersionUID = 7166606282732995836L;
	
    @Id UUID id;

    private int total;
    private int votoSim;
    private int votoNao;
}