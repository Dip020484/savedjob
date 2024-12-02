package com.linkedin.api.savedjob.util;

import com.linkedin.api.savedjob.model.Job;
import com.linkedin.api.savedjob.properties.ApplicationProperties;
import com.linkedin.api.savedjob.service.LinkedInService;
import org.mockito.Mock;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.CoreSubscriber;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static reactor.core.publisher.Mono.when;

@Component
public class LinkedinServiceTest extends LinkedInService {




    public LinkedinServiceTest(WebClient webClient, ApplicationProperties applicationProperties, JsonFormatter jsonFormatter) {
        super(webClient, applicationProperties, jsonFormatter);
    }

    public Mono<ResponseEntity<String>> getJobsResponseEntity(String cookie, String url) {
        return Mono.empty();

    }

    public List<Job> convertAndRetrieveArchiveJobList(Mono<ResponseEntity<String>> archivedJobsJobMono) {
        return new ArrayList<>();
    }

    public List<Job> convertAndRetrieveAppliedJobList(Mono<ResponseEntity<String>> inAppliedJobsMono) {
        return new ArrayList<>();
    }

    public List<Job> convertAndRetrieveInProgressJobList(Mono<ResponseEntity<String>> inProgressJobsMono) {
        return new ArrayList<>();
    }

    public List<Job> convertAndRetrieveSavedJobList(Mono<ResponseEntity<String>> savedJobsMono) {

        Job job = Job.builder()
                .role("Software Engineer")
                .company("TestCompany")
                .location("Glassgow")
                .applied("3 weeks ago")
                .build();

        List<Job> jobList = new ArrayList<>();

        jobList.add(job);

        return jobList;

    }


}
