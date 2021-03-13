package br.com.votacao.votaweb.service;

import java.util.List;
import java.util.Optional;

import br.com.votacao.votaweb.model.Associado;

public interface AssociadoService {
	
	public Optional<Associado> findById(int id);

	public Associado save(Associado associado);

	public List<Associado> findAll();

	public Associado findAssociadoCPF(String cpf);
	
	public void deleteAssociado(Associado associado);

	public Associado findUltimoAssociado();

}
