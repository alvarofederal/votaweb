package br.com.votacao.votaweb.controller;

import br.com.votacao.votaweb.model.Associado;
import br.com.votacao.votaweb.model.Pauta;
import br.com.votacao.votaweb.model.Votacao;
import br.com.votacao.votaweb.repository.AssociadoRepository;
import br.com.votacao.votaweb.repository.PautaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/pautas")
public class PautaController {

    @Autowired
    PautaRepository pautaRepository;

    @GetMapping
    public ResponseEntity<List<Pauta>> listaPautas() {
        return ResponseEntity.ok().body(pautaRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pauta> buscaPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(pautaRepository.findById(id).get());
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<?> adicionaPauta(@RequestBody Pauta pauta) {
        Pauta pautaSalvo = pautaRepository.save(pauta);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/pautas").path("/{id}")
                .buildAndExpand(pautaSalvo.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

}
