package com.example.jobprocessor.dto;

public class JobResponse {

    private Long id;
    private String payload;
    private String status;

    public JobResponse(Long id, String payload, String status) {
        this.id = id;
        this.payload = payload;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public String getPayload() {
        return payload;
    }

    public String getStatus() {
        return status;
    }
}