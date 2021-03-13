package br.com.votacao.votaweb.votacao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import br.com.votacao.votaweb.model.Associado;
import br.com.votacao.votaweb.model.Pauta;
import br.com.votacao.votaweb.model.Sessao;
import br.com.votacao.votaweb.model.Votacao;
import br.com.votacao.votaweb.service.AssociadoService;
import br.com.votacao.votaweb.service.PautaService;
import br.com.votacao.votaweb.service.ResultadoVotacaoService;
import br.com.votacao.votaweb.service.SessaoService;
import br.com.votacao.votaweb.service.VotacaoService;
import br.com.votacao.votaweb.utils.GeraCpfCnpj;
import br.com.votacao.votaweb.utils.GeradorSequenciais;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class VotoTest {
	
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
	
	int numeroBase = GeradorSequenciais.getProximoSequencial();

	@Test
	public void votoCompletoTest() {
		
		int numberPauta = numeroBase;
		int numberAssociado = numeroBase;
		int numberVotacao = numeroBase;
		int numberSessao = numeroBase;
		
		Pauta pauta = new Pauta();
		Pauta ultimaPauta = pautaService.findUltimaPauta();
		if (ultimaPauta == null) {
			pauta.setId(numberPauta);
			pauta.setNomePauta("Pauta Teste " + numberPauta);
		}else {
			pauta.setId(ultimaPauta.getId() + numberPauta);
			pauta.setNomePauta("Pauta Teste " + numberPauta);
		}
		Pauta pautaSalva = pautaService.save(pauta);
		
		assertEquals(pauta.getId() + numberPauta, pautaSalva.getId());


		Associado associado = new Associado();
		GeraCpfCnpj geraCpfCnpj = new GeraCpfCnpj();
		Associado ultimaAssociado = associadoService.findUltimoAssociado();
		if (ultimaAssociado == null) {
			associado.setId(numberAssociado);
			associado.setCpf(geraCpfCnpj.cpf(false));
			associado.setNomeAssociado("Associado Teste " + numberAssociado);
		} else {
			associado.setId(ultimaAssociado.getId() + numberAssociado);
			associado.setCpf(geraCpfCnpj.cpf(false));
			associado.setNomeAssociado("Associado Teste " + numberAssociado);
			
		}
		Associado associadoSalvo = associadoService.save(associado);
		
		assertEquals(associado.getId() + numberAssociado, associadoSalvo.getId());

		
		Sessao sessao = new Sessao();
		Sessao ultimaSessao = sessaoService.findUltimaSessao();
		if (ultimaSessao == null) {
			sessao.setId(numberSessao);
			sessao.setInicioSessao("2021-03-20 00:00:00");
			sessao.setTerminoSessao("2021-03-25 00:00:00");
			sessao.setMensagemTermino(true);
		}else {
			sessao.setId(ultimaSessao.getId()+numberSessao);
			sessao.setInicioSessao(ultimaSessao.getInicioSessao());
			sessao.setTerminoSessao(ultimaSessao.getTerminoSessao());
			sessao.setMensagemTermino(ultimaSessao.getMensagemTermino());			
		}
		Sessao sessaoSalva = sessaoService.save(sessao);
		assertEquals(sessao.getId() + numberSessao, sessaoSalva.getId());

		Votacao votacao = new Votacao();
		Votacao ultimaVotacao = votacaoService.findUltimaVotacao();
		if (ultimaVotacao == null) {
			votacao = new Votacao(numberVotacao, associado, pauta, sessao, 1);
		}else {
			votacao = new Votacao(ultimaVotacao.getId() + numberVotacao, associado, pauta, sessao, 1);
		}
		Votacao votacaoSalva = votacaoService.save(votacao);

		assertEquals(votacao.getId() + numberVotacao, votacaoSalva.getId());

		associadoService.deleteAssociado(associado);
		sessaoService.deleteSessao(sessao);
		pautaService.deletePauta(pauta);
		votacaoService.deleteVotacao(votacao);
	}
}
