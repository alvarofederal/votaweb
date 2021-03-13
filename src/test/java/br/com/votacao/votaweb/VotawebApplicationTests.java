package br.com.votacao.votaweb;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.votacao.votaweb.controller.VotacaoController;

@SpringBootTest
class VotawebApplicationTests {

	@Autowired
	private VotacaoController controller;

	@Test
	public void contextLoads() throws Exception {
		assertThat(controller).isNotNull();
	}

}
