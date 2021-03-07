package br.com.votacao.votaweb.schedule;

import br.com.votacao.votaweb.mensageria.VotacaoProducer;
import br.com.votacao.votaweb.model.Votacao;
import br.com.votacao.votaweb.service.SessaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VotacaoServiceScheduling {

	private SessaoService sessaoService;
	private VotacaoProducer votacaoProducer;

	@Autowired
	public VotacaoServiceScheduling(SessaoService sessaoService, VotacaoProducer votacaoProducer) {
		this.sessaoService = sessaoService;
		this.votacaoProducer = votacaoProducer;
	}

	@Scheduled(cron = "15 * * * * *")
	public void verificarQualSessaoVotaçãooEncerrada() {
		System.out.println("Scheduling inicializando agora...");
		List<String> listOfSessaoFechada = sessaoService.doHaveAnOpenSessionThatCanBeClosed();
		listOfSessaoFechada.forEach(sessao -> {
			System.out.println("Criando mensagem: " + sessao);
			votacaoProducer.enviaMessage(sessao);
		});
		System.out.println("Scheduling finalizando agora...");
	}

}
