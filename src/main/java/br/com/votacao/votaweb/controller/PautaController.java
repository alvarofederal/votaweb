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

import br.com.votacao.votaweb.model.Pauta;
import br.com.votacao.votaweb.repository.PautaRepository;

@RestController
@RequestMapping(value = "/api")
public class PautaController {

    private Logger logger = LoggerFactory.getLogger(PautaController.class);

    @Autowired
    private PautaRepository pautaRepository;

    @GetMapping("/v1/pautas")
    public ResponseEntity<List<Pauta>> listaPautas() {
        return ResponseEntity.ok().body(pautaRepository.findAll());
    }

//    @GetMapping("/v1/pautas/{id}")
//    public ResponseEntity<Pauta> buscaPorId(@PathVariable Long id) {
//        return ResponseEntity.ok().body(pautaRepository.findById(id).get());
//    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(path = "/v1/pautas/pauta")
    public ResponseEntity<?> cadastraPauta(@RequestBody Pauta pauta) {
        Pauta pautaSalva = pautaRepository.save(pauta);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/pautas").path("/{id}")
                .buildAndExpand(pautaSalva.getId()).toUri();
        logger.info("Pauta criada com sucesso!");
        return ResponseEntity.created(uri).build();
    }

}
