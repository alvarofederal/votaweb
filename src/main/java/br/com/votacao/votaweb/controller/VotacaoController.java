package br.com.votacao.votaweb.controller;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.votacao.votaweb.exception.CpfInvalidoException;
import br.com.votacao.votaweb.exception.SessaoExistenteException;
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

	private Logger logger = LoggerFactory.getLogger(VotacaoController.class);

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

	@GetMapping("/v1/votacoes")
	public ResponseEntity<Optional<List<Votacao>>> listaVotacaos() {
		return ResponseEntity.ok().body(votacaoService.findAll());
	}

	@GetMapping("/v1/votacoes/{id}")
	public ResponseEntity<Votacao> buscaPorId(@PathVariable Long id) {
		return ResponseEntity.ok().body(votacaoService.findById(id).get());
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(path = "/v1/votacoes/pauta/{pautaId}/associado/{associadoId}/votar/{voto}")
	public void votar(@PathVariable Long pautaId, @PathVariable Long associadoId, @PathVariable Long voto) {
		Votacao votacaoSalvo = new Votacao();
		Votacao votacao = new Votacao();
		Sessao sessaoBanco = sessaoService.ultimaSessao();

		buscaPautaValida(pautaId, votacao);

		buscaAssociadoValido(associadoId, votacao);

		// Metodo responsável por validar os votos selecionados pelos Associados
		validaVotoAssociadoPauta(voto, votacao);

		// Verifica a condição da sessão(Aberta ou Fechada), pava o voto de um associado
		if (sessaoService.isSessaoAbertaParaVotacao()) {
			this.votoEfetuado = votacaoService.verificarVotoAssociado(votacao);
			if (votoEfetuado == null) {
				votacaoSalvo = votacaoService.save(votacao);
				logger.info("Votação efetuada com sucesso!");
			} else {
				throw new CpfInvalidoException("Associado já efetuou seu voto nessa Pauta.");
			}
		} else {
			throw new SessaoExistenteException(
					"Não pode efetuar esse voto nessa pauta, pois não existe uma sessão aberta!");
		}

	}

	// Metodo para recuperar a pauta em que deseja efetuar o voto
	private void buscaPautaValida(Long pautaId, Votacao votacao) {
		Optional<Pauta> pautaOptional = pautaService.findById(pautaId);
		votacao.setPauta(pautaOptional.get());
	}

	// Metodo para recuperar a associado que deseja efetuar o voto na pauta
	private void buscaAssociadoValido(Long associadoId, Votacao votacao) {
		Optional<Associado> associadoOptional = associadoService.findById(associadoId);

		if (validaCPFService.verificaIntegraçãoCPF(associadoOptional.get().getCpf()))
			throw new CpfInvalidoException("CPF so associado está invalido.");

		votacao.setAssociado(associadoOptional.get());
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

	// Metodo principal para votar na pauta
//    @PostMapping(consumes = "application/json")
//    public ResponseEntity<Votacao> votar(@RequestBody Votacao votacao) {
//        return ResponseEntity.ok(votacaoRepository.votar(votacao));
//    }

	@GetMapping("/v1/votacoes/resultado/{id}")
	public ResponseEntity<Votacao> resultadoVotos(@PathVariable Long id) {
		return ResponseEntity.ok(this.resultadoVotacaoService.resultadoVotacao(id));
	}

}
