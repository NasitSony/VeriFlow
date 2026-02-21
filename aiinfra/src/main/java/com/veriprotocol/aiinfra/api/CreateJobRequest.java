package com.veriprotocol.aiinfra.api;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateJobRequest(
        @NotBlank @Size(max = 64) String requestId,
        @NotBlank @Size(max = 200) String image
) {}
