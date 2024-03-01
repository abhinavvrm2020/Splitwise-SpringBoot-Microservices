package com.example.MainProject.Service;

import com.example.MainProject.Schema.Flight.Airport;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Response;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class FlightService {
    private final String rapidApiKey = "f983b8da1cmsh5821288506d498dp1c7e7bjsn5a80c4dd5abb";
    private final String rapidApiHost = "booking-com15.p.rapidapi.com";

    private AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient();

    public CompletableFuture<List<Airport>> searchAirportDetailsAsync(String query) {
        String url = "https://booking-com15.p.rapidapi.com/api/v1/flights/searchDestination?query=" + query;
        System.out.println(url);
        ListenableFuture<Response> future = asyncHttpClient.prepareGet(url)
                .setHeader("X-RapidAPI-Key", rapidApiKey)
                .setHeader("X-RapidAPI-Host", rapidApiHost)
                .execute();
        System.out.println("here");
        // Transform from ListeneableFuture to CompletableFuture
        CompletableFuture<Response> responseFuture = future.toCompletableFuture();
        System.out.println("here");
        List<Airport> airportList = new ArrayList<>();
        // Response handling & mapping: This to be replaced by your flight parsing logic
        return responseFuture.thenApply(response -> {
            return airportList;
        });
    }
}
