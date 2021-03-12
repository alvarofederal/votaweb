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
//	public ResponseEntity<Votacao> buscaPorId(@PathVariable Long id) {
//		return ResponseEntity.ok().body(votacaoService.findById(id).get());
//	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(path = "/v1/votacoes/pauta/{pautaId}/associado/{associadoId}/votar/{voto}")
	public ResponseEntity<String> votar(@PathVariable Long pautaId, @PathVariable Long associadoId,
			@PathVariable Long voto) {
		Votacao votacao = new Votacao();
		Sessao sessaoBanco = new Sessao();

		buscaPautaValida(pautaId, votacao);
		buscaAssociadoValido(associadoId, votacao);
		validaVotoAssociadoPauta(voto, votacao);
		sessaoBanco = sessaoService.ultimaSessao();
		if (sessaoService.isSessaoAbertaParaVotacao(sessaoBanco)) {
			this.votoEfetuado = votacaoService.verificarVotoAssociado(votacao);
			// Vincula a ultima sessão aberta a votação já validada
			vinculaUltimaSessaoVotacao(votacao, sessaoBanco);
			if (votoEfetuado == null) {
				votacaoService.save(votacao);
				return new ResponseEntity<>("Voto efetuado com sucesso!", new HttpHeaders(), HttpStatus.CREATED);
			} else {
				return new ResponseEntity<>("Associado já efetuou seu voto nessa Pauta.", new HttpHeaders(),
						HttpStatus.NOT_ACCEPTABLE);
			}
		} else {
			return new ResponseEntity<>("Não pode efetuar voto nessa pauta! Não existe uma sessão aberta!",
					new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
		}
	}

	private void vinculaUltimaSessaoVotacao(Votacao votacao, Sessao sessaoBanco) {
		votacao.setSessao(sessaoBanco);
	}

	// Metodo para recuperar a pauta em que deseja efetuar o voto
	private void buscaPautaValida(Long pautaId, Votacao votacao) {
		Optional<Pauta> pautaOptional = pautaService.findById(pautaId);
		if (pautaOptional != null) {
			votacao.setPauta(pautaOptional.get());
		}
	}

	// Metodo para recuperar a associado que deseja efetuar o voto na pauta
	private void buscaAssociadoValido(Long associadoId, Votacao votacao) {
		Optional<Associado> associadoOptional = associadoService.findById(associadoId);
		if (validaCPFService.verificaIntegraçãoCPF(associadoOptional.get().getCpf()))
			new ResponseEntity<>("CPF do associado está inválido!", new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
		if (associadoOptional != null) {
			votacao.setAssociado(associadoOptional.get());
		}
	}

	private void validaVotoAssociadoPauta(Long voto, Votacao votacao) {
		if (voto == 1) {
			votacao.setVotoSim(voto);
			votacao.setVotoNao(0L);
		} else {
			votacao.setVotoNao(voto);
			votacao.setVotoSim(0L);
		}
	}
}
