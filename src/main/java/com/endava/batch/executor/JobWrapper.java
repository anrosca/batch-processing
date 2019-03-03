package com.endava.batch.executor;

import com.endava.batch.domain.Job;
import com.endava.batch.domain.JobResult;
import com.endava.batch.domain.State;
import com.endava.batch.repository.JobRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JobWrapper implements Runnable {

    private final JobRepository jobRepository;

    private final Job jobToExecute;

    public void run() {
        markJobAs(State.RUNNING);
        final Object result = executeTask();
        setJobResult(result);
    }

    private void setJobResult(final Object result) {
        jobToExecute.setJobResult(JobResult.builder()
                .job(jobToExecute)
                .result(result.toString())
                .build());
        jobRepository.save(jobToExecute);
    }

    private Object executeTask() {
        Object result = null;
        try {
            result = jobToExecute.getTask().call();
            markJobAs(State.TERMINATED);
        } catch (Exception e) {
            markJobAs(State.TERMINATED_EXCEPTIONALLY);
        }
        return result;
    }

    private void markJobAs(final State state) {
        jobToExecute.setState(state);
        jobRepository.save(jobToExecute);
    }
}