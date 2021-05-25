package com.sbsatter;

import com.sbsatter.graph.Graph;
import com.sbsatter.graph.GraphImpl;
import com.sbsatter.model.Airport;
import com.sbsatter.model.Flight;
import com.sbsatter.model.Trip;
import com.sbsatter.utils.CsvFileReader;
import com.sbsatter.utils.FlightObjectMapper;
import com.sbsatter.utils.Utils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        CsvFileReader fileReader = new CsvFileReader();

        // todo move filePath, delimiter to args
        List<List<String>> data = fileReader.readLines("./flights.csv", ',');

        List<String> columnNames = data.remove(0);

        FlightObjectMapper flightMapper = new FlightObjectMapper();
        List<Flight> allFlights = data.stream().map(flightMapper::map).collect(Collectors.toList());
        Graph graph = new GraphImpl();
        graph.init(allFlights);

        List<List<Flight>> combinations = new ArrayList<>();
        findCombinations(graph, combinations);

        List<List<Flight>> withNoBaggage = combinations.stream().filter(flights -> flights.size() > 0).collect(Collectors.toList());
        List<List<Flight>> withOneBaggage = combinations.stream().map(flights -> flights.stream().takeWhile(flight -> flight.getBagsAllowed() >= 1).collect(Collectors.toList())).filter(flights -> flights.size() > 0).collect(Collectors.toList());
        List<List<Flight>> withTwoBaggage = combinations.stream().map(flights -> flights.stream().takeWhile(flight -> flight.getBagsAllowed() > 1).collect(Collectors.toList())).filter(flights -> flights.size() > 0).collect(Collectors.toList());

        prepareReport(withNoBaggage, 0);
        prepareReport(withOneBaggage,1);
        prepareReport(withTwoBaggage,2);
    }

    private static void prepareReport(List<List<Flight>> flights, int bagCount) {
        System.out.println("#########################################################################################");
        System.out.printf("Passengers with %d bags\n", bagCount);
        List<Trip> trips = prepareTrips(flights, bagCount);
        for (int i = 0; i < trips.size(); i++) {
            System.out.printf("---- TRIP %d ----\n", i);
            System.out.println(trips.get(i));
        }
    }

    private static List<Trip> prepareTrips(List<List<Flight>> allPossibleFlights, int bagCount) {
        return allPossibleFlights.stream().map(flights -> {
            Trip trip =  new Trip();
            double totalCost = flights.stream().mapToDouble(flight -> flight.getPrice() + bagCount * flight.getBagPrice()).sum();
            trip.setFlights(flights);
            trip.setCost(totalCost);
            return trip;
        }).collect(Collectors.toList());
    }

    private static void findCombinations(Graph graph, List<List<Flight>> combinations) {
        Collection<Airport> airports = graph.getAllAirports();
        airports.forEach(airport -> {
            airport.getOutgoingFlights().forEach(initialFlight -> {
                Map<String, Integer> visited = new HashMap<>();
                visited.put(initialFlight.getSource(), visited.size());
                List<Flight> currentFlights = new ArrayList<>();
                findCombinationsStartingFrom(initialFlight, visited, currentFlights, combinations, graph);
            });
        });
    }

    private static void findCombinationsStartingFrom(Flight flight, Map<String, Integer> visited, List<Flight> flightsSoFar, List<List<Flight>> combinations, Graph graph) {
        List<Flight> currentFlights = new ArrayList<>(flightsSoFar);
        if (visited.containsKey(flight.getDestination())) {
            // this city is already visited
            if (visited.get(flight.getDestination()) == 0) {
                // this destination is the first city, hence trip must complete.
                currentFlights.add(flight);
                combinations.add(currentFlights);
            }
            return;
        }
        // city is not visited yet
        currentFlights.add(flight);
        combinations.add(currentFlights);
        Map<String, Integer> updatedVisited = new HashMap<>(visited);
        updatedVisited.put(flight.getDestination(), visited.size());

        graph.getOutgoingFlightsFrom(flight.getDestination(), flight.getArrival())
                .forEach(nextFlight -> findCombinationsStartingFrom(nextFlight, updatedVisited, currentFlights, combinations, graph));

    }
}
