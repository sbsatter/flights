package com.sbsatter.graph;

import com.sbsatter.model.Airport;
import com.sbsatter.model.Flight;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public interface Graph {

    void init(List<Flight> flights);

    Airport initAirport(String name);

    Collection<Airport> getAllAirports();

    default Set<Flight> getIncomingFlightsTo(Airport airport, LocalDateTime after) {
        return getIncomingFlightsTo(airport.getName(), after);
    }

    Set<Flight> getIncomingFlightsTo(String airport, LocalDateTime after);

    default Set<Flight> getOutgoingFlightsFrom(Airport airport, LocalDateTime after) {
            return getOutgoingFlightsFrom(airport.getName(), after);
    }
    Set<Flight> getOutgoingFlightsFrom(String airport, LocalDateTime after);

    void addIncomingFlightTo(String airport, Flight incomingFlight);

    void addOutgoingFlightTo(String airport, Flight outgoingFlight);

    Airport getAirportByName(String airport);
}
