package com.example.jobprocessor.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.jobprocessor.dto.JobRequest;
import com.example.jobprocessor.dto.JobResponse;
import com.example.jobprocessor.entity.Job;
import com.example.jobprocessor.service.JobService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private static final Logger logger =
            LoggerFactory.getLogger(JobController.class);

    private final JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    // CREATE JOB
    @PostMapping
    public ResponseEntity<JobResponse> createJob(
            @Valid @RequestBody JobRequest request
    ) {

        logger.info(
                "Received request to create job with payload: {}",
                request.getPayload()
        );

        Job savedJob =
                jobService.createJob(request.getPayload());

        logger.info(
                "Job created successfully with ID: {}",
                savedJob.getId()
        );

        return new ResponseEntity<>(
                mapToResponse(savedJob),
                HttpStatus.CREATED
        );
    }

    // GET ALL JOBS
    @GetMapping
    public ResponseEntity<List<JobResponse>> getAllJobs() {

        logger.info("Fetching all jobs");

        List<JobResponse> jobs =
                jobService.getAllJobs()
                        .stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(jobs);
    }

    // GET JOB BY ID
    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> getJobById(
            @PathVariable Long id
    ) {

        logger.info(
                "Fetching job with ID: {}",
                id
        );

        Job job = jobService.getJobById(id);

        return ResponseEntity.ok(
                mapToResponse(job)
        );
    }
    @DeleteMapping("/{id}")
    public String deleteJob(@PathVariable Long id) {

        jobService.deleteJob(id);

        return "Job deleted successfully";
    }
    // GET JOBS BY STATUS
    @GetMapping("/status/{status}")
    public ResponseEntity<List<JobResponse>>
    getJobsByStatus(
            @PathVariable String status
    ) {

        logger.info(
                "Fetching jobs with status: {}",
                status
        );

        List<JobResponse> jobs =
                jobService.getJobsByStatus(status)
                        .stream()
                        .map(this::mapToResponse)
                        .collect(Collectors.toList());

        return ResponseEntity.ok(jobs);
    }

    // DTO mapper
    private JobResponse mapToResponse(Job job) {

        return new JobResponse(
                job.getId(),
                job.getPayload(),
                job.getStatus()
        );
    }
}