package br.com.votacao.votaweb.model;

import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "sessao")
public class Sessao implements Serializable {

	private static final long serialVersionUID = 597336369274149345L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = ISO.DATE_TIME)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private String inicioSessao;

	@Column
	@DateTimeFormat(pattern = "yyyy-MM-dd HH:mm", iso = ISO.DATE_TIME)
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private String terminoSessao;

	private Boolean mensagemTermino;
	
	public boolean isDataTerminoExpirada(String terminoSessao) {
		final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault());
		Instant result = Instant.from(formatter.parse(terminoSessao));
		boolean flag = result.isBefore(Instant.now());
		return flag;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInicioSessao() {
		return inicioSessao;
	}

	public void setInicioSessao(String inicioSessao) {
		this.inicioSessao = inicioSessao;
	}

	public String getTerminoSessao() {
		return terminoSessao;
	}

	public void setTerminoSessao(String terminoSessao) {
		this.terminoSessao = terminoSessao;
	}

	public Boolean getMensagemTermino() {
		return mensagemTermino;
	}

	public void setMensagemTermino(Boolean mensagemTermino) {
		this.mensagemTermino = mensagemTermino;
	}
	
}
