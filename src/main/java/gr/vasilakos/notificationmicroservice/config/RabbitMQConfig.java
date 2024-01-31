package gr.vasilakos.notificationmicroservice.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.air.queue.name}")
    private String airQueue;

    @Value("${rabbitmq.air.routing.key}")
    private String airRoutingKey;

    @Value("${rabbitmq.water.queue.name}")
    private String waterQueue;

    @Value("${rabbitmq.water.routing.key}")
    private String waterRoutingKey;

    @Value("${rabbitmq.soil.queue.name}")
    private String soilQueue;

    @Value("${rabbitmq.soil.routing.key}")
    private String soilRoutingKey;

    @Bean
    public DirectExchange exchange(){
        return new DirectExchange(exchange);
    }

    @Bean
    public Queue airQueue(){
        return  new Queue(airQueue);
    }

    @Bean
    public Binding airBinding(){
        return BindingBuilder.bind(airQueue())
                .to(exchange())
                .with(airRoutingKey);
    }

    @Bean
    public Queue waterQueue(){
        return  new Queue(waterQueue);
    }

    @Bean
    public Binding waterBinding(){
        return BindingBuilder.bind(waterQueue())
                .to(exchange())
                .with(waterRoutingKey);
    }

    @Bean
    public Queue soilQueue(){
        return  new Queue(soilQueue);
    }

    @Bean
    public Binding soilBinding(){
        return BindingBuilder.bind(waterQueue())
                .to(exchange())
                .with(soilRoutingKey);
    }

    @Bean
    public MessageConverter converter(){
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return  rabbitTemplate;
    }
}
