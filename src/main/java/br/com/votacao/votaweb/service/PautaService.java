package br.com.votacao.votaweb.service;

import java.util.List;
import java.util.Optional;

import br.com.votacao.votaweb.model.Pauta;

public interface PautaService {

	Optional<Pauta> findById(long id);

	Pauta save(Pauta pauta);

	Optional<List<Pauta>> findAll();

}
