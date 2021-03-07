package br.com.votacao.votaweb.mensageria;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class VotacaoConsumer {

	@RabbitListener(queues = {"${rabbitmq.queue-name}"})
    public void receive(@Payload String fileBody) {
       System.out.println("Consumer: " + fileBody);
    }
	
}
