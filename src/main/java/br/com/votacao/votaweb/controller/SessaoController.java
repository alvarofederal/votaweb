package br.com.votacao.votaweb.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.votacao.votaweb.model.Sessao;
import br.com.votacao.votaweb.service.SessaoService;
import br.com.votacao.votaweb.utils.VotaWebUtils;
import io.swagger.annotations.Api;

@RestController
@Api(value = "sessao")
@RequestMapping(value = "/api", produces = "application/json")
public class SessaoController {

	@Autowired
	private SessaoService sessaoService;

	@GetMapping(value = "/v1/sessoes")
	public ResponseEntity<Optional<List<Sessao>>> listaSessoesV1() {
		return ResponseEntity.ok().body(sessaoService.findAll());
	}

	@GetMapping(value = "/v2/sessoes")
	public ResponseEntity<Optional<List<Sessao>>> listaSessoesV2() {
		return ResponseEntity.ok().body(sessaoService.findAll());
	}

	@GetMapping(value = "/v1/sessoes/{id}")
	public ResponseEntity<Sessao> buscaPorIdV1(@PathVariable Long id) {
		return ResponseEntity.ok().body(sessaoService.findById(id).get());
	}

	@GetMapping(value = "/v2/sessoes/{id}")
	public ResponseEntity<Sessao> buscaPorIdV2(@PathVariable Long id) {
		return ResponseEntity.ok().body(sessaoService.findById(id).get());
	}

	@PostMapping(value = "/v1/sessoes/nova-sessao")
	public ResponseEntity<?> abrirSessaoDefault() {
		try {
			if (sessaoService.isSessaoAberta()) {
				Sessao sessao = new Sessao();
				String stringTimestamp = VotaWebUtils.nowString();
				sessao.setInicioSessao(stringTimestamp);
				sessao.setTerminoSessao(VotaWebUtils.format(stringTimestamp, 1L));
				sessao.setMensagemTermino(true);
				sessaoService.save(sessao);
				return new ResponseEntity<>("Sessão aberta por padrão, por 1 minuto! Efetue seu voto como associado!",
						new HttpHeaders(), HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>("Existe uma sessão aberta! Aproveite para efetuar seu voto como associado!",
						new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	// Abrindo sessão, com tempo especificado pelo usuário, para votação de pauta
	@PostMapping(value = "/v1/sessoes/nova-sessao/{tempoSessao}")
	public ResponseEntity<?> abrirSessao(@PathVariable Long tempoSessao) {
		try {
			if (sessaoService.isSessaoAberta()) {
				Sessao sessao = new Sessao();
				String stringTimestamp = VotaWebUtils.nowString();
				sessao.setInicioSessao(stringTimestamp);
				sessao.setMensagemTermino(true);
				sessao.setTerminoSessao(VotaWebUtils.format(stringTimestamp, tempoSessao));
				sessaoService.save(sessao);
				return new ResponseEntity<>("Sessão aberta! Efetue seu voto como Associado!", new HttpHeaders(),
						HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>("Existe uma sessão aberta! Aproveite para efetuar seu voto como associado!",
						new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

}
