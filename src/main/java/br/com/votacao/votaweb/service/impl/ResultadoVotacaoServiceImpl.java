package br.com.votacao.votaweb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.votacao.votaweb.repository.ResultadoVotacao;
import br.com.votacao.votaweb.model.VotacaoDto;
import br.com.votacao.votaweb.repository.ResultadosVotacaoRepository;
import br.com.votacao.votaweb.service.ResultadoVotacaoService;

@Service
public class ResultadoVotacaoServiceImpl implements ResultadoVotacaoService {
	
	@Autowired
	ResultadosVotacaoRepository resultadosVotacaoRepository;

	@Override
	public VotacaoDto findResultadoVotacaoPorPauta(int id) {
		ResultadoVotacao votacao = resultadosVotacaoRepository.findResultadoVotacaoPorPauta(id);
		int total = votacao.getTotal();
		int votoSim = votacao.getVotoSim();
		int votoNao = votacao.getVotoNao();
		VotacaoDto votacaoDto = new VotacaoDto(total, votoSim, votoNao);
		return votacaoDto;
	}
}
