package com.veriprotocol.aiinfra.service;



import com.veriprotocol.aiinfra.api.CreateJobRequest;
import com.veriprotocol.aiinfra.api.JobResponse;
import com.veriprotocol.aiinfra.domain.JobEntity;
import com.veriprotocol.aiinfra.repo.JobRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class JobService {

    private final JobRepository repo;

    public JobService(JobRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public JobResponse submit(CreateJobRequest req) {
        // Idempotency: same requestId returns existing job
        var existing = repo.findByRequestId(req.requestId());
        if (existing.isPresent()) return toResponse(existing.get());

        var job = new JobEntity(req.requestId(), req.image());
        var saved = repo.save(job);
        return toResponse(saved);
    }

    @Transactional(readOnly = true)
    public JobResponse get(String id) {
        var job = repo.findById(id).orElseThrow(() -> new IllegalArgumentException("job not found"));
        return toResponse(job);
    }

    private static JobResponse toResponse(JobEntity j) {
        return new JobResponse(
                j.getId(),
                j.getRequestId(),
                j.getImage(),
                j.getStatus(),
                j.getCreatedAt(),
                j.getUpdatedAt()
        );
    }
}
