package com.linkedin.api.savedjob.exception;

import com.linkedin.api.savedjob.service.LinkedInService;

public class LinkedInWebException extends  RuntimeException{

    public LinkedInWebException(String message){
        super(message);
    }

    public LinkedInWebException(String message,Exception cause){
        super(message,cause);
    }

    public LinkedInWebException(Exception exception){
        super(exception);
    }
}
