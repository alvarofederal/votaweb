package br.com.votacao.votaweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.votacao.votaweb.model.ResultadoVotacao;
import br.com.votacao.votaweb.model.VotacaoDto;
import br.com.votacao.votaweb.service.ResultadoVotacaoService;

@RestController
@RequestMapping(value = "/api")
public class ResultadoVotacaoController {

	@Autowired
	private ResultadoVotacaoService resultadoVotacaoService;

	@GetMapping("/v1/resultado-votacao/{pautaId}")
	public ResultadoVotacao resultadoVotos(@PathVariable Long pautaId) {
		VotacaoDto votacaoDto = resultadoVotacaoService.findResultadoVotacaoPorPauta(pautaId);
		ResultadoVotacao resultadoVotacao = new ResultadoVotacao(); 
		resultadoVotacao.setTotal(votacaoDto.getTotal().intValue());
		resultadoVotacao.setVotoSim(votacaoDto.getVotoSim().intValue());
		resultadoVotacao.setVotoNao(votacaoDto.getVotoNao().intValue());
		return resultadoVotacao;
	}

}
