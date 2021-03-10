package br.com.votacao.votaweb.controller;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.votacao.votaweb.model.Sessao;
import br.com.votacao.votaweb.repository.SessaoRepository;
import br.com.votacao.votaweb.repository.VotacaoRepository;
import io.swagger.annotations.Api;

@RestController
@Api(value = "sessao")
@RequestMapping(value = "/api", produces = "application/json")
public class SessaoController {

	private Logger logger = LoggerFactory.getLogger(SessaoController.class);

	@Autowired
	SessaoRepository sessaoRepository;

	@Autowired
	VotacaoRepository votacaoRepository;

	DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

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

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/v1/sessoes/nova-sessao")
	public ResponseEntity<Sessao> abrirSessaoDefault() {
		Sessao sessao = new Sessao();
		String stringTimestamp = nowString();
		sessao.setInicioSessao(stringTimestamp);
		try {
			sessao.setTerminoSessao(format(stringTimestamp, 1L));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		sessao.setMensagemTermino(true);
		if (isSessaoAberta(sessao)) {
			Sessao sessaoSalva = sessaoRepository.save(sessao);
			logger.info("Sessão aberta por padrão, por 1 minuto!");
			return ResponseEntity.ok().body(sessaoRepository.findById(sessaoSalva.getId()).get());
		}
		return null;
	}

	// Abrindo sessão, com tempo especificado pelo usuário, para votação de pauta
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/v1/sessoes/nova-sessao/{tempoSessao}")
	public ResponseEntity<Sessao> abrirSessao(@PathVariable Long tempoSessao) {
		Sessao sessao = new Sessao();
		String stringTimestamp = nowString();
		sessao.setInicioSessao(stringTimestamp);
		try {
			sessao.setTerminoSessao(format(stringTimestamp, tempoSessao));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		sessao.setMensagemTermino(true);
		if (isSessaoAberta(sessao)) {
			Sessao sessaoSalva = sessaoRepository.save(sessao);
			logger.info("Sessão aberta com sucesso por " + tempoSessao + " minutos para expiração!");
			return ResponseEntity.ok().body(sessaoRepository.findById(sessaoSalva.getId()).get());
		}
		return null;
	}

	// Metodo para abrir sessao
	@PutMapping(value = "/v1/sessoes/{id}")
	public ResponseEntity<Sessao> update(@PathVariable("id") long id, @RequestBody Sessao sessao) {
		logger.info("Sessão atualizada com sucesso!");
		return sessaoRepository.findById(id).map(record -> {
			record.setInicioSessao(sessao.getInicioSessao());
			Sessao updated = sessaoRepository.save(record);
			return ResponseEntity.ok().body(updated);
		}).orElse(ResponseEntity.notFound().build());
	}

	public Boolean isSessaoAberta(Sessao sessao) {
		if (sessao != null) {
			Sessao sessaoBanco = sessaoRepository.findUltimoResgistro();
			if (sessaoBanco != null) {
				if (convertStringToTimestamp(sessaoBanco.getTerminoSessao())
						.before(convertStringToTimestamp(nowString()))) {
					logger.info("Efetue seu voto como associado!");
					return true;
				} else {
					logger.info("Existe uma sessão aberta! Aproveite para efetuar seu voto como associado!");
					return false;
				}
			}else {
				return true;
			}
		}
		return true;
	}

	public String convertToDatabaseColumn(LocalDateTime value) {
		return (value != null) ? value.format(dateTimeFormatter) : null;
	}

	private String format(String sourceDate, Long hours) throws ParseException {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(sdf.parse(sourceDate));
		calendar.add(Calendar.MINUTE, converLongToInt(hours));
		return sdf.format(calendar.getTime());
	}

	public Timestamp convertStringToTimestamp(String timestampString) {
		Timestamp ts = Timestamp.valueOf(timestampString);
		return ts;
	}

	public int converLongToInt(Long numberLong) {
		int numerInt = numberLong.intValue();
		return numerInt;
	}

	private String nowString() {
		Calendar calendar = Calendar.getInstance();
		java.util.Date currentDate = calendar.getTime();
		SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return fmt.format(new Date(currentDate.getTime()));
	}


}
