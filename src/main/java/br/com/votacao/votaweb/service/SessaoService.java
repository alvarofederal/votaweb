package br.com.votacao.votaweb.service;

import java.util.List;
import java.util.Optional;

import br.com.votacao.votaweb.model.Sessao;

public interface SessaoService {

	public boolean isSessaoAberta();

	public boolean isSessaoAbertaParaVotacao(Sessao sessaoBanco);

	public Optional<Sessao> findById(int id);

	public Sessao save(Sessao sessao);

	public List<Sessao> findAll();

	public Sessao findUltimaSessao();

	public void deleteSessao(Sessao sessao);



}
