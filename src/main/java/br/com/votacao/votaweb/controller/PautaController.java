package br.com.votacao.votaweb.controller;

import br.com.votacao.votaweb.model.Associado;
import br.com.votacao.votaweb.model.Pauta;
import br.com.votacao.votaweb.model.Votacao;
import br.com.votacao.votaweb.repository.AssociadoRepository;
import br.com.votacao.votaweb.repository.PautaRepository;
import br.com.votacao.votaweb.service.SessaoService;
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
//@RequestMapping("/pautas")
@RequestMapping(value = "/api/v1", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class PautaController {

    Logger logger= (Logger) LoggerFactory.getLogger(PautaRepository.class);

    @Autowired
    PautaRepository pautaRepository;

    @GetMapping
    public ResponseEntity<List<Pauta>> listaPautas() {
        return ResponseEntity.ok().body(pautaRepository.findAll());
    }

    @GetMapping("/pautas/{id}")
    public ResponseEntity<Pauta> buscaPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(pautaRepository.findById(id).get());
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> adicionaPauta(@RequestBody Pauta pauta) {
        Pauta pautaSalva = pautaRepository.save(pauta);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/pautas").path("/{id}")
                .buildAndExpand(pautaSalva.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

}
