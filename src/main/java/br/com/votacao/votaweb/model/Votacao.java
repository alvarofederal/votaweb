package br.com.votacao.votaweb.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "votacao")
@Data
public class Votacao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_pauta")
    private Pauta pauta;

    @OneToOne
    @JoinColumn(name = "id_associado")
    private Associado associado;

    @OneToOne
    @JoinColumn(name = "id_sessao")
    private Sessao sessao;

    private Long votoSim;

    private Long votoNao;

}
