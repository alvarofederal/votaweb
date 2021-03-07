package br.com.votacao.votaweb.controller;

import br.com.votacao.votaweb.model.Sessao;
import br.com.votacao.votaweb.model.Votacao;
import br.com.votacao.votaweb.service.SessaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/sessoes")
@RequestMapping(value = "/api/v1", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
public class SessaoController {

    @Autowired
    SessaoService sessaoService;

    @GetMapping
    public ResponseEntity<List<Sessao>> listaSessoes() {
        return ResponseEntity.ok().body(sessaoService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sessao> buscaPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(sessaoService.findById(id).get());
    }

    @RequestMapping(value = "/sessoes/{sessaoId}/abrir-sessao", method = RequestMethod.POST)
    public ResponseEntity<Sessao> abrirSessao(@PathVariable Long sessaoId, @RequestBody Sessao sessao) {
        return ResponseEntity.ok(sessaoService.abrirSessao(sessaoId, sessao));
    }

    //Metodo para abrir sessao
    @PutMapping(value="/sessoes/{id}")
    public ResponseEntity update(@PathVariable("id") long id,
                                 @RequestBody Sessao sessao) {
        return sessaoService.findById(id)
                .map(record -> {
                    record.setInicioVotacao(sessao.getInicioVotacao());
                    Sessao updated = sessaoService.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }
}




