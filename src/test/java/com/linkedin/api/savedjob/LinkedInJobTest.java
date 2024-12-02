package com.linkedin.api.savedjob;


import com.linkedin.api.savedjob.controller.JobController;
import com.linkedin.api.savedjob.exception.LinkedInWebException;
import com.linkedin.api.savedjob.model.Job;
import com.linkedin.api.savedjob.util.LinkedinServiceTest;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.reactive.function.client.WebClient;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@SpringBootTest
public class LinkedInJobTest {
    @Autowired
    LinkedinServiceTest service;
    @Mock
    private WebClient.Builder webClientBuilder;
    @Mock
    private WebClient.RequestBodySpec requestBodySpec;

    @Mock
    private WebClient.ResponseSpec responseSpec;
    @Autowired
    private JobController jobConroller;

    @Mock
    HttpServletRequest httpServletRequest;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Set up WebClient mock
        when(webClientBuilder.baseUrl(anyString())).thenReturn(webClientBuilder);

        when(requestBodySpec.retrieve()).thenReturn(responseSpec);
    }


    @Test
    void testLinkedInJobApi_HappyPath() throws Exception {

        // Arrange

        List<Job> expectedJobList = new ArrayList<>();
        expectedJobList.add(buildJob("Software Engineer","TestCompany","Glassgow","3 weeks ago"));
        jobConroller = new JobController(service);
        List<Job> result = jobConroller.fetchAllMyJobListFromLinkedIn("Cookie");
        Assertions.assertEquals(result.get(0).getCompany(),expectedJobList.get(0).getCompany(),"Actual and Expected json are not matching");
        Assertions.assertEquals(result.get(0).getLocation(),expectedJobList.get(0).getLocation(),"Actual and Expected json are not matching");
        Assertions.assertEquals(result.get(0).getApplied(),expectedJobList.get(0).getApplied(),"Actual and Expected json are not matching");
    }

    @Test
    void testLinkedInJobApi_ThrowsExceptionWhenCookieIsNotProvided() throws Exception {

        // Arrange

        List<Job> expectedJobList = new ArrayList<>();
        expectedJobList.add(buildJob("Software Engineer","TestCompany","Glassgow","3 weeks ago"));
        jobConroller = new JobController(service);
        LinkedInWebException thrown = Assertions.assertThrows(
                LinkedInWebException.class,
                () -> jobConroller.fetchAllMyJobListFromLinkedIn(null), // This method internally calls mockedService.doSomething()
                "Expected doSomething() to throw a RuntimeException"
        );

        // Additional assertions on the exception
        Assertions.assertEquals("Cookie is empty", thrown.getMessage());
    }

    private Job buildJob(String role,String company,String location,String applied){
        return Job.builder()
                .role(role)
                .company(company)
                .location(location)
                .applied(applied)
                .build();
    }



}
