package com.sbsatter.utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CsvFileReader {

    public List<List<String>> readLines(String filePath, char delimiter) {
        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(filePath));
            List<List<String>> data = fileReader.lines()
                    .map(line -> Arrays.stream(
                            line.split(String.valueOf(delimiter)) // break each line by delimiter
                    )
                            .collect(Collectors.toList())) // collect tokens into list
                    .collect(Collectors.toList()); // collect each list into data
            return data;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
