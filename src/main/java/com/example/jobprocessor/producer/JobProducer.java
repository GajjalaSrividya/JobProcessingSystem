package com.example.jobprocessor.producer;

import com.example.jobprocessor.config.RabbitMQConfig;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class JobProducer {

    private final RabbitTemplate rabbitTemplate;

    public JobProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendJob(String job) {

        System.out.println("Sending job: " + job);

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.MAIN_QUEUE,
                job
        );
    }
}