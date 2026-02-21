package com.veriprotocol.aiinfra.service;


import com.veriprotocol.aiinfra.domain.JobStatus;
import com.veriprotocol.aiinfra.infra.JobRunner;
import com.veriprotocol.aiinfra.repo.JobRepository;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JobProcessorService {

    private final JobRepository repo;
    private final JobRunner runner;

    public JobProcessorService(JobRepository repo, JobRunner runner) {
        this.repo = repo;
        this.runner = runner;
    }

    @Transactional
    public void processOne() {
    	var jobs = repo.pickNextPendingForUpdate(JobStatus.PENDING, PageRequest.of(0, 1));
        if (jobs.isEmpty()) return;

        var job = jobs.get(0);
        job.setStatus(JobStatus.RUNNING);
        job.incrementAttempts();

        try {
            runner.run(job.getId(), job.getImage());
            job.setStatus(JobStatus.SUCCEEDED);
            job.setLastError(null);
        } catch (Exception e) {
            job.setLastError(e.getMessage());
            job.setStatus(job.getAttempts() >= job.getMaxAttempts()
                    ? JobStatus.FAILED
                    : JobStatus.PENDING);
        }
    }
}
