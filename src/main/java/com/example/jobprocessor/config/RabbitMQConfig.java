package com.example.jobprocessor.config;

import java.util.HashMap;
import java.util.Map;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String MAIN_QUEUE = "jobQueue";
    public static final String DLQ = "jobQueue.dlq";

    public static final String EXCHANGE = "jobExchange";
    public static final String DLX = "jobDLX";

    // MAIN QUEUE
    @Bean
    Queue mainQueue() {

        Map<String, Object> args = new HashMap<>();

        // Define Dead Letter Exchange
        args.put("x-dead-letter-exchange", DLX);

        // Define routing key for DLQ
        args.put("x-dead-letter-routing-key", DLQ);

        return new Queue(MAIN_QUEUE, true, false, false, args);
    }

    // DLQ QUEUE
    @Bean
    Queue deadLetterQueue() {
        return new Queue(DLQ, true);
    }

    // MAIN EXCHANGE
    @Bean
    DirectExchange exchange() {
        return new DirectExchange(EXCHANGE);
    }

    // DEAD LETTER EXCHANGE
    @Bean
    DirectExchange deadLetterExchange() {
        return new DirectExchange(DLX);
    }

    // BIND MAIN QUEUE
    @Bean
    Binding binding() {
        return BindingBuilder
                .bind(mainQueue())
                .to(exchange())
                .with(MAIN_QUEUE);
    }

    // BIND DLQ
    @Bean
    Binding dlqBinding() {
        return BindingBuilder
                .bind(deadLetterQueue())
                .to(deadLetterExchange())
                .with(DLQ);
    }
}