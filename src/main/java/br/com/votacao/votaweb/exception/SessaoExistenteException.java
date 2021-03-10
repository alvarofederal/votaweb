package br.com.votacao.votaweb.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.FORBIDDEN, reason="Já existe uma sessão aberta. Aproveite para efetuar seu voto!!!")
public class SessaoExistenteException extends RuntimeException {
	
	private static final long serialVersionUID = 391643314131912492L;

	public SessaoExistenteException(String message) {
		super(message);
	}
}
