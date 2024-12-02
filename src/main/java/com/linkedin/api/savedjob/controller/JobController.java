package com.linkedin.api.savedjob.controller;


import com.linkedin.api.savedjob.exception.LinkedInWebException;
import com.linkedin.api.savedjob.model.Job;
import com.linkedin.api.savedjob.service.LinkedInService;
import io.netty.util.internal.StringUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;



@RestController
@RequestMapping("/linkedIn")
public class JobController {

    private final LinkedInService linkedInService;


    @Autowired
    public JobController(LinkedInService linkedInService) {
       this.linkedInService = linkedInService;
    }

    @GetMapping("/myjobs")
    public List<Job> fetchAllMyJobListFromLinkedIn(@RequestParam String cookie) {
        if(StringUtil.isNullOrEmpty(cookie)){
            throw new LinkedInWebException("Cookie is empty");
        }
        return linkedInService.getSavedJobDetails(cookie);
    }


}
