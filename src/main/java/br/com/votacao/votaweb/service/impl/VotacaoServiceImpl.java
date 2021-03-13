package br.com.votacao.votaweb.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.votacao.votaweb.model.Votacao;
import br.com.votacao.votaweb.repository.VotacaoRepository;
import br.com.votacao.votaweb.service.VotacaoService;

@Service
public class VotacaoServiceImpl implements VotacaoService {

	@Autowired
	public VotacaoRepository votacaoRepository;

	@Override
	public Optional<Votacao> findById(int id) {
		return votacaoRepository.findById(id);
	}

	@Override
	public Votacao save(Votacao votacao) {
		return votacaoRepository.save(votacao);
	}

	@Override
	public Optional<List<Votacao>> findAll() {
		return Optional.of(votacaoRepository.findAll());
	}

	@Override
	public Votacao verificarVotoAssociado(Votacao votacao) {
		Votacao votacaoBanco = new Votacao();
		votacaoBanco = votacaoRepository.verificarVotoAssociado(
				votacao.getAssociado().getId(),
				votacao.getPauta().getId());
		return votacaoBanco;
	}

	@Override
	public Votacao findUltimaVotacao() {
		return votacaoRepository.findUltimaVotacao();
	}

	@Override
	public void deleteVotacao(Votacao votacao) {
		votacaoRepository.delete(votacao);
	}
}