package com.linkedin.api.savedjob.util;

import com.linkedin.api.savedjob.model.Job;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component

public class JsonFormatter {
    public List<Job> convertToJobObjects(String json) {
        // Initialize Jackson ObjectMapper
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT); // For pretty printing

        // List to hold simplified Job objects
        List<Job> jobs = new ArrayList<>();

        try {

            JsonNode root = mapper.readTree(json);

            // Navigate to the "included" array
            JsonNode included = root.path("included");

            if (included.isMissingNode() || !included.isArray()) {

                return null;
            }




            for (JsonNode entry : included) {
                // Check if the entry is a job posting
                String type = entry.path("$type").asText("");
                if ("com.linkedin.voyager.dash.search.EntityResultViewModel".equals(type)) {
                    // Extract Role from "title.text"
                    String role = entry.path("title").path("text").asText("").trim();
                    if (role.isEmpty()) {

                        continue; // Skip to the next entry
                    } else {

                    }

                    // Extract Company from "primarySubtitle.text"
                    String company = entry.path("primarySubtitle").path("text").asText("").trim();
                    if (company.isEmpty()) {

                        company = "N/A";
                    } else {

                    }

                    // Extract Location from "secondarySubtitle.text"
                    String location = entry.path("secondarySubtitle").path("text").asText("").trim();
                    if (location.isEmpty()) {

                        location = "N/A";
                    }

                    // Extract Applied information from "insightsResolutionResults"
                    String applied = "N/A"; // Default value
                    JsonNode insights = entry.path("insightsResolutionResults");
                    if (insights.isMissingNode()) {

                    } else if (insights.isArray()) {
                        for (JsonNode insight : insights) {
                            JsonNode simpleInsight = insight.path("simpleInsight");
                            if (simpleInsight.isMissingNode()) {
                                continue;
                            }
                            String text = simpleInsight.path("title").path("text").asText("").trim();
                            if (text.toLowerCase().contains("posted")) {
                                // Extract the time ago (e.g., "2d ago")
                                applied = text.replace("Posted", "").trim();

                                break; // Stop after finding the first relevant insight
                            }
                        }
                    }

                    // If 'Applied' information wasn't found, log a message
                    if ("N/A".equals(applied)) {

                    }

                    // Create a Job object and add it to the list
                    Job job = new Job(role, company, location, applied);
                    jobs.add(job);
                }
            }

            // Convert the list of jobs to JSON
            String outputJson = mapper.writeValueAsString(jobs);

            // Print the simplified JSON


            // Optionally, write the output JSON to a file in resources or another directory
            // mapper.writeValue(new File("output.json"), jobs);

        } catch (JsonProcessingException e) {

            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        }

        return jobs;
    }

}
