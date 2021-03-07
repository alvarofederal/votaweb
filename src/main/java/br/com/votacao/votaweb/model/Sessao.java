package br.com.votacao.votaweb.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "sessao")
@Data
public class Sessao implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "id_pauta")
    private Pauta pauta;

    @OneToOne
    @JoinColumn(name = "id_associado")
    private Associado associado;

    @JoinColumn(name = "inicio_sessao")
    private LocalDateTime inicioVotacao;

    @JoinColumn(name = "termino_sessao")
    private LocalDateTime terminoVotacao;

    private Boolean enviarMensagemVotacaoTerminada;

}
