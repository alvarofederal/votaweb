package br.com.votacao.votaweb.service;

import java.util.List;
import java.util.Optional;

import br.com.votacao.votaweb.model.Associado;

public interface AssociadoService {
	
	Optional<Associado> findById(long id);

	Associado save(Associado associado);

	Optional<List<Associado>> findAll();
}
