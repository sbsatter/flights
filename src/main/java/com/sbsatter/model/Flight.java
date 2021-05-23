package com.sbsatter.model;

import java.time.LocalDateTime;

public class Flight {
    private String source;
    private String destination;
    private LocalDateTime departure;
    private LocalDateTime arrival;
    private String flightNumber;
    private double price;
    private int bagsAllowed;
    private double bagPrice;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public LocalDateTime getDeparture() {
        return departure;
    }

    public void setDeparture(LocalDateTime departure) {
        this.departure = departure;
    }

    public LocalDateTime getArrival() {
        return arrival;
    }

    public void setArrival(LocalDateTime arrival) {
        this.arrival = arrival;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getBagsAllowed() {
        return bagsAllowed;
    }

    public void setBagsAllowed(int bagsAllowed) {
        this.bagsAllowed = bagsAllowed;
    }

    public double getBagPrice() {
        return bagPrice;
    }

    public void setBagPrice(double bagPrice) {
        this.bagPrice = bagPrice;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", departure=" + departure +
                ", arrival=" + arrival +
                ", flightNumber='" + flightNumber + '\'' +
                ", price=" + price +
                ", bagsAllowed=" + bagsAllowed +
                ", bagPrice=" + bagPrice +
                '}';
    }
}
