package br.com.votacao.votaweb.controller;

import br.com.votacao.votaweb.model.Votacao;
import br.com.votacao.votaweb.repository.AssociadoRepository;
import br.com.votacao.votaweb.repository.PautaRepository;
import br.com.votacao.votaweb.repository.VotacaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/votacoes")
public class VotacaoController {

    private Logger logger = LoggerFactory.getLogger(VotacaoController.class);

    @Autowired
    VotacaoRepository votacaoRepository;

    @Autowired
    PautaRepository pautaRepository;

    @Autowired
    AssociadoRepository associadoRepository;

    @GetMapping("/")
    public ResponseEntity<List<Votacao>> listaVotacaos() {
        return ResponseEntity.ok().body(votacaoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Votacao> buscaPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(votacaoRepository.findById(id).get());
    }

//    @PostMapping(path = "/members")
//    public void addMemberV1(@RequestBody Member member) {
//        //code
//    }
//
//    @PostMapping(path = "/members", produces = MediaType.APPLICATION_JSON_VALUE)
//    public void addMemberV2(@RequestBody Member member) {
//        //code
//    }
//
//    @GetMapping("/home")
//    public String homeInit(Model model) {
//        return "home";
//    }
//
//    @GetMapping("/members/{id}")
//    public String getMembers(Model model) {
//        return "member";
//    }

    @PostMapping(path = "/votacao", consumes = "application/json")
    public ResponseEntity<?> adicionaVotacao(@RequestBody Votacao votacao) {
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

}

