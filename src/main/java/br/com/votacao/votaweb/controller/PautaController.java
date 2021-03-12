package br.com.votacao.votaweb.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.votacao.votaweb.model.Pauta;
import br.com.votacao.votaweb.repository.PautaRepository;

@RestController
@RequestMapping(value = "/api")
public class PautaController {

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
		pautaRepository.save(pauta);
		return new ResponseEntity<>("Pauta criada com sucesso!", new HttpHeaders(), HttpStatus.CREATED);
	}

}
