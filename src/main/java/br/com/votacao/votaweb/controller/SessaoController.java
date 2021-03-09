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
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api")
public class SessaoController {

    private Logger logger = LoggerFactory.getLogger(SessaoController.class);

    @Autowired
    SessaoRepository sessaoRepository;

    @Autowired
    VotacaoRepository votacaoRepository;

    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    /**
     * Versionamento de API pelo Header 'X-API-Version', define versão 'v2'.
     *
//     * @param nome
     * @return ResponseEntity<Response<String>>
     */
    @GetMapping(value = "/v1/sessoes")
    public ResponseEntity<List<Sessao>> listaSessoesV1() {
        return ResponseEntity.ok().body(sessaoRepository.findAll());
    }

    @GetMapping(value = "/v2/sessoes")
    public ResponseEntity<List<Sessao>> listaSessoesV2() {
        return ResponseEntity.ok().body(sessaoRepository.findAll());
    }

    @GetMapping(value = "/v1/sessoes/{id}")
    public ResponseEntity<Sessao> buscaPorIdV1(@PathVariable Long id) {
        return ResponseEntity.ok().body(sessaoRepository.findById(id).get());
    }

    @GetMapping(value = "/v2/sessoes/{id}")
    public ResponseEntity<Sessao> buscaPorIdV2(@PathVariable Long id) {
        return ResponseEntity.ok().body(sessaoRepository.findById(id).get());
    }

    @PostMapping(value = "/v1/sessoes/nova-sessao")
    public ResponseEntity<Sessao> abrirSessaoDefault() {
        Sessao sessao = new Sessao();
        sessao.setInicioSessao(now());
        sessao.setTerminoSessao(convertUtilDateToSqlDate(addHoursToJavaUtilDate(now(), 1L)));
        sessao.setMensagemTermino(true);
        if (!isSessaoAberta(sessao)){
            Sessao sessaoSalva = sessaoRepository.save(sessao);
            logger.info("Sessão aberta por padrão, por 1 mínuto!");
            return ResponseEntity.ok().body(sessaoRepository.findById(sessaoSalva.getId()).get());
        }
        throw new IllegalArgumentException(Messages.SE_ENCONTRA_ABERTA_UMA_SESSAO);
    }

    // Abrindo sessão, com tempo especificado pelo usuário, para votação de pauta
    @PostMapping(value = "/v1/sessoes/nova-sessao/{tempoSessao}")
    public ResponseEntity<Sessao> abrirSessao(@PathVariable Long tempoSessao) {
            Sessao sessao = new Sessao();
            sessao.setInicioSessao(now());
            sessao.setTerminoSessao(convertUtilDateToSqlDate(addHoursToJavaUtilDate(now(),tempoSessao)));
            sessao.setMensagemTermino(true);
        if (!isSessaoAberta(sessao)){
            Sessao sessaoSalva = sessaoRepository.save(sessao);
            logger.info("Sessão aberta com sucesso por " + tempoSessao + " minutos para expiração!");
            return ResponseEntity.ok().body(sessaoRepository.findById(sessaoSalva.getId()).get());
        }
        throw new IllegalArgumentException(Messages.SE_ENCONTRA_ABERTA_UMA_SESSAO);
    }

    @PostMapping(value = "/v2/sessoes/nova-sessao")
    public ResponseEntity<?> adicionaCriar(@RequestBody Sessao sessao) {
        Sessao sessaoSalva = sessaoRepository.save(sessao);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().replacePath("/sessoes").path("/{id}")
                .buildAndExpand(sessaoSalva.getId()).toUri();
        logger.info("Votação criada com sucesso!");
        return ResponseEntity.created(uri).build();
    }

    //Metodo para abrir sessao
    @PutMapping(value="/v1/sessoes/{id}")
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
            return now().before(optionalSessao.get().getTerminoSessao());
        }
        logger.info("Existe uma sessão aberta!");
        throw new IllegalArgumentException(Messages.SESSAO_NAO_ENCONTRADA);
    }

    public Boolean isSessaoAberta(Sessao sessao) {
        if (sessao != null){
            List<Sessao> sessaoBanco = sessaoRepository.findAllWithCreationDateTimeBefore(sessao.getTerminoSessao());

            for (Sessao sessaoVerificada: sessaoBanco) {
                if (sessaoVerificada != null){
                    if (now().before(sessaoVerificada.getTerminoSessao())){
                        return true;
                    }else {
                        return false;
                    }
                }
            }
        }
        logger.info("Existe uma sessão aberta!");
        throw new IllegalArgumentException(Messages.SE_ENCONTRA_ABERTA_UMA_SESSAO);
    }

//    public List<String> doHaveAnOpenSessionThatCanBeClosed() {
//        List<Sessao> sessoesAbertas = sessaoRepository.findByMensagemTerminoFalseOrMensagemTermino(null);
//        Calendar calendar = Calendar.getInstance();
//        java.util.Date currentDate = calendar.getTime();
//        Date date = new Date(currentDate.getTime());
//        return sessoesAbertas.stream()
//                .filter(sessao -> sessao.getTerminoSessao().before(LocalDateTime.now()))
//                .map(sessao -> saveAndReturnTopicVotingDescription(sessao))
//                .collect(Collectors.toList());
//    }

    private String saveAndReturnTopicVotingDescription(Sessao sessao) {
        sessao.setMensagemTermino(Boolean.TRUE);
        Sessao sessaoSalva = sessaoRepository.save(sessao);
        return sessaoSalva.getTerminoSessao().toString();
    }

    public String convertToDatabaseColumn(LocalDateTime value) {
        return (value != null) ? value.format(dateTimeFormatter) : null;
    }

    public LocalDateTime convertToEntityAttribute(String value) {
        return convertLocalDateTime(value);
    }

    private LocalDateTime convertLocalDateTime(String value) {
        try {
            return (value != null) ? LocalDateTime.parse(value, dateTimeFormatter) : null;
        } catch (DateTimeException e) {
            return null;
        }
    }

    public java.util.Date addHoursToJavaUtilDate(java.util.Date date, Long hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, converLongToInt(hours));
        return calendar.getTime();
    }

    public java.sql.Date convertUtilDateToSqlDate(java.util.Date dataUtil){
        Date dataSql = new Date(dataUtil.getTime());
        return dataSql;
    }

    private java.util.Date convertSqlDateToUtilDate(java.sql.Date dateSql) {
        java.util.Date utilDate = new java.util.Date(dateSql.getTime());
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(utilDate));
        return utilDate;
    }

    public int converLongToInt(Long numberLong){
        int numerInt = numberLong.intValue();
        return numerInt;
    }

    private Date now() {
        Calendar calendar = Calendar.getInstance();
        java.util.Date currentDate = calendar.getTime();
        return new Date(currentDate.getTime());
    }

    public String getUtilToTimeStamp(java.util.Date dateUtilTimeStamp) {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateUtilTimeStamp);
    }
}




