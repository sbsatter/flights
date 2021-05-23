package com.sbsatter.utils;

import com.sbsatter.model.Flight;

import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.sbsatter.utils.Utils.parseDateTime;

public class FlightObjectMapper {
    private final int sourceIdx = 0;
    private final int destinationIdx = 1;
    private final int departureIdx = 2;
    private final int arrivalIdx = 3;
    private final int flightNumberIdx = 4;
    private final int priceIdx = 5;
    private final int bagsAllowedIdx = 6;
    private final int bagPriceIdx = 7;

    public Flight map(List<String> flightData) {
        Flight flight = new Flight();
        flight.setSource(flightData.get(sourceIdx));
        flight.setDestination(flightData.get(destinationIdx));
        flight.setDeparture(parseDateTime(flightData.get(departureIdx)));
        flight.setArrival(parseDateTime(flightData.get(arrivalIdx)));
        flight.setFlightNumber(flightData.get(flightNumberIdx));
        flight.setPrice(Double.parseDouble(flightData.get(priceIdx)));
        flight.setBagsAllowed(Integer.parseInt(flightData.get(bagsAllowedIdx)));
        flight.setBagPrice(Double.parseDouble(flightData.get(bagPriceIdx)));

        return flight;
    }
}
