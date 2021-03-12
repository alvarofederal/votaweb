package br.com.votacao.votaweb.service;

import br.com.votacao.votaweb.model.VotacaoDto;

public interface ResultadoVotacaoService{

	public VotacaoDto findResultadoVotacaoPorPauta(Long id);

}
