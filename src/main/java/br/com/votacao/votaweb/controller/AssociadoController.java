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

import br.com.votacao.votaweb.model.Associado;
import br.com.votacao.votaweb.repository.AssociadoRepository;

@RestController
@RequestMapping(value = "/api")
public class AssociadoController {

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
	public ResponseEntity<?> adicionaAssociado(@RequestBody Associado associado) {
		associadoRepository.save(associado);
		return new ResponseEntity<>("Associado criado com sucesso!", new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE);
	}

}
