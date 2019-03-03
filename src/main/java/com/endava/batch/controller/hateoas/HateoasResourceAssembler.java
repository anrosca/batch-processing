package com.endava.batch.controller.hateoas;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import com.endava.batch.controller.JobController;
import com.endava.batch.domain.Job;
import com.endava.batch.domain.State;
import com.endava.batch.response.JobResultDTO;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Component
public class HateoasResourceAssembler {

    public ResourceSupport toJobResource(final long jobId) {
        ResourceSupport resourceSupport = new ResourceSupport();
        resourceSupport.add(linkTo(methodOn(JobController.class).getJobStatus(jobId)).withRel("jobState"));
        resourceSupport.add(linkTo(methodOn(JobController.class).getJobResult(jobId)).withRel("jobResult"));
        return resourceSupport;
    }

    public ResourceSupport addJobStatusLinks(final Job job) {
        JobResourceSupport support = new JobResourceSupport(job);
        support.add(linkTo(methodOn(JobController.class).getJobResult(job.getId())).withRel("jobResult"));
        support.add(linkTo(JobController.class).slash(job.getId()).slash("state").withSelfRel());
        return support;
    }

    public ResourceSupport addJobResultLinks(final JobResultDTO jobResult) {
        JobResultResourceSupport support = new JobResultResourceSupport(jobResult);
        support.add(linkTo(methodOn(JobController.class).getJobStatus(jobResult.getJobId())).withRel("jobStatus"));
        support.add(linkTo(JobController.class).slash(jobResult.getJobId()).withSelfRel());
        return support;
    }

    @Getter
    @RequiredArgsConstructor
    private static class JobResultResourceSupport extends ResourceSupport {
        private final JobResultDTO jobResult;
    }

    @Getter
    private static class JobResourceSupport extends ResourceSupport {

        private Long jobId;

        private State state;

        public JobResourceSupport(final Job job) {
            this.jobId = job.getId();
            this.state = job.getState();
        }
    }
}
