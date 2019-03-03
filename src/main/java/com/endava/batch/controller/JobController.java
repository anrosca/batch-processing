package com.endava.batch.controller;

import java.net.URI;
import java.util.concurrent.Callable;

import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.endava.batch.controller.hateoas.HateoasResourceAssembler;
import com.endava.batch.request.CalculationRequest;
import com.endava.batch.service.FactorialService;
import com.endava.batch.service.JobService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/factorials")
@RequiredArgsConstructor
public class JobController {

    private static final String LOCATION_HEADER_NAME = "Location";

    private final JobService jobService;

    private final FactorialService factorialService;

    private final HateoasResourceAssembler resourceAssembler;

    @PostMapping
    public ResponseEntity<ResourceSupport> startJob(@RequestBody CalculationRequest request) {
        final Callable task = () -> factorialService.calculateFor(request.getValue());
        long jobId = jobService.startJob(task);
        final URI location = MvcUriComponentsBuilder.fromController(getClass())
                .path("/{jobId}")
                .build(jobId);
        return ResponseEntity.status(HttpStatus.ACCEPTED)
                .header(LOCATION_HEADER_NAME, location.toString())
                .body(resourceAssembler.toJobResource(jobId));
    }

    @GetMapping("{jobId}/state")
    public ResponseEntity<ResourceSupport> getJobStatus(@PathVariable("jobId") long jobId) {
        return ResponseEntity.ok(resourceAssembler.addJobStatusLinks(jobService.findJobBy(jobId)));
    }

    @GetMapping("{jobId}")
    public ResponseEntity<ResourceSupport> getJobResult(@PathVariable("jobId") long jobId) {
        return ResponseEntity.ok(resourceAssembler.addJobResultLinks(jobService.findJobResultOf(jobId)));
    }
}
