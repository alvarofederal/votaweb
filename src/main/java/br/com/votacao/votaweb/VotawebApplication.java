package br.com.votacao.votaweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

//@EnableJpaRepositories("br.com.votacao.votaweb.repository")
@Configuration
@EntityScan("br.com.votacao.votaweb.model")
@SpringBootApplication
public class VotawebApplication {

	public static void main(String[] args) {
		SpringApplication.run(VotawebApplication.class, args);
	}

}
