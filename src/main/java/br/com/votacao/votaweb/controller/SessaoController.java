package br.com.votacao.votaweb.controller;

import br.com.votacao.votaweb.exception.Messages;
import br.com.votacao.votaweb.model.Sessao;
import br.com.votacao.votaweb.model.Votacao;
import br.com.votacao.votaweb.repository.SessaoRepository;
import br.com.votacao.votaweb.repository.VotacaoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping(value = "/api")
public class SessaoController {

    private Logger logger = LoggerFactory.getLogger(SessaoController.class);

    @Autowired
    SessaoRepository sessaoRepository;

    @Autowired
    VotacaoRepository votacaoRepository;

    /**
     * Versionamento de API pelo Header 'X-API-Version', define versão 'v2'.
     *
//     * @param nome
     * @return ResponseEntity<Response<String>>
     */
    @GetMapping(value = "/v1/sessoes", headers = "X-API-Version=v1")
    public ResponseEntity<List<Sessao>> listaSessoesV1() {
        return ResponseEntity.ok().body(sessaoRepository.findAll());
    }

    @GetMapping(value = "/v2/sessoes", headers = "X-API-Version=v2")
    public ResponseEntity<List<Sessao>> listaSessoesV2() {
        return ResponseEntity.ok().body(sessaoRepository.findAll());
    }

    @GetMapping(value = "/v1/sessoes/{id}", headers = "X-API-Version=v1")
    public ResponseEntity<Sessao> buscaPorIdV1(@PathVariable Long id) {
        return ResponseEntity.ok().body(sessaoRepository.findById(id).get());
    }

    @GetMapping(value = "/v2/sessoes/{id}", headers = "X-API-Version=v2")
    public ResponseEntity<Sessao> buscaPorIdV2(@PathVariable Long id) {
        return ResponseEntity.ok().body(sessaoRepository.findById(id).get());
    }

    @PostMapping(value = "/v1/sessoes/nova-sessao", headers = "X-API-Version=v1")
    public ResponseEntity<Sessao> abrirSessaoDefault() {
        Sessao sessao = new Sessao();
        sessao.setInicioSessao(LocalDateTime.now());
        sessao.setTerminoSessao(sessao.getInicioSessao().plusMinutes(1));
        sessao.setMensagemTermino(true);
        if (isSessaoAberta(sessao)){
            Sessao sessaoSalva = sessaoRepository.save(sessao);
            logger.info("Sessão aberta por padrão, por 1 mínuto!");
            return ResponseEntity.ok().body(sessaoRepository.findById(sessaoSalva.getId()).get());
        }
        throw new IllegalArgumentException(Messages.SE_ENCONTRA_ABERTA_UMA_SESSAO);
    }

    // Abrindo sessão, com tempo especificado pelo usuário, para votação de pauta
    @PostMapping(value = "/v1/sessoes/nova-sessao/{tempoSessao}", headers = "X-API-Version=v1")
    public ResponseEntity<Sessao> abrirSessao(@PathVariable Long tempoSessao) {
            Sessao sessao = new Sessao();
            sessao.setInicioSessao(LocalDateTime.now());
            sessao.setTerminoSessao(sessao.getInicioSessao().plusMinutes(tempoSessao));
            sessao.setMensagemTermino(true);
        if (isSessaoAberta(sessao)){
            Sessao sessaoSalva = sessaoRepository.save(sessao);
            logger.info("Sessão aberta com sucesso por " + tempoSessao + " minutos para expiração!");
            return ResponseEntity.ok().body(sessaoRepository.findById(sessaoSalva.getId()).get());
        }
        throw new IllegalArgumentException(Messages.SE_ENCONTRA_ABERTA_UMA_SESSAO);
    }

    @PostMapping(value = "/v2/sessoes/nova-sessao", headers = "X-API-Version=v2")
    public ResponseEntity<?> adicionaCriar(@RequestBody Sessao sessao) {
        Sessao sessaoSalva = sessaoRepository.save(sessao);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/sessoes").path("/{id}")
                .buildAndExpand(sessaoSalva.getId()).toUri();
        logger.info("Votação criada com sucesso!");
        return ResponseEntity.created(uri).build();
    }

    //Metodo para abrir sessao
    @PutMapping(value="/v1/sessoes/{id}", headers = "X-API-Version=v1")
    public ResponseEntity update(@PathVariable("id") long id,
                                 @RequestBody Sessao sessao) {
        logger.info("Sessão atualizada com sucesso!");
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
        logger.info("Existe uma sessão aberta!");
        throw new IllegalArgumentException(Messages.SESSAO_NAO_ENCONTRADA);
    }

    public Boolean isSessaoAberta(Sessao sessao) {
        if (sessao != null){
            LocalDateTime str = sessao.getTerminoSessao();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String formattedDateTime = str.format(formatter);
            LocalDateTime dateTime = LocalDateTime.parse(formattedDateTime, formatter);
            DateTimeFormatter dTF2 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            System.out.println(" formats as " + dTF2.format(str));
            List<Sessao> sessaoBanco = sessaoRepository.verificaELiberaSessao(dTF2.format(str));
            if (sessaoBanco.size() > 0 && sessaoBanco.get(0).getTerminoSessao() != null) {
                return LocalDateTime.now().isBefore(sessaoBanco.get(0).getTerminoSessao());
            }else{
                return false;
            }
        }
        logger.info("Existe uma sessão aberta!");
        throw new IllegalArgumentException(Messages.SE_ENCONTRA_ABERTA_UMA_SESSAO);
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




