package br.com.votacao.votaweb.service;

import java.util.List;
import java.util.Optional;

import br.com.votacao.votaweb.model.Sessao;

public interface SessaoService {

	boolean isSessaoAberta(Sessao sessao);

	Optional<Sessao> findById(long id);

	void save(Sessao sessao);

	Optional<List<Sessao>> findAll();

	Sessao ultimaSessao();

}
