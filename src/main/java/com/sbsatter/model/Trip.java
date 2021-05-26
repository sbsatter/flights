package com.sbsatter.model;

import java.time.LocalDateTime;
import java.util.List;

public class Trip {
    private List<Flight> flights;
    private LocalDateTime begins;
    private LocalDateTime ends;
    private double cost;

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public LocalDateTime getBegins() {
        return begins;
    }

    public void setBegins(LocalDateTime begins) {
        this.begins = begins;
    }

    public LocalDateTime getEnds() {
        return ends;
    }

    public void setEnds(LocalDateTime ends) {
        this.ends = ends;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return String.format("Trip[cost: %.2f, starts=%s, ends=%s]\nflights=%s", cost, begins, ends, flights);
    }
}
