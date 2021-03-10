package br.com.votacao.votaweb.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.votacao.votaweb.model.Votacao;
import br.com.votacao.votaweb.repository.VotacaoRepository;

@Service
public class ResultadoVotacaoServiceImpl implements ResultadoVotacaoService {

	@Autowired
	VotacaoRepository votacaoRepository;

	private Optional<Votacao> findVotacao(Long id) {
		return this.votacaoRepository.findById(id);
	}

	@Override
	public Votacao resultadoVotacao(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

}
