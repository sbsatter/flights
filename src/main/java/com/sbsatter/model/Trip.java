package com.sbsatter.model;

import java.util.List;

public class Trip {
    private List<Flight> flights;
    private double cost;

    public List<Flight> getFlights() {
        return flights;
    }

    public void setFlights(List<Flight> flights) {
        this.flights = flights;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return String.format("Trip[cost: %.2f, flights=%s]", cost, flights);
    }
}
