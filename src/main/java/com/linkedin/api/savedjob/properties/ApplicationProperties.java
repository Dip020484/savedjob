package com.linkedin.api.savedjob.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties
public class ApplicationProperties {

    @Value("${spring.application.name}")
    private String applicationName;
    @Value("${headers.authority}")
    private String authority;

    @Value("${headers.authorityValue}")
    private String authorityHeaderValue;


    @Value("${headers.method}")
    private String method;

    @Value("${headers.methodValue}")
    private String methodValue;

    @Value("${headers.path}")
    private String path;

    @Value("${headers.pathValue}")
    private String pathValue;


    @Value("${headers.scheme}")
    private String scheme;

    @Value("${headers.schemeValue}")
    private String schemeValue;


    @Value("${headers.accept}")
    private String accept;

    @Value("${headers.acceptValue}")
    private String acceptValue;

    @Value("${headers.acceptEncoding}")
    private String acceptEncoding;

    @Value("${headers.acceptEncodingValue}")
    private String acceptEncodingValue;

    @Value("${headers.acceptLanguage}")
    private String acceptLanguage;

    @Value("${headers.acceptLanguageValue}")
    private String acceptLanguageValue;

    @Value("${headers.csrfToken}")
    private String csrfToken;

    @Value("${headers.csrfTokenValue}")
    private String csrfTokenValue;

    @Value("${headers.priority}")
    private String priority;

    @Value("${headers.priorityValue}")
    private String priorityValue;

    @Value("${headers.referer}")
    private String referer;

    @Value("${headers.refererValue}")
    private String refererValue;

    @Value("${headers.xlipemMetadata}")
    private String xlipemMetadata;

    @Value("${headers.xlipemMetadataValue}")
    private String xlipemMetadataValue;

    @Value("${headers.xlitrack}")
    private String xlitrack;

    @Value("${headers.userAgent}")
    private String userAgent;

    @Value("${headers.userAgentValue}")
    private String userAgentValue;

    @Value("${request.urls.savedJobsUrl}")
    private String savedJobsUrl;

    @Value("${request.urls.inProgressJobsUrl}")
    private String inProgressJobsUrl;

    @Value("${request.urls.appliedJobsUrl}")
    private String appliedJobsUrl;

    @Value("${request.urls.archivedJobsUrl}")
    private String archivedJobsUrl;



}
