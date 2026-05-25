package com.example.jobprocessor.consumer;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;

import com.example.jobprocessor.entity.Job;
import com.example.jobprocessor.repository.JobRepository;
import com.rabbitmq.client.Channel;

@Component
public class JobConsumer {

    private static final int MAX_RETRY = 3;

    private static final Logger logger =
            LoggerFactory.getLogger(JobConsumer.class);

    private final JobRepository jobRepository;
    private final RabbitTemplate rabbitTemplate;

    public JobConsumer(
            JobRepository jobRepository,
            RabbitTemplate rabbitTemplate
    ) {
        this.jobRepository = jobRepository;
        this.rabbitTemplate = rabbitTemplate;
    }

    @RabbitListener(queues = "jobQueue")
    public void consumeJob(
            Long jobId,
            Channel channel,
            @Header(AmqpHeaders.DELIVERY_TAG) long tag,
            @Header(required = false, name = "x-retry-count") Integer retryCount
    ) throws IOException {

        if (retryCount == null) {
            retryCount = 0;
        }

        Job job = null;

        try {

            // fetch job from database
            job = jobRepository.findById(jobId)
                    .orElseThrow(() ->
                            new RuntimeException(
                                    "Job not found with id: " + jobId
                            ));

            logger.info(
                    "Thread: {} | Processing Job ID: {} | Payload: {} | Retry: {}",
                    Thread.currentThread().getName(),
                    jobId,
                    job.getPayload(),
                    retryCount
            );

            // update status to PROCESSING
            job.setStatus("PROCESSING");
            jobRepository.save(job);

            // simulate processing time
           // Thread.sleep(30000);
            int processingTime =
                    (int) (Math.random() * 10000) + 2000;
            Thread.sleep(processingTime);

            // simulate failure
            if (job.getPayload().equalsIgnoreCase("fail")) {
                throw new RuntimeException("Job failed");
            }

            // success
            job.setStatus("COMPLETED");
            jobRepository.save(job);

            logger.info(
                    "Thread: {} | Job completed successfully: {}",
                    Thread.currentThread().getName(),
                    jobId
            );

            // acknowledge message
            channel.basicAck(tag, false);

        } catch (Exception e) {

            retryCount++;

            logger.error(
                    "Thread: {} | Job failed: {} | Attempt: {}",
                    Thread.currentThread().getName(),
                    jobId,
                    retryCount
            );

            // retry logic
            if (retryCount < MAX_RETRY) {

                Integer updatedRetryCount = retryCount;

                rabbitTemplate.convertAndSend(
                        "jobQueue",
                        jobId,
                        message -> {

                            message.getMessageProperties()
                                    .setHeader(
                                            "x-retry-count",
                                            updatedRetryCount
                                    );

                            return message;
                        }
                );

                logger.warn(
                        "Retrying Job ID: {}",
                        jobId
                );

            } else {

                // mark failed
                if (job != null) {

                    job.setStatus("FAILED");
                    jobRepository.save(job);
                }

                // send to dead letter queue
                rabbitTemplate.convertAndSend(
                        "jobQueue.dlq",
                        jobId
                );

                logger.error(
                        "Job ID {} sent to Dead Letter Queue",
                        jobId
                );
            }

            // acknowledge original message
            channel.basicAck(tag, false);
        }
    }
}