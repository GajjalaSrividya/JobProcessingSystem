package com.example.jobprocessor.worker;

import com.example.jobprocessor.queue.JobQueue;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

@Component
public class JobWorker {

    private final JobQueue jobQueue;

    public JobWorker(JobQueue jobQueue) {
        this.jobQueue = jobQueue;
    }

    @PostConstruct
    public void startWorker() {
        // Start worker thread
        new Thread(() -> {
            while (true) {
                String job = jobQueue.takeJob();

                System.out.println("Processing job: " + job);

                try {
                    Thread.sleep(5000); // simulate work
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Completed job: " + job);
            }
        }).start();
    }
}