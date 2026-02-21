package com.veriprotocol.aiinfra.domain;



import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "jobs", indexes = {
        @Index(name = "idx_jobs_status_created", columnList = "status,createdAt")
})
public class JobEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true, length = 64)
    private String requestId; // idempotency key

    @Column(nullable = false, length = 200)
    private String image; // container image to run later in K8s

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 16)
    private JobStatus status;

    @Column(nullable = false)
    private Instant createdAt;

    @Column(nullable = false)
    private Instant updatedAt;
    
    @Column(nullable = false)
    private int attempts = 0;

    @Column(nullable = false)
    private int maxAttempts = 3;

    @Column(length = 1000)
    private String lastError;


    protected JobEntity() {}

    public JobEntity(String requestId, String image) {
        this.requestId = requestId;
        this.image = image;
        this.status = JobStatus.PENDING;
        this.createdAt = Instant.now();
        this.updatedAt = this.createdAt;
        this.attempts = 0;
        this.maxAttempts = 3;
        this.lastError = null;
    }

    @PreUpdate
    public void onUpdate() {
        this.updatedAt = Instant.now();
    }

    // getters/setters (or use Lombok if you added it)
    public String getId() { return id; }
    public String getRequestId() { return requestId; }
    public String getImage() { return image; }
    public JobStatus getStatus() { return status; }
    public Instant getCreatedAt() { return createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }

    public void setStatus(JobStatus status) { this.status = status; }
    public int getAttempts() { return attempts; }
    public int getMaxAttempts() { return maxAttempts; }
    public String getLastError() { return lastError; }

    public void incrementAttempts() { this.attempts++; }
    public void setLastError(String lastError) { this.lastError = lastError; }
}