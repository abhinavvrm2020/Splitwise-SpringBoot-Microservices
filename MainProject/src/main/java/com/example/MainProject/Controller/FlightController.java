package com.example.MainProject.Controller;

import com.example.MainProject.Schema.Flight.Airport;
import com.example.MainProject.Schema.Flight.SearchFlight;
import com.example.MainProject.Service.FlightService;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("api/v1/flights")
public class FlightController {
    @Autowired
    public FlightService flightService;

    @GetMapping("searchAirport")
    public CompletableFuture<List<Airport>> searchAirport(
            @RequestParam String query
    ) {
        System.out.println(query);
        return flightService.searchAirportDetailsAsync(query);
    }
}
