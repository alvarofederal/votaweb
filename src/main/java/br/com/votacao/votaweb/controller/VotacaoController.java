package br.com.votacao.votaweb.controller;

import java.net.URI;
import java.util.List;

import br.com.votacao.votaweb.model.Pauta;
import br.com.votacao.votaweb.model.Votacao;
import br.com.votacao.votaweb.repository.AssociadoRepository;
import br.com.votacao.votaweb.repository.PautaRepository;
import br.com.votacao.votaweb.repository.VotacaoRepository;
import br.com.votacao.votaweb.service.VotacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
//@RequestMapping("/votacoes")
@RequestMapping(value = "/api/v1", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class VotacaoController {

    @Autowired
    VotacaoService votacaoService;

    @Autowired
    PautaRepository pautaRepository;

    @Autowired
    AssociadoRepository associadoRepository;

    @GetMapping
    public ResponseEntity<List<Votacao>> listaVotacaos() {
        return ResponseEntity.ok().body(votacaoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Votacao> buscaPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(votacaoService.findById(id).get());
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> adicionaVotacao(@RequestBody Votacao votacao) {
        Votacao votacaoSalvo = votacaoService.save(votacao);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/votacaos").path("/{id}")
                .buildAndExpand(votacaoSalvo.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @RequestMapping(value = "/votacao/{id}/sessao", method = RequestMethod.POST)
    public ResponseEntity<Votacao> votar(@PathVariable Long associadoId,@PathVariable Long pautaId, @RequestBody String voto) {
        return ResponseEntity.ok(votacaoService.votar(associadoId, pautaId, voto));
    }

}

