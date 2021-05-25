package com.sbsatter.model;

import com.sbsatter.utils.DatePair;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class Airport {

    private String name;
    // todo consider using TreeSet here!
    private Set<Flight> incomingFlights;
    private Set<Flight> outgoingFlights;

    public Airport() {
        incomingFlights = new TreeSet<>();
        outgoingFlights = new TreeSet<>();
    }

    public void addIncomingFlight(Flight incomingFlight) {
        this.incomingFlights.add(incomingFlight);
    }

    public void addOutgoingFlight(Flight outgoingFlight) {
        this.outgoingFlights.add(outgoingFlight);
    }

    public Set<Flight> getIncomingFlightsBetween(DatePair datePair) {
        Flight firstFlight = new Flight();
        firstFlight.setDeparture(datePair.getStart());
        Flight lastFlight = new Flight();
        lastFlight.setDeparture(datePair.getEnd());
        return ((TreeSet) incomingFlights).subSet(firstFlight, true, lastFlight, true);
    }

    public Set<Flight> getOutgoingFlightsBetween(DatePair datePair) {
        Flight firstFlight = new Flight();
        firstFlight.setDeparture(datePair.getStart());
        Flight lastFlight = new Flight();
        lastFlight.setDeparture(datePair.getEnd());
        return ((TreeSet) outgoingFlights).subSet(firstFlight, true, lastFlight, true);
    }
    // getter and setter


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Flight> getIncomingFlights() {
        return incomingFlights;
    }

    public void setIncomingFlights(Set<Flight> incomingFlights) {
        this.incomingFlights = incomingFlights;
    }

    public Set<Flight> getOutgoingFlights() {
        return outgoingFlights;
    }

    public void setOutgoingFlights(Set<Flight> outgoingFlights) {
        this.outgoingFlights = outgoingFlights;
    }

    @Override
    public String toString() {
        return "Airport{" +
                "name='" + name + '\'' +
                ", incomingFlights=" + incomingFlights +
                ", outgoingFlights=" + outgoingFlights +
                '}';
    }
}
