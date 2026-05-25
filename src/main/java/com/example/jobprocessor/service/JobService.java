package com.example.jobprocessor.service;

import java.util.List;
import com.example.jobprocessor.exception.JobNotFoundException;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import com.example.jobprocessor.entity.Job;
import com.example.jobprocessor.repository.JobRepository;

@Service
public class JobService {

    private final RabbitTemplate rabbitTemplate;
    private final JobRepository jobRepository;

    public JobService(RabbitTemplate rabbitTemplate,
                      JobRepository jobRepository) {

        this.rabbitTemplate = rabbitTemplate;
        this.jobRepository = jobRepository;
    }

    // CREATE JOB
    public Job createJob(String payload) {

        // Create job object
        Job job = new Job();
        job.setPayload(payload);
        job.setStatus("QUEUED");

        // Save in DB
        Job savedJob = jobRepository.save(job);

        // Send JOB ID to RabbitMQ
        rabbitTemplate.convertAndSend(
                "jobQueue",
                savedJob.getId().toString()
        );

        return savedJob;
    }

    // GET ALL JOBS
    public List<Job> getAllJobs() {

        return jobRepository.findAll();
    }

 // GET JOB BY ID
    @Cacheable(value = "jobs", key = "#id")
    public Job getJobById(Long id) {

        System.out.println("Fetching from DATABASE...");

        return jobRepository.findById(id)
                .orElseThrow(() ->
                        new JobNotFoundException(id));
    }
    @CacheEvict(value = "jobs", key = "#id")
    public void deleteJob(Long id) {

        Job job = jobRepository.findById(id)
                .orElseThrow(() ->
                        new JobNotFoundException(id));

        jobRepository.delete(job);

        System.out.println(
                "Deleted Job ID: " + id
        );
    }

    // GET JOBS BY STATUS
    public List<Job> getJobsByStatus(String status) {

        return jobRepository.findByStatus(status);
    }
}