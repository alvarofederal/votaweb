package br.com.votacao.votaweb.exception;

public class CpfInvalidoException extends RuntimeException {
	private static final long serialVersionUID = 3527856779591838783L;

	public CpfInvalidoException(String message){
        super(message);
    }
}

