package br.com.votacao.votaweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

//@EnableJpaRepositories("br.com.votacao.votaweb.repository")
@Configuration
@EntityScan("br.com.votacao.votaweb.model")
@SpringBootApplication
public class VotawebApplication {

	public static void main(String[] args) {
		SpringApplication.run(VotawebApplication.class, args);
	}

}
