//package br.com.votacao.votaweb.mensageria;
//
//import org.springframework.amqp.core.Queue;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class VotacaoProducer {
//
//	private RabbitTemplate rabbitTemplate;
//	private Queue queue;
//
//	@Autowired
//	public VotacaoProducer(RabbitTemplate rabbitTemplate, Queue queue) {
//		this.rabbitTemplate = rabbitTemplate;
//		this.queue = queue;
//	}
//
//	public void enviaMessage(String message) {
//		rabbitTemplate.convertAndSend(queue.getName(), message);
//	}
//
//}
