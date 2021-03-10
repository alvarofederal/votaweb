package br.com.votacao.votaweb.controller;

import br.com.votacao.votaweb.model.Votacao;
import br.com.votacao.votaweb.repository.AssociadoRepository;
import br.com.votacao.votaweb.repository.PautaRepository;
import br.com.votacao.votaweb.repository.VotacaoRepository;
import br.com.votacao.votaweb.service.ResultadoVotacaoService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api")
public class VotacaoController {

    private Logger logger = LoggerFactory.getLogger(VotacaoController.class);

    @Autowired
    VotacaoRepository votacaoRepository;

    @Autowired
    PautaRepository pautaRepository;

    @Autowired
    AssociadoRepository associadoRepository;
    
    @Autowired
    ResultadoVotacaoService resultadoVotacaoService;  

    @GetMapping("/v1/votacoes")
    public ResponseEntity<List<Votacao>> listaVotacaos() {
        return ResponseEntity.ok().body(votacaoRepository.findAll());
    }

    @GetMapping("/v1/votacoes/{id}")
    public ResponseEntity<Votacao> buscaPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(votacaoRepository.findById(id).get());
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/v1/votacoes/votacao")
    public ResponseEntity<Votacao> adicionaVotacao(@RequestBody Votacao votacao) {
        Votacao votacaoSalvo = votacaoRepository.save(votacao);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/votacaos").path("/{id}")
                .buildAndExpand(votacaoSalvo.getId()).toUri();
        logger.info("Votação criada com sucesso!");
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

