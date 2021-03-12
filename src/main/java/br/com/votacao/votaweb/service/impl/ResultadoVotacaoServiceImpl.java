package br.com.votacao.votaweb.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.votacao.votaweb.model.Votacao;
import br.com.votacao.votaweb.repository.VotacaoRepository;
import br.com.votacao.votaweb.service.ResultadoVotacaoService;

@Service
public class ResultadoVotacaoServiceImpl implements ResultadoVotacaoService {

	@Autowired
	VotacaoRepository votacaoRepository;

	@Override
	public Optional<List<Votacao>>  resultadoVotacao(Long id) {
		return null;
	}

}
