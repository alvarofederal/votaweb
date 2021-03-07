package br.com.votacao.votaweb.service;

import br.com.votacao.votaweb.model.Votacao;
import br.com.votacao.votaweb.repository.VotacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VotacaoService {

    @Autowired
    VotacaoRepository votacaoRepository;

    public List<Votacao> findAll() {
        return votacaoRepository.findAll();
    }

    public Optional<Votacao> findById(Long id) {
        return votacaoRepository.findById(id);
    }

    public Votacao save(Votacao votacao) {
        return votacaoRepository.save(votacao);
    }

    public Votacao votar(Long associadoId, Long pautaId, String voto) {
        return votacaoRepository.votar(associadoId, pautaId, voto);
    }
}
