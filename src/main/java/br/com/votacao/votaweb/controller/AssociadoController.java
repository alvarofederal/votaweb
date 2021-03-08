package br.com.votacao.votaweb.controller;

import br.com.votacao.votaweb.model.Associado;
import br.com.votacao.votaweb.repository.AssociadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/associados")
public class AssociadoController {

//    Logger logger= (Logger) LoggerFactory.getLogger(AssociadoController.class);

    @Autowired
    AssociadoRepository associadoRepository;

    @GetMapping("/")
    public ResponseEntity<List<Associado>> listaAssociados() {
        return ResponseEntity.ok().body(associadoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Associado> buscaPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(associadoRepository.findById(id).get());
    }

    @PostMapping(consumes = "application/json", produces ="application/json")
    public ResponseEntity<Associado> adicionaVotacao(@RequestBody Associado associado) {
        Associado associadoSalvo = associadoRepository.save(associado);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/associados").path("/{id}")
                .buildAndExpand(associadoSalvo.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

}

