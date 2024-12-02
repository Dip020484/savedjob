package com.linkedin.api.savedjob;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.linkedin.api.savedjob.model.Job;
import com.linkedin.api.savedjob.util.JsonFormatter;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JsonFormatterTest {

    private final JsonFormatter jsonFormatter = new JsonFormatter();

    @Test
    void testConvertToJobObjects_ValidJson() {
        // Arrange: Create a valid JSON input
        String json = """
        {
            "included": [
                {
                    "$type": "com.linkedin.voyager.dash.search.EntityResultViewModel",
                    "title": { "text": "Software Engineer" },
                    "primarySubtitle": { "text": "Tech Company" },
                    "secondarySubtitle": { "text": "New York, NY" },
                    "insightsResolutionResults": [
                        {
                            "simpleInsight": {
                                "title": { "text": "Posted 2d ago" }
                            }
                        }
                    ]
                }
            ]
        }
        """;

        // Act: Call the method
        List<Job> jobs = jsonFormatter.convertToJobObjects(json);

        // Assert: Verify the returned list
        assertNotNull(jobs, "Jobs list should not be null");
        assertEquals(1, jobs.size(), "There should be exactly one job");

        Job job = jobs.get(0);
        assertEquals("Software Engineer", job.getRole());
        assertEquals("Tech Company", job.getCompany());
        assertEquals("New York, NY", job.getLocation());
        assertEquals("2d ago", job.getApplied());
    }

    @Test
    void testConvertToJobObjects_MissingIncludedArray() {
        // Arrange: JSON without "included" array
        String json = "{ \"someOtherField\": [] }";

        // Act: Call the method
        List<Job> jobs = jsonFormatter.convertToJobObjects(json);

        // Assert: Verify that the result is null
        assertNull(jobs, "Jobs list should be null when 'included' is missing");
    }

    @Test
    void testConvertToJobObjects_InvalidJson() {
        // Arrange: Malformed JSON
        String json = "{  }";

        // Act & Assert: Method should not throw but handle gracefully
        List<Job> jobs = jsonFormatter.convertToJobObjects(json);
        assertNull(jobs, "Jobs list should be null for invalid JSON");
    }

    @Test
    void testConvertToJobObjects_EmptyJson() {
        // Arrange: Empty JSON
        String json = "{}";

        // Act: Call the method
        List<Job> jobs = jsonFormatter.convertToJobObjects(json);

        // Assert: Verify the result
        assertNull(jobs, "Jobs list should be null for empty JSON");
    }
}

