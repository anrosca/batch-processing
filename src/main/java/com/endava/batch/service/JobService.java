package com.endava.batch.service;

import java.util.concurrent.Callable;

import org.springframework.stereotype.Service;

import com.endava.batch.domain.Job;
import com.endava.batch.domain.JobResult;
import com.endava.batch.domain.State;
import com.endava.batch.exception.JobNotFoundException;
import com.endava.batch.exception.JobResultNotFoundException;
import com.endava.batch.executor.JobExecutor;
import com.endava.batch.repository.JobRepository;
import com.endava.batch.response.JobResultDTO;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;

    private final JobExecutor jobExecutor;

    public long startJob(final Callable task) {
        final Job job = jobRepository.save(Job.builder()
                .task(task)
                .state(State.SCHEDULED)
                .build());
        jobExecutor.execute(job);
        return job.getId();
    }

    public Job findJobBy(final long jobId) {
        return jobRepository.findById(jobId)
                .orElseThrow(JobNotFoundException::new);
    }

    public JobResultDTO findJobResultOf(final long jobId) {
        JobResult jobResult = jobRepository.findJobResultByJobId(jobId).orElseThrow(JobResultNotFoundException::new);
        return new JobResultDTO(jobId, jobResult.getResult());
    }
}
