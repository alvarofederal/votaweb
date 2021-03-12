package br.com.votacao.votaweb;

import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

import br.com.votacao.votaweb.controller.SessaoController;

@Configuration
@EntityScan("br.com.votacao.votaweb.model")
@SpringBootApplication
public class VotawebApplication {

	public static void main(String[] args) {
		LoggerFactory.getLogger(SessaoController.class).info("Sistema inicializado com sucesso!");
		SpringApplication.run(VotawebApplication.class, args);
	}

}
