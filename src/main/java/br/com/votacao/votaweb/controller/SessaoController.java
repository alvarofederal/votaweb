package br.com.votacao.votaweb.controller;

import br.com.votacao.votaweb.exception.Messages;
import br.com.votacao.votaweb.model.Sessao;
import br.com.votacao.votaweb.model.Votacao;
import br.com.votacao.votaweb.repository.SessaoRepository;
import br.com.votacao.votaweb.repository.VotacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api/v1/sessoes")
public class SessaoController {

//    Logger logger= (Logger) LoggerFactory.getLogger(SessaoController.class);
    @Autowired
    SessaoRepository sessaoRepository;

    @Autowired
    VotacaoRepository votacaoRepository;

//    @Autowired
//    public SessaoController(SessaoService sessaoService) {
//        this.sessaoService = sessaoService;
//    }

    @GetMapping("/")
    public ResponseEntity<List<Sessao>> listaSessoes() {
        return ResponseEntity.ok().body(sessaoRepository.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Sessao> buscaPorId(@PathVariable Long id) {
        return ResponseEntity.ok().body(sessaoRepository.findById(id).get());
    }

    @PostMapping(consumes = "application/json", produces ="application/json")
    public ResponseEntity<?> adicionaCriar(@RequestBody Sessao sessao) {
        Sessao sessaoSalva = sessaoRepository.save(sessao);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/sessoes").path("/{id}")
                .buildAndExpand(sessaoSalva.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    // Criar uma nova sessão e abrir para votação da pauta
    @RequestMapping(value = "/nova-sessao/{votacaoId}", method = RequestMethod.POST)
    public ResponseEntity<Sessao> abrirSessao(@PathVariable Long votacaoId) {
        Optional<Votacao> votacaoOptional = votacaoRepository.findById(votacaoId);
        Optional<Sessao> sessaoOptional = sessaoRepository.findById(votacaoOptional.get().getSessao().getId());
        if (sessaoOptional.isPresent() || votacaoOptional.isPresent()) {
            Sessao sessao = new Sessao();
            sessao.setInicioSessao(LocalDateTime.now());
            sessao.setTerminoSessao(tempoSessao(sessaoOptional.get().getTerminoSessao(), sessaoOptional.get().getInicioSessao()));
            Sessao sessaoSalva = sessaoRepository.save(sessao);
            return ResponseEntity.ok().body(sessaoRepository.findById(sessaoSalva.getId()).get());
        }
        throw new IllegalArgumentException(Messages.SESSAO_JA_SE_ENCONTRA_ABERTA);
    }

    //Metodo para abrir sessao
    @PutMapping(value="/{id}")
    public ResponseEntity update(@PathVariable("id") long id,
                                 @RequestBody Sessao sessao) {
        return sessaoRepository.findById(id)
                .map(record -> {
                    record.setInicioSessao(sessao.getInicioSessao());
                    Sessao updated = sessaoRepository.save(record);
                    return ResponseEntity.ok().body(updated);
                }).orElse(ResponseEntity.notFound().build());
    }

    private LocalDateTime tempoSessao(LocalDateTime terminoVotacao, LocalDateTime inicioSessao) {
        if (Objects.isNull(terminoVotacao)) {
            return inicioSessao.plusMinutes(5);
        }
        return terminoVotacao;
    }

    public Boolean isSessaoAbertaParaVotar(Votacao votacao) {
        Optional<Votacao> optionalVotacao = votacaoRepository.findById(votacao.getId());
        Optional<Sessao> optionalSessao = sessaoRepository.findById(votacao.getSessao().getId());

        if (optionalVotacao.isPresent()) {
            return LocalDateTime.now().isBefore(optionalSessao.get().getTerminoSessao());
        }
        throw new IllegalArgumentException(Messages.SESSAO_NAO_ENCONTRADA);
    }

    public List<String> doHaveAnOpenSessionThatCanBeClosed() {
        List<Sessao> sessoesAbertas = sessaoRepository.findByMensagemTerminoFalseOrMensagemTermino(null);
        return sessoesAbertas.stream()
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




