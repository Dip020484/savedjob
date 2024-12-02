package com.linkedin.api.savedjob.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public class Job {
    @JsonProperty("Role")
    private String role;

    @JsonProperty("Company")
    private String company;

    @JsonProperty("Location")
    private String location;

    @JsonProperty("Applied")
    private String applied;

    // Constructor
    public Job(String role, String company, String location, String applied) {
        this.role = role;
        this.company = company;
        this.location = location;
        this.applied = applied;
    }

    // Getters and Setters
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getApplied() {
        return applied;
    }

    public void setApplied(String applied) {
        this.applied = applied;
    }
}
