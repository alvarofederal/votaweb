package br.com.votacao.votaweb.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.votacao.votaweb.model.Associado;
import br.com.votacao.votaweb.service.AssociadoService;
import br.com.votacao.votaweb.service.ValidaCPFService;

@RestController
@RequestMapping(value = "/api")
public class AssociadoController {

	@Autowired
	AssociadoService associadoService;

	@Autowired
	public ValidaCPFService validaCPFService;
	
	@GetMapping("/v1/associados")
	public ResponseEntity<List<Associado>> listaAssociados() {
		return ResponseEntity.ok().body(associadoService.findAll());
	}

    @GetMapping("/v1/associados/{id}")
    public Optional<Associado> buscaPorId(@PathVariable int id) {
        return associadoService.findById(id);
    }

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(path = "/v1/associados/associado")
	public ResponseEntity<?> adicionaAssociado(@RequestBody Associado associado) {
		if (validaCPFService.verificaIntegraçãoCPF(associado.getCpf()))
			new ResponseEntity<>("CPF do associado está inválido!", new HttpHeaders(), HttpStatus.NOT_FOUND);
		Associado associadoBanco = associadoService.findAssociadoCPF(associado.getCpf());
		if (associadoBanco == null) {
			associadoService.save(associado);
			return new ResponseEntity<>("Associado criado com sucesso!", new HttpHeaders(), HttpStatus.CREATED);
		}else {
			return new ResponseEntity<>("Associado já se encontra cadastrado!", new HttpHeaders(), HttpStatus.FOUND);
		}
	}

}
