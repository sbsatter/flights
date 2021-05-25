package com.sbsatter.graph;

import com.sbsatter.model.Airport;
import com.sbsatter.model.Flight;
import com.sbsatter.utils.Utils;

import java.time.LocalDateTime;
import java.util.*;

public class GraphImpl implements Graph {

    private Map<String, Airport> graph;

    @Override
    public void init(List<Flight> flights) {
        if (graph == null) {
            graph = new HashMap<>();
        } else {
            throw new UnsupportedOperationException("Graph has already been initialized");
        }
        flights.forEach(flight -> graph.computeIfAbsent(flight.getSource(), this::initAirport).addOutgoingFlight(flight));
        flights.forEach(flight -> graph.computeIfAbsent(flight.getDestination(), this::initAirport).addIncomingFlight(flight));
        flights.forEach(flight -> {
            flight.setSourceAirport(graph.get(flight.getSource()));
            flight.setDestinationAirport(graph.get(flight.getDestination()));
        });
    }

    @Override
    public Airport initAirport(String name) {
        Airport airport = new Airport();
        airport.setName(name);
        return airport;
    }

    @Override
    public Collection<Airport> getAllAirports() {
        return graph.values();
    }

    @Override
    public Set<Flight> getIncomingFlightsTo(String airport, LocalDateTime after) {
        Set<Flight> flights;
        if (after == null) {
            flights = graph.get(airport).getIncomingFlights();
        } else {
            flights = graph.get(airport).getIncomingFlightsBetween(Utils.getRequiredDatePair(after));
        }

        return flights;
    }

    @Override
    public Set<Flight> getOutgoingFlightsFrom(String airport, LocalDateTime after) {
        Set<Flight> flights;
        if (after == null) {
            flights = graph.get(airport).getOutgoingFlights();
        } else {
            flights = graph.get(airport).getOutgoingFlightsBetween(Utils.getRequiredDatePair(after));
        }

        return flights;
    }

    @Override
    public void addIncomingFlightTo(String airport, Flight incomingFlight) {

    }

    @Override
    public void addOutgoingFlightTo(String airport, Flight outgoingFlight) {

    }

    @Override
    public Airport getAirportByName(String airport) {
        return null;
    }
}
