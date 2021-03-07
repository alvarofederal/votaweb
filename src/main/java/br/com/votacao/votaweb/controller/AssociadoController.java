package br.com.votacao.votaweb.controller;

import br.com.votacao.votaweb.model.Associado;
import br.com.votacao.votaweb.model.Sessao;
import br.com.votacao.votaweb.model.Votacao;
import br.com.votacao.votaweb.repository.AssociadoRepository;
import br.com.votacao.votaweb.repository.PautaRepository;
import br.com.votacao.votaweb.repository.SessaoRepository;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.logging.Logger;

@RestController
//@RequestMapping("/associados")
@RequestMapping(value = "/api/v1", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class AssociadoController {

    Logger logger= (Logger) LoggerFactory.getLogger(AssociadoRepository.class);

    @Autowired
    AssociadoRepository associadoRepository;

    @GetMapping
    public ResponseEntity<List<Associado>> listaAssociados() {
        return ResponseEntity.ok().body(associadoRepository.findAll());
    }

    @GetMapping("/associados/{id}")
    public ResponseEntity<Associado> buscaPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(associadoRepository.findById(id).get());
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> adicionaVotacao(@RequestBody Associado associado) {
        Associado associadoSalvo = associadoRepository.save(associado);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/associados").path("/{id}")
                .buildAndExpand(associadoSalvo.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

}

