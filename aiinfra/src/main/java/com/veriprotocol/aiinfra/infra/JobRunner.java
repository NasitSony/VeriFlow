package com.veriprotocol.aiinfra.infra;

public interface JobRunner {
	void run(String jobId, String image) throws Exception;
}
