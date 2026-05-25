package com.example.jobprocessor.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.jobprocessor.entity.Job;

public interface JobRepository
        extends JpaRepository<Job, Long> {

    List<Job> findByStatus(String status);
}