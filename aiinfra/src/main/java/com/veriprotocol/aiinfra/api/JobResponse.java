package com.veriprotocol.aiinfra.api;



import com.veriprotocol.aiinfra.domain.JobStatus;
import java.time.Instant;

public record JobResponse(
        String id,
        String requestId,
        String image,
        JobStatus status,
        Instant createdAt,
        Instant updatedAt
) {}