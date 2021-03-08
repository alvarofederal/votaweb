package br.com.votacao.votaweb.controller;

import br.com.votacao.votaweb.model.Pauta;
import br.com.votacao.votaweb.repository.PautaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/pautas")
public class PautaController {

//    Logger logger= (Logger) LoggerFactory.getLogger(PautaController.class);

    @Autowired
    private PautaRepository pautaRepository;

    @GetMapping("/")
    public ResponseEntity<List<Pauta>> listaPautas() {
        return ResponseEntity.ok().body(pautaRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pauta> buscaPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(pautaRepository.findById(id).get());
    }

    @PostMapping(consumes = "application/json", produces ="application/json")
    public ResponseEntity<?> adicionaPauta(@RequestBody Pauta pauta) {
        Pauta pautaSalva = pautaRepository.save(pauta);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/pautas").path("/{id}")
                .buildAndExpand(pautaSalva.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

}
