package br.com.votacao.votaweb.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.votacao.votaweb.model.Votacao;
import br.com.votacao.votaweb.service.ResultadoVotacaoService;

@RestController
@RequestMapping(value = "/api")
public class ResultadoVotacaoController {

	@Autowired
	private ResultadoVotacaoService resultadoVotacaoService;

	@GetMapping("/v1/resultado-votacao")
	public ResponseEntity<Optional<List<Votacao>>> resultadoVotos(@PathVariable Long id) {
		return ResponseEntity.ok(this.resultadoVotacaoService.resultadoVotacao(id));
	}

}
