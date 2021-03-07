package br.com.votacao.votaweb.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "votacao")
@Data
public class Votacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_pauta")
    private Pauta pauta;

    @OneToOne
    @JoinColumn(name = "id_associado")
    private Associado associado;

    private String voto;

}
