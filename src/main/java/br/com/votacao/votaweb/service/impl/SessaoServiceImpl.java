package br.com.votacao.votaweb.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.votacao.votaweb.model.Sessao;
import br.com.votacao.votaweb.repository.SessaoRepository;
import br.com.votacao.votaweb.service.SessaoService;
import br.com.votacao.votaweb.utils.VotaWebUtils;

@Service
public class SessaoServiceImpl implements SessaoService {

	@Autowired
	SessaoRepository sessaoRepository;

	public Sessao ultimaSessao() {
		return sessaoRepository.findUltimaSessao();
	}

	public boolean isSessaoAberta() {
		Sessao sessaoBanco = ultimaSessao();
		if (sessaoBanco != null) {
			if (VotaWebUtils.convertStringToTimestamp(sessaoBanco.getTerminoSessao())
					.before(VotaWebUtils.convertStringToTimestamp(VotaWebUtils.nowString()))) {
				return true;
			} else {
				return false;
			}
		} else {
			return true;
		}
	}

	@Override
	public Optional<Sessao> findById(long id) {
		return sessaoRepository.findById(id);
	}

	@Override
	public void save(Sessao sessao) {
		sessaoRepository.save(sessao);
	}

	@Override
	public Optional<List<Sessao>> findAll() {
		return Optional.of(sessaoRepository.findAll());
	}

}