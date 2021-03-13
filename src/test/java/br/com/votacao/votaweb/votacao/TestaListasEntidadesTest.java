package br.com.votacao.votaweb.votacao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.votacao.votaweb.model.Associado;
import br.com.votacao.votaweb.model.Pauta;
import br.com.votacao.votaweb.model.Sessao;
import br.com.votacao.votaweb.service.AssociadoService;
import br.com.votacao.votaweb.service.PautaService;
import br.com.votacao.votaweb.service.ResultadoVotacaoService;
import br.com.votacao.votaweb.service.SessaoService;
import br.com.votacao.votaweb.service.VotacaoService;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class TestaListasEntidadesTest {
	
	@Autowired
	public VotacaoService votacaoService;

	@Autowired
	public SessaoService sessaoService;

	@Autowired
	public PautaService pautaService;

	@Autowired
	public AssociadoService associadoService;

	@Autowired
	public ResultadoVotacaoService resultadoVotacaoService;

	@Test
	public void listaSessaoZerada() {
		List<Sessao> listaSessao = sessaoService.findAll();
		assertEquals(0, listaSessao.size());
	}

	@Test
	public void listaPauta() {
		List<Pauta> listaPauta = pautaService.findAll();
		assertEquals(0, listaPauta.size());
	}

	@Test
	public void listaAssociado() {
		List<Associado> listaAssociado = associadoService.findAll();
		assertEquals(0, listaAssociado.size());
	}

	@Test
	public void listaVotacao() {
		List<Associado> listaVotacao = associadoService.findAll();
		assertEquals(0, listaVotacao.size());
	}

}
