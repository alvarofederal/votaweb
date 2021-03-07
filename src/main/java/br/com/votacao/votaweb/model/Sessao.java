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

    private LocalDateTime inicioSessao;

    private LocalDateTime terminoSessao;

    private Boolean mensagemTermino;

    public Sessao() {

    }
}
