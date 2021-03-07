package br.com.votacao.votaweb.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "resultados_votos")
@Data
public class ResultadoVotos implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Boolean votoComputado;

    private Long countSim;

    private Long countNao;

}
