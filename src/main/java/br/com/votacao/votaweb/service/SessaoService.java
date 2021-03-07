package br.com.votacao.votaweb.service;

import br.com.votacao.votaweb.exception.Messages;
import br.com.votacao.votaweb.model.Sessao;
import br.com.votacao.votaweb.model.Votacao;
import br.com.votacao.votaweb.repository.SessaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

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

    public Sessao abrirSessao(Long sessaoId) {
        Optional<Sessao> sessaoOptional = sessaoRepository.findById(sessaoId);
        if (sessaoOptional.isPresent()) {
            Sessao sessao = new Sessao();
            sessao.setInicioSessao(LocalDateTime.now());
            sessao.setTerminoSessao(tempoSessao(sessao.getTerminoSessao(), sessao.getInicioSessao()));
            return sessaoRepository.abrirSessao(sessaoId, sessao.getInicioSessao());
        }
        throw new IllegalArgumentException(Messages.SESSAO_JA_SE_ENCONTRA_ABERTA);
    }

    private LocalDateTime tempoSessao(LocalDateTime terminoVotacao, LocalDateTime inicioSessao) {
        if (Objects.isNull(terminoVotacao)) {
            return inicioSessao.plusMinutes(5);
        }
        return terminoVotacao;
    }

    public Boolean isSessaoAbertaParaVotar(Votacao votacao) {
        Optional<Sessao> optionalSessao = sessaoRepository.findByVotacao(votacao);
        if (optionalSessao.isPresent()) {
            return LocalDateTime.now().isBefore(optionalSessao.get().getTerminoSessao());
        }
        throw new IllegalArgumentException(Messages.SESSAO_NAO_ENCONTRADA);
    }

    public List<String> doHaveAnOpenSessionThatCanBeClosed() {
        List<Sessao> sessionsOpen = sessaoRepository.findByProduceMessageFalseOrProduceMessage(null);
        return sessionsOpen.stream()
                .filter(sessao -> sessao.getTerminoSessao().isBefore(LocalDateTime.now()))
                .map(sessao -> saveAndReturnTopicVotingDescription(sessao))
                .collect(Collectors.toList());
    }

    private String saveAndReturnTopicVotingDescription(Sessao sessao) {
        sessao.setMensagemTermino(Boolean.TRUE);
        Sessao sessaoSalva = sessaoRepository.save(sessao);
        return sessaoSalva.getTerminoSessao().toString();
    }
}
