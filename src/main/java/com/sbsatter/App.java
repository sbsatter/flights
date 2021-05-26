package com.sbsatter;

import com.sbsatter.graph.Graph;
import com.sbsatter.graph.GraphImpl;
import com.sbsatter.model.Airport;
import com.sbsatter.model.Flight;
import com.sbsatter.model.Trip;
import com.sbsatter.utils.CsvFileReader;
import com.sbsatter.utils.FlightObjectMapper;

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

        String fileName = System.getProperty("filename");

        if (fileName == null) {
            System.err.println("Please specify filename with -Dfilename <path-to-your-file>");
            System.exit(128);
        }

        String delimiter = System.getProperty("delimiter", ",");

        if (!(delimiter.trim().length() == 1 && delimiter.matches("^[,;]$"))) {
            System.err.println("Please specify a 1-character string delimiter between ',' and ';'");
            System.exit(128);
        }

        List<List<String>> data = fileReader.readLines(fileName, delimiter.trim().charAt(0));

        // the first row is the column names, need to remove them from data.
        List<String> columnNames = data.remove(0);

        // map extracted flights to Flight POJO
        FlightObjectMapper flightMapper = new FlightObjectMapper();
        List<Flight> allFlights = data.stream().map(flightMapper::map).collect(Collectors.toList());

        // Create a graph, use graph interface to define possible graph usage
        Graph graph = new GraphImpl();
        graph.init(allFlights);

        List<List<Flight>> combinations = new ArrayList<>(); // all combinations are accumulated here
        findCombinations(graph, combinations);

        // filter on bagsAllowed to ensure passengers do not carry excess baggage
        List<List<Flight>> withNoBaggage = combinations.stream().filter(flights -> flights.size() > 0).collect(Collectors.toList());
        List<List<Flight>> withOneBaggage = combinations.stream().map(flights -> flights.stream().takeWhile(flight -> flight.getBagsAllowed() >= 1).collect(Collectors.toList())).filter(flights -> flights.size() > 0).collect(Collectors.toList());
        List<List<Flight>> withTwoBaggage = combinations.stream().map(flights -> flights.stream().takeWhile(flight -> flight.getBagsAllowed() > 1).collect(Collectors.toList())).filter(flights -> flights.size() > 0).collect(Collectors.toList());

        // prepare report
        prepareReport(withNoBaggage, 0);
        prepareReport(withOneBaggage,1);
        prepareReport(withTwoBaggage,2);
    }

    private static void prepareReport(List<List<Flight>> flights, int bagCount) {
        System.out.println("#########################################################################################");
        System.out.printf("Passengers with %d bags [Total Trips: %d]\n", bagCount, flights.size());
        List<Trip> trips = prepareTrips(flights, bagCount);
        for (int i = 0; i < trips.size(); i++) {
            System.out.printf("---- TRIP %d ----\n", i);
            System.out.println(trips.get(i));
        }
    }

    /**
     * Get a list of {@code Trip} for all possible flights for passengers with bag count
     * @param allPossibleFlights (multiple) combination of flights
     * @param bagCount number of bags passenger will carry
     * @return List of possible trips
     */
    private static List<Trip> prepareTrips(List<List<Flight>> allPossibleFlights, int bagCount) {
        return allPossibleFlights.stream().map(flights -> {
            Trip trip =  new Trip();
            double totalCost = flights.stream().mapToDouble(flight -> flight.getPrice() + bagCount * flight.getBagPrice()).sum();
            trip.setFlights(flights);
            trip.setBegins(flights.get(0).getDeparture());
            trip.setEnds(flights.get(flights.size() - 1).getArrival());
            trip.setCost(totalCost);
            return trip;
        }).collect(Collectors.toList());
    }

    /**
     * Find all possible combinations in a recursive manner (with no memoization)
     * @param graph The graph on which to calculate combinations on
     * @param combinations The container to hold all the combinations of flights
     */
    private static void findCombinations(Graph graph, List<List<Flight>> combinations) {
        Collection<Airport> airports = graph.getAllAirports();
        airports.forEach(airport -> {
            graph.getOutgoingFlightsFrom(airport, null).forEach(initialFlight -> {
                Map<String, Integer> visited = new HashMap<>();
                visited.put(initialFlight.getSource(), visited.size());
                List<Flight> currentFlights = new ArrayList<>();
                findCombinationsStartingFrom(initialFlight, visited, currentFlights, combinations, graph);
            });
        });
    }

    /**
     * Recursive implementation to find all possible combinations given an initial flight @param flight
     * and set of already visited cities, and the list of flights needed so far to reach current airport
     * @param flight initial flight
     * @param visited map of visited cities, where (key, value) => (airport, index of the airport visited in current trip)
     * @param flightsSoFar List of flights taken so far
     * @param combinations The container to hold all the combinations of flights
     * @param graph The graph on which to calculate combinations on
     */
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
