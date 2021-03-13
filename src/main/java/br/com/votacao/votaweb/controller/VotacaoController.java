package br.com.votacao.votaweb.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.votacao.votaweb.model.Associado;
import br.com.votacao.votaweb.model.Pauta;
import br.com.votacao.votaweb.model.Sessao;
import br.com.votacao.votaweb.model.Votacao;
import br.com.votacao.votaweb.service.AssociadoService;
import br.com.votacao.votaweb.service.PautaService;
import br.com.votacao.votaweb.service.ResultadoVotacaoService;
import br.com.votacao.votaweb.service.SessaoService;
import br.com.votacao.votaweb.service.ValidaCPFService;
import br.com.votacao.votaweb.service.VotacaoService;

@RestController
@RequestMapping(value = "/api")
public class VotacaoController {

	@Autowired
	public VotacaoService votacaoService;

	@Autowired
	public SessaoService sessaoService;

	@Autowired
	public PautaService pautaService;

	@Autowired
	public AssociadoService associadoService;

	@Autowired
	public ResultadoVotacaoService resultadoVotacaoService;

	@Autowired
	public ValidaCPFService validaCPFService;

	private Votacao votoEfetuado = null;

//	@GetMapping("/v1/votacoes")
//	public ResponseEntity<Optional<List<Votacao>>> listaVotacoes() {
//		return ResponseEntity.ok().body(votacaoService.findAll());
//	}
//
//	@GetMapping("/v1/votacoes/{id}")
//	public ResponseEntity<Votacao> buscaPorId(@PathVariable int id) {
//		return ResponseEntity.ok().body(votacaoService.findById(id).get());
//	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(path = "/v1/votacoes/pauta/{pautaId}/associado/{associadoId}/votar/{voto}")
	public ResponseEntity<String> votar(@PathVariable int pautaId, @PathVariable int associadoId,
			@PathVariable int voto) throws Exception {
		Votacao votacao = new Votacao();
		Sessao sessaoBanco = new Sessao();
		try {
			sessaoBanco = preparaParaVoto(pautaId, associadoId, voto, votacao);
		} catch (Exception e) {
			throw new Exception("Associado não encontrado!");
		}
		return voto(votacao, sessaoBanco);
	}

	private Sessao preparaParaVoto(int pautaId, int associadoId, int voto, Votacao votacao) throws Exception {
		Sessao sessaoBanco;
		buscaPautaValida(pautaId, votacao);
		buscaAssociadoValido(associadoId, votacao);
		validaVinculaVoto(voto, votacao);
		sessaoBanco = sessaoService.findUltimaSessao();
		return sessaoBanco;
	}

	private void buscaAssociadoValido(int associadoId, Votacao votacao) throws Exception {
		Optional<Associado> associadoOptional = null;
		try {
				associadoOptional =associadoService.findById(associadoId);
		}catch (Exception e) {
			throw new Exception("Associado não encontrado!");
		}
		
		if (associadoOptional.get() != null
				&& associadoOptional.get().getCpf() != null
				&& validaCPFService.verificaIntegraçãoCPF(associadoOptional.get().getCpf())) {
			new ResponseEntity<>("CPF do associado está inválido!", new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
		}
		
		if (associadoOptional.get() != null) {
			votacao.setAssociado(associadoOptional.get());
		}
	}

	private ResponseEntity<String> voto(Votacao votacao, Sessao sessaoBanco) {
		if (sessaoService.isSessaoAbertaParaVotacao(sessaoBanco)) {
			this.votoEfetuado = votacaoService.verificarVotoAssociado(votacao);
			// Vincula a ultima sessão aberta a votação já validada
			vinculaUltimaSessaoVotacao(votacao, sessaoBanco);
			return salvarVoto(votacao);
		} else {
			return new ResponseEntity<>("Não pode efetuar voto nessa pauta! Não existe uma sessão aberta!",
					new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
		}
	}

	private ResponseEntity<String> salvarVoto(Votacao votacao) {
		if (votoEfetuado == null) {
			votacaoService.save(votacao);
			return new ResponseEntity<>("Voto efetuado com sucesso!", new HttpHeaders(), HttpStatus.CREATED);
		} else {
			return new ResponseEntity<>("Associado já efetuou seu voto nessa Pauta.", new HttpHeaders(),
					HttpStatus.NOT_ACCEPTABLE);
		}
	}

	private void vinculaUltimaSessaoVotacao(Votacao votacao, Sessao sessaoBanco) {
		votacao.setSessao(sessaoBanco);
	}

	// Metodo para recuperar a pauta em que deseja efetuar o voto
	private void buscaPautaValida(int pautaId, Votacao votacao) {
		Optional<Pauta> pautaOptional = pautaService.findById(pautaId);
		if (!pautaOptional.isPresent()) {
			votacao.setPauta(pautaOptional.get());
		}
	}

	private void validaVinculaVoto(int voto, Votacao votacao) {
		votacao.setVoto(voto);
	}
}
