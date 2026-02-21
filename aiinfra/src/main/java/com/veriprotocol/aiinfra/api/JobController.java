package com.veriprotocol.aiinfra.api;


import com.veriprotocol.aiinfra.service.JobService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobService service;

    public JobController(JobService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<JobResponse> create(@Valid @RequestBody CreateJobRequest req) {
        return ResponseEntity.ok(service.submit(req));
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobResponse> get(@PathVariable String id) {
        return ResponseEntity.ok(service.get(id));
    }
}
