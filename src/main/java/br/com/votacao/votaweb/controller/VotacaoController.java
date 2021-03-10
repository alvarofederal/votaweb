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

import br.com.votacao.votaweb.model.Associado;
import br.com.votacao.votaweb.model.Pauta;
import br.com.votacao.votaweb.model.Sessao;
import br.com.votacao.votaweb.model.Votacao;
import br.com.votacao.votaweb.service.AssociadoService;
import br.com.votacao.votaweb.service.PautaService;
import br.com.votacao.votaweb.service.ResultadoVotacaoService;
import br.com.votacao.votaweb.service.SessaoService;
import br.com.votacao.votaweb.service.VotacaoService;

@RestController
@RequestMapping(value = "/api")
public class VotacaoController {

    private Logger logger = LoggerFactory.getLogger(VotacaoController.class);

    @Autowired
    VotacaoService votacaoService;

    @Autowired
    SessaoService sessaoService;
    
    @Autowired
    PautaService pautaService;

    @Autowired
    AssociadoService associadoService;
    
    @Autowired
    ResultadoVotacaoService resultadoVotacaoService;  

    @GetMapping("/v1/votacoes")
    public ResponseEntity<Optional<List<Votacao>>> listaVotacaos() {
        return ResponseEntity.ok().body(votacaoService.findAll());
    }

    @GetMapping("/v1/votacoes/{id}")
    public ResponseEntity<Votacao> buscaPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(votacaoService.findById(id).get());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/v1/votacoes/sessao/{sessaoId}/pauta/{pautaId}/associado/{associadoId}/votar/{voto}")
    public ResponseEntity<Votacao> votar(@PathVariable Long sessaoId,
							    		@PathVariable Long pautaId,
							    		@PathVariable Long associadoId,
							    		@PathVariable Long voto) {
    	Optional<Sessao> sessaoOptional = sessaoService.findById(sessaoId);
    	Optional<Pauta> pautaOptional = pautaService.findById(pautaId);
    	Optional<Associado> associadoOptional = associadoService.findById(associadoId);
		Sessao sessaoBanco = sessaoService.ultimaSessao();
		Votacao votacao = new Votacao();
		votacao.setSessao(sessaoOptional.get());
		votacao.setPauta(pautaOptional.get());
		votacao.setAssociado(associadoOptional.get());
		if (voto == 1) {
			votacao.setVotoSim(voto);
			votacao.setVotoNao(0L);
		}else {
			votacao.setVotoNao(voto);
			votacao.setVotoSim(0L);
		}
        Votacao votacaoSalvo = votacaoService.save(votacao);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/votacoes").path("/{id}")
                .buildAndExpand(votacaoSalvo.getId()).toUri();
        logger.info("Votação efetuada com sucesso!");
        return ResponseEntity.created(uri).build();
    }

    // Metodo principal para votar na pauta
//    @PostMapping(consumes = "application/json")
//    public ResponseEntity<Votacao> votar(@RequestBody Votacao votacao) {
//        return ResponseEntity.ok(votacaoRepository.votar(votacao));
//    }
    
    @GetMapping("/v1/votacoes/resultado/{id}")
    public ResponseEntity<Votacao> resultadoVotos(@PathVariable Long id){
        return ResponseEntity.ok(this.resultadoVotacaoService.resultadoVotacao(id));
    }
    
}

