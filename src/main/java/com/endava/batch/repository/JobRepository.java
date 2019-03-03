package com.endava.batch.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.endava.batch.domain.Job;
import com.endava.batch.domain.JobResult;

public interface JobRepository extends CrudRepository<Job, Long> {

    @Query("select jr from Job j join j.jobResult jr where j.id = :id")
    Optional<JobResult> findJobResultByJobId(@Param("id") long id);
}
