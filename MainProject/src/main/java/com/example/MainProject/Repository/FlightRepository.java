package com.example.MainProject.Repository;

import com.example.MainProject.Schema.Flight.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FlightRepository extends JpaRepository<Flight,Integer> {
}
