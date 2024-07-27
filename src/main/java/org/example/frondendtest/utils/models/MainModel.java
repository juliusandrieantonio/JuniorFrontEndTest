package org.example.frondendtest.utils.models;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.frondendtest.utils.constants.SingletonInstance;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

public class MainModel {
    private static final int NUM_OF_TIMES = 15;
    private ObjectMapper objectMapper;
    private final HttpClient client;
    private final HttpRequest request;
    private HttpResponse<String> response;
    // constructor to get the input and format that the user want
    public MainModel() {
        SingletonInstance singletonInstance = SingletonInstance.getInstance();
        objectMapper = singletonInstance.getObjectMapper();
        client = HttpClient.newHttpClient();
        request = HttpRequest.newBuilder()
                .uri(URI.create("https://bored-api.appbrewery.com/random"))
                .build();
    }

    // getting the data from the api
    public ArrayList<RandomDataModel> getData() throws IOException, InterruptedException {
        int i = 0;
        ArrayList<RandomDataModel> models = new ArrayList<>();
        while (i < NUM_OF_TIMES) {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // error handling
            if (response.body().equals("Too many requests, please try again later.")) {
                i++;
                continue;
            }
            RandomDataModel model = objectMapper.readValue(response.body(), RandomDataModel.class);
            System.out.println(model.toString());
            models.add(model);
            i++;
        }
        return models;
    }
}
