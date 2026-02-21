package com.veriprotocol.aiinfra.scheduler;


//import com.veriprotocol.aiinfra.domain.JobStatus;
//import com.veriprotocol.aiinfra.repo.JobRepository;
//import com.veriprotocol.aiinfra.infra.JobRunner;
import com.veriprotocol.aiinfra.service.JobProcessorService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
//import org.springframework.transaction.annotation.Transactional;

@Component
public class JobScheduler {

   // private final JobRepository repo;
   // private final JobRunner runner;

   /* public JobScheduler(JobRepository repo, JobRunner runner) {
        this.repo = repo;
        this.runner = runner;
    }*/

   /* @Scheduled(fixedDelayString = "1000")
    public void tick() {
        // keep the scheduled method itself small; do txn inside helper
        processOne();
    }*/

   private final JobProcessorService processor;

    public JobScheduler(JobProcessorService processor) {
        this.processor = processor;
    }

    @Scheduled(fixedDelayString = "1000")
    public void tick() {
    	System.out.println("[scheduler] tick");
        try {
            processor.processOne();
        } catch (Exception e) {
            System.out.println("[scheduler] processOne failed: " + e.getMessage());
            e.printStackTrace();
        }
    }
}