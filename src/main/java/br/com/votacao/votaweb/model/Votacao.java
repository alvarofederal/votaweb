package br.com.votacao.votaweb.model;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import br.com.votacao.votaweb.utils.VotaWebUtils;
import lombok.Data;

@Entity
@Table(name = "votacao")
@Data
public class Votacao implements Serializable {

	private static final long serialVersionUID = -6622406453767205375L;

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
