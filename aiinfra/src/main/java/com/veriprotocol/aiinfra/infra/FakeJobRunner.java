package com.veriprotocol.aiinfra.infra;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class FakeJobRunner implements JobRunner{
	
	private final Random rnd = new Random();
	
	@Override
    public void run(String jobId, String image) throws Exception {
        // simulate work
        Thread.sleep(800);

        // simulate occasional failure (for retry signal)
        if (rnd.nextInt(4) == 0) { // 25% fail
            throw new RuntimeException("simulated failure for jobId=" + jobId);
        }
    }


}
