package com.example.jobprocessor.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class JobRequest {

    @NotBlank(message = "Payload cannot be empty")
    @Size(max = 100, message = "Payload cannot exceed 100 characters")
    private String payload;

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }
}