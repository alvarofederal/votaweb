package br.com.votacao.votaweb.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.votacao.votaweb.model.Associado;
import br.com.votacao.votaweb.repository.AssociadoRepository;
import br.com.votacao.votaweb.service.AssociadoService;

@Service
public class AssociadoServiceImpl implements AssociadoService {

		@Autowired
		AssociadoRepository associadoRepository;

		@Override
		public Optional<Associado> findById(int id) {
			return associadoRepository.findById(id);
		}

		@Override
		public Associado save(Associado pauta) {
			return associadoRepository.save(pauta);
		}

		@Override
		public List<Associado> findAll() {
			return associadoRepository.findAll();
		}

		@Override
		public Associado findAssociadoCPF(String cpf) {
			return associadoRepository.findAssociadoCPF(cpf);
		}

		@Override
		public Associado findUltimoAssociado() {
			return associadoRepository.findUltimoAssociado();
		}

		@Override
		public void deleteAssociado(Associado associado) {
			associadoRepository.delete(associado);			
		}
}
