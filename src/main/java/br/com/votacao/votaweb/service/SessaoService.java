package br.com.votacao.votaweb.service;

import br.com.votacao.votaweb.exception.Messages;
import br.com.votacao.votaweb.model.Sessao;
import br.com.votacao.votaweb.repository.SessaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SessaoService {

    @Autowired
    SessaoRepository sessaoRepository;

    public List<Sessao> findAll() {
        return sessaoRepository.findAll();
    }

    public Optional<Sessao> findById(Long id) {
        return sessaoRepository.findById(id);
    }

    public Sessao save(Sessao sessao) {
        return sessaoRepository.save(sessao);
    }

    public Sessao abrirSessao(Long sessaoId, Sessao sessao) {

        if (sessao != null) {

            return sessaoRepository.abrirSessao(sessaoId, sessao.getInicioVotacao());
        }
        throw new IllegalArgumentException(Messages.SESSAO_JA_SE_ENCONTRA_ABERTA);
    }
}
