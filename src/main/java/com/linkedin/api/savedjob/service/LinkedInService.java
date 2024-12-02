package com.linkedin.api.savedjob.service;


import com.linkedin.api.savedjob.model.Job;
import com.linkedin.api.savedjob.properties.ApplicationProperties;
import com.linkedin.api.savedjob.util.JsonFormatter;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static com.linkedin.api.savedjob.constant.JobAPIHeaderConstant.*;
import static org.springframework.http.HttpHeaders.REFERER;

@Service
@Component
public class LinkedInService {

    private final WebClient webClient;
    private final ApplicationProperties applicationProperties;
    private final JsonFormatter jsonFormatter;

    @Autowired
    public LinkedInService(WebClient webClient, ApplicationProperties applicationProperties,JsonFormatter jsonFormatter){
        this.webClient = webClient;
        this.applicationProperties = applicationProperties;
        this.jsonFormatter = jsonFormatter;
    }

    @CircuitBreaker(name = "linkedInCircuitBreaker", fallbackMethod = "fallbackResponse")
    public List<Job> getSavedJobDetails(String cookie){

        Mono<ResponseEntity<String>> savedJobsMono;
        Mono<ResponseEntity<String>> inProgressJobsMono;
        Mono<ResponseEntity<String>> inAppliedJobsMono;
        Mono<ResponseEntity<String>> archivedJobsJobMono;

        List<Job> allSavedJobList = new ArrayList<>();

        savedJobsMono = getJobsResponseEntity(cookie,applicationProperties.getSavedJobsUrl());
        inProgressJobsMono = getJobsResponseEntity(cookie,applicationProperties.getInProgressJobsUrl());
        inAppliedJobsMono = getJobsResponseEntity(cookie,applicationProperties.getAppliedJobsUrl());
        archivedJobsJobMono = getJobsResponseEntity(cookie,applicationProperties.getArchivedJobsUrl());


        List<Job> savedJobList = convertAndRetrieveSavedJobList(savedJobsMono);
        List<Job> inProgressJobsList = convertAndRetrieveInProgressJobList(inProgressJobsMono);
        List<Job> appliedJobList = convertAndRetrieveAppliedJobList(inAppliedJobsMono);
        List<Job> archivedJobList = convertAndRetrieveArchiveJobList(archivedJobsJobMono);

        allSavedJobList.addAll(savedJobList);
        allSavedJobList.addAll(inProgressJobsList);
        allSavedJobList.addAll(appliedJobList);
        allSavedJobList.addAll(archivedJobList);


        return allSavedJobList;

    }

    public String fallbackResponse(Throwable throwable) {
        return "LinkedIn is down for some maintenance activity and trying to process once it will be up again..."+throwable.getMessage();
    }

    public List<Job> convertAndRetrieveArchiveJobList(Mono<ResponseEntity<String>> archivedJobsJobMono) {
        return jsonFormatter.convertToJobObjects(archivedJobsJobMono.block().getBody());
    }

    public List<Job> convertAndRetrieveAppliedJobList(Mono<ResponseEntity<String>> inAppliedJobsMono) {
        return jsonFormatter.convertToJobObjects(inAppliedJobsMono.block().getBody());
    }

    public List<Job> convertAndRetrieveInProgressJobList(Mono<ResponseEntity<String>> inProgressJobsMono) {
        return jsonFormatter.convertToJobObjects(inProgressJobsMono.block().getBody());
    }

    public List<Job> convertAndRetrieveSavedJobList(Mono<ResponseEntity<String>> savedJobsMono) {
        return jsonFormatter.convertToJobObjects(savedJobsMono.block().getBody());
    }

    public Mono<ResponseEntity<String>> getJobsResponseEntity(String cookie,String url) {
        return getResponseEntityMono(cookie, url);
    }

    public Mono<ResponseEntity<String>> getResponseEntityMono(String cookie, String url) {
        HttpHeaders headers = getHttpHeaders();
        return webClient.get()
                .uri(url) // Replace with your GraphQL API URL
                .headers(httpHeaders -> {
                    httpHeaders.addAll(headers);
                    //if (cookies != null) {
                    httpHeaders.add(COOKIE, cookie);
                    // }
                })

                .retrieve()
                .toEntity(String.class);
    }

    private HttpHeaders getHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();

        headers.add(HEADER_ATTRIBUTE_AUTHORITY,applicationProperties.getAuthorityHeaderValue());
        headers.add(METHOD,applicationProperties.getMethodValue());
        headers.add(HEADER_ATTRIBUTE_PATH,applicationProperties.getPathValue());
        headers.add(SCHEME,applicationProperties.getSchemeValue());
        //headers.add("Accept","application/vnd.linkedin.normalized+json+2.1");
        headers.add(ACCEPT,applicationProperties.getAcceptValue());
        headers.add(ACCEPT_ENCODING,applicationProperties.getAcceptEncodingValue());
        headers.add(ACCEPT_LANGUAGE,applicationProperties.getAcceptLanguageValue());
        headers.add(CSRF_TOKEN,applicationProperties.getCsrfTokenValue());
        headers.add(XLIPEM_METADATA,applicationProperties.getXlipemMetadataValue());
        headers.add(XLI_TRACK,"{\"clientVersion\":\"1.13.27083\",\"mpVersion\":\"1.13.27083\",\"osName\":\"web\",\"timezoneOffset\":0,\"timezone\":\"Europe/London\",\"deviceFormFactor\":\"DESKTOP\",\"mpName\":\"voyager-web\",\"displayDensity\":2.5,\"displayWidth\":3840,\"displayHeight\":2160}/London\",\"deviceFormFactor\":\"DESKTOP\",\"mpName\":\"voyager-web\",\"displayDensity\":2.5,\"displayWidth\":3840,\"displayHeight\":2160}");
        headers.add(USER_AGENT,"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/131.0.0.0 Safari/537.36");
        headers.add(REFERER,applicationProperties.getRefererValue());
        return headers;
    }


}
