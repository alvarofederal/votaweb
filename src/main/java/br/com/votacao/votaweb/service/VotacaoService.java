package br.com.votacao.votaweb.service;

import br.com.votacao.votaweb.exception.FechaSessaoException;
import br.com.votacao.votaweb.exception.Messages;
import br.com.votacao.votaweb.model.Votacao;
import br.com.votacao.votaweb.repository.SessaoRepository;
import br.com.votacao.votaweb.repository.VotacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VotacaoService {

    @Autowired
    VotacaoRepository votacaoRepository;

    @Autowired
    SessaoService sessaoService;

    public List<Votacao> findAll() {
        return votacaoRepository.findAll();
    }

    public Optional<Votacao> findById(Long id) {
        return votacaoRepository.findById(id);
    }

    public Votacao save(Votacao votacao) {
        return votacaoRepository.save(votacao);
    }

    public Votacao votar(Votacao votacao) {
            Optional<Votacao> optionalVotacao = votacaoRepository.findById(votacao.getId());
            if (optionalVotacao.isPresent()) {
                if (sessaoService.isSessaoAbertaParaVotar(optionalVotacao.get())) {
                    return executeVote(optionalVotacao.get().getAssociado().getId(), optionalVotacao.get().getPauta().getId(), votacao.getVotoSim(), votacao.getVotoNao());
                }
                throw new FechaSessaoException(Messages.FECHA_SESSAO);
            }
            throw new IllegalArgumentException(Messages.THE_TOPIC_VOTING_NOT_EXISTS);
    }

    private Votacao executeVote(Long associadoId, Long pautaId, Long votoSim, Long votoNao) {
        return votacaoRepository.votar(associadoId, pautaId, votoSim, votoNao);
    }

//    public String recuperaVotos(Long votacaoId) {
//
//        return  countSim, countNao;
//    }
}
