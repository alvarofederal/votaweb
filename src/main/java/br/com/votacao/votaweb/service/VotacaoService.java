package br.com.votacao.votaweb.service;

import java.util.List;
import java.util.Optional;

import br.com.votacao.votaweb.model.Votacao;

public interface VotacaoService {

	public Optional<Votacao> findById(int id);

	public Votacao save(Votacao votacao);

	public Optional<List<Votacao>> findAll();

	public Votacao verificarVotoAssociado(Votacao votacao);
	
	public Votacao findUltimaVotacao();

	public void deleteVotacao(Votacao votacao);
}
