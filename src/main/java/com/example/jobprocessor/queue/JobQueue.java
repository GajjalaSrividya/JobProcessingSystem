package com.example.jobprocessor.queue;

import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Component
public class JobQueue {

    private final BlockingQueue<String> queue = new LinkedBlockingQueue<>();

    // Add job to queue (Producer)
    public void addJob(String job) {
        try {
            queue.put(job); // waits if needed
            System.out.println("Job added to queue: " + job);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Take job from queue (Consumer)
    public String takeJob() {
        try {
            return queue.take(); // waits if empty
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}