package com.sbsatter;

import com.sbsatter.utils.CsvFileReader;
import com.sbsatter.utils.FlightObjectMapper;

import java.util.List;

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
        data.stream().map(flightMapper::map).forEach(System.out::println);
    }
}
