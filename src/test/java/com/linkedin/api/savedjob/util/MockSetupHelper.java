package com.linkedin.api.savedjob.util;

import static org.springframework.test.web.client.match.MockRestRequestMatchers.method;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.requestTo;
import static org.springframework.test.web.client.response.MockRestResponseCreators.withStatus;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.client.ExpectedCount;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.test.web.servlet.htmlunit.MockMvcWebClientBuilder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

public class MockSetupHelper {

    private MockRestServiceServer mockServer;


    public MockSetupHelper setUpMockServer(WebClient webclient) {
        //mockServer = MockMvcWebClientBuilder.mockMvcSetup();
        System.out.println(
                "\n"
                        + "┏━┳━┓   ┏┓┏━━┓ ┏┓    ┏┓┏┓\n"
                        + "┃┃┃┃┣━┳━┫┣┫━━╋━┫┗┳┳┳━┫┗┛┣━┳┓┏━┳━┳┳┓\n"
                        + "┃┃┃┃┃╋┃━┫━╋━━┃┻┫┏┫┃┃╋┃┏┓┃┻┫┗┫╋┃┻┫┏┛\n"
                        + "┗┻━┻┻━┻━┻┻┻━━┻━┻━┻━┫┏┻┛┗┻━┻━┫┏┻━┻┛\n"
                        + "╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋╋┗┛╋╋╋╋╋╋╋┗┛  v.1 \n"
                        + "Mock Server Started Successfully... \n"
                        + "------------------------------------ \n");

        return this;
    }

    public void resetServer() {
        mockServer.reset();
    }

    /**
     * Post Endpoint with a custom response.
     *
     * @param uri
     * @param response
     * @param status
     */


    /**
     * Post request with no body.
     *
     * @param uri
     * @param status
     */
    public void setUpPostEndpoint(String uri, HttpStatus status) {
        try {
            mockServer
                    .expect(ExpectedCount.manyTimes(), requestTo(new URI(uri)))
                    .andExpect(method(HttpMethod.GET))
                    .andRespond(withStatus(status));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    /**
     * Custom method with no body.
     *
     * @param uri
     * @param method
     * @param status
     */
    public void setUpEndpointWithNoResponseBody(String uri, HttpMethod method, HttpStatus status) {
        try {

            mockServer
                    .expect(ExpectedCount.manyTimes(), requestTo(new URI(uri)))
                    .andExpect(method(method))
                    .andRespond(withStatus(status));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }




    public static JSONArray readJSONArray(String fileName) throws JSONException, IOException {
        return new JSONArray(readFileAndReturnString(fileName));
    }

    public static JSONObject readJSONObject(String fileName) throws JSONException, IOException {
        return new JSONObject(readFileAndReturnString(fileName));
    }

    private static String readFileAndReturnString(String fileName) throws IOException {
        BufferedReader br = null;

        File file = new File("./src/test/resources/" + fileName + ".json");

        br = new BufferedReader(new FileReader(file));

        String line = null;
        StringBuffer fullPayload = new StringBuffer();

        try {
            while ((line = br.readLine()) != null) {
                fullPayload.append(line);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fullPayload.toString();
    }
}

