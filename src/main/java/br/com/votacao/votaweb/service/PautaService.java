package br.com.votacao.votaweb.service;

import java.util.List;
import java.util.Optional;

import br.com.votacao.votaweb.model.Pauta;

public interface PautaService {

	public Pauta save(Pauta pauta);

	public List<Pauta> findAll();

	public void deletePauta(Pauta pauta);

	public Pauta findUltimaPauta();

	public Optional<Pauta> findById(Integer id);

}
