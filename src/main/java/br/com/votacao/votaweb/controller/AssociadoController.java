package br.com.votacao.votaweb.controller;

import java.net.URI;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.votacao.votaweb.model.Associado;
import br.com.votacao.votaweb.repository.AssociadoRepository;

@RestController
@RequestMapping(value = "/api")
public class AssociadoController {

    private Logger logger = LoggerFactory.getLogger(AssociadoController.class);

    @Autowired
    AssociadoRepository associadoRepository;

    @GetMapping("/v1/associados")
    public ResponseEntity<List<Associado>> listaAssociados() {
        return ResponseEntity.ok().body(associadoRepository.findAll());
    }

//    @GetMapping("/v1/associados/{id}")
//    public ResponseEntity<Associado> buscaPorId(@PathVariable Long id) {
//        return ResponseEntity.ok().body(associadoRepository.findById(id).get());
//    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/v1/associados/associado")
    public ResponseEntity<Associado> adicionaAssociado(@RequestBody Associado associado) {
        Associado associadoSalvo = associadoRepository.save(associado);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/associados").path("/{id}")
                .buildAndExpand(associadoSalvo.getId()).toUri();
        logger.info("Associado criado com sucesso!");
        return ResponseEntity.created(uri).build();
    }

}

