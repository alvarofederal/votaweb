package br.com.votacao.votaweb.exception;

public class IntegracaoException extends RuntimeException {

	private static final long serialVersionUID = 4617135746790758323L;

	public IntegracaoException(String message){
        super(message);
    }
}
