package br.com.votacao.votaweb.service;

import java.util.List;
import java.util.Optional;

import br.com.votacao.votaweb.model.Votacao;

public interface VotacaoService {

	Optional<Votacao> findById(long id);

	Votacao save(Votacao votacao);

	Optional<List<Votacao>> findAll();

	Votacao verificarVotoAssociado(Votacao votacao);
}
