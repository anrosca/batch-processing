package com.endava.batch.executor;

import java.util.concurrent.ExecutorService;

import org.springframework.stereotype.Service;

import com.endava.batch.domain.Job;
import com.endava.batch.repository.JobRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobExecutor {

    private final JobRepository jobRepository;

    private final ExecutorService executorService;

    public void execute(final Job job) {
        executorService.submit(new JobWrapper(jobRepository, job));
    }
}
