package br.com.votacao.votaweb.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.votacao.votaweb.model.Pauta;
import br.com.votacao.votaweb.repository.PautaRepository;
import br.com.votacao.votaweb.service.PautaService;

@Service
public class PautaServiceImpl implements PautaService {

	@Autowired
	PautaRepository pautaRepository;

	@Override
	public Optional<Pauta> findById(long id) {
		return pautaRepository.findById(id);
	}

	@Override
	public Pauta save(Pauta pauta) {
		return pautaRepository.save(pauta);
	}

	@Override
	public Optional<List<Pauta>> findAll() {
		return Optional.of(pautaRepository.findAll());
	}
}