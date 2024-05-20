package com.dream.exerciseSystem.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String REGISTER_QUEUE = "registerQueue";
    public static final String LOGIN_QUEUE = "loginQueue";
    public static final String REPLY_QUEUE = "replyQueue";

    @Bean
    public Queue registerQueue(){
        return new Queue(REGISTER_QUEUE, false);
    }

    @Bean
    public Queue loginQueue(){
        return new Queue(LOGIN_QUEUE, false);
    }

    @Bean Queue replyQueue(){
        return new Queue(REPLY_QUEUE, false);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setReplyTimeout(100); // 设置回复超时
        return rabbitTemplate;
    }

//    @Bean
//    public SimpleMessageListenerContainer replyContainer(ConnectionFactory connectionFactory,
//                                                         MessageListenerAdapter listenerAdapter) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.setQueueNames(REPLY_QUEUE);
//        container.setMessageListener(listenerAdapter);
//        return container;
//    }
//    @Bean
//    public MessageListenerAdapter listenerAdapter(RabbitTemplate rabbitTemplate) {
//        return new MessageListenerAdapter(rabbitTemplate.getReplyMessageListener());
//    }

}
