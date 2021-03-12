package br.com.votacao.votaweb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import br.com.votacao.votaweb.service.ValidaCPFService;

@Service
public class ValidaCPFServiceImpl implements ValidaCPFService {

	private final static String ABLE_TO_VOTE = "ABLE_TO_VOTE";

	@Bean
	public RestTemplate restTesmplate() {
		return new RestTemplate();
	}

	@Autowired
	private RestTemplate restTemplate = restTesmplate();

	@Autowired
	private final Environment env = null;

	@Override
	public boolean verificaIntegraçãoCPF(String cpf) {
		try {
			String url = env.getProperty("cpf.validacao.integracao") + cpf;
			ResponseEntity<String> response = restTemplate.getForEntity(url, String.class); // 3
			return response.getBody().equals(ABLE_TO_VOTE);
		} catch (HttpStatusCodeException ex) {
			if (ex.getStatusCode() == HttpStatus.NOT_FOUND)
				throw new ResponseStatusException(HttpStatus.NOT_FOUND, "CPF do associado inválido!");
			throw new ResponseStatusException(HttpStatus.SERVICE_UNAVAILABLE,
					"Problemas com a integração, Por favor, procuprar o administrador!");
		}
	}
}
