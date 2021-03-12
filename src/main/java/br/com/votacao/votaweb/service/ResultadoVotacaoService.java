package br.com.votacao.votaweb.service;

import java.util.List;
import java.util.Optional;

import br.com.votacao.votaweb.model.Votacao;

public interface ResultadoVotacaoService{

	public Optional<List<Votacao>>  resultadoVotacao(Long id);

}
