//package br.com.votacao.votaweb.schedule;
//
//import br.com.votacao.votaweb.controller.SessaoController;
//import br.com.votacao.votaweb.mensageria.VotacaoProducer;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//
//@Component
//public class VotacaoServiceScheduling {
//
//	private SessaoController sessaoController;
////	private VotacaoProducer votacaoProducer;
//
//	@Autowired
////	public VotacaoServiceScheduling(SessaoController sessaoController, VotacaoProducer votacaoProducer) {
//	public VotacaoServiceScheduling(SessaoController sessaoController) {
//		this.sessaoController = sessaoController;
////		this.votacaoProducer = votacaoProducer;
//	}
//
//	@Scheduled(cron = "15 * * * * *")
//	public void verificarQualSessaoVotaçãooEncerrada() {
//		System.out.println("Scheduling inicializando agora...");
//		List<String> listOfSessaoFechada = sessaoController.doHaveAnOpenSessionThatCanBeClosed();
//		listOfSessaoFechada.forEach(sessao -> {
//			System.out.println("Criando mensagem: " + sessao);
////			votacaoProducer.enviaMessage(sessao);
//		});
//		System.out.println("Scheduling finalizando agora...");
//	}
//
//}
