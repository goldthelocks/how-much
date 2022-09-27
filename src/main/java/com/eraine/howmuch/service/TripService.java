package com.eraine.howmuch.service;

import static net.logstash.logback.argument.StructuredArguments.kv;

import com.eraine.howmuch.mapper.TapPairToTripMapper;
import com.eraine.howmuch.mapper.TripToCsvRowMapper;
import com.eraine.howmuch.model.api.TapPair;
import com.eraine.howmuch.model.api.TripFileHeader;
import com.eraine.howmuch.model.jpa.Fare;
import com.eraine.howmuch.model.jpa.Trip;
import com.eraine.howmuch.repository.FareRepository;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TripService {

    private final FareRepository fareRepository;

    public TripService(FareRepository fareRepository) {
        this.fareRepository = fareRepository;
    }

    public List<Trip> createTrips(List<TapPair> pairs) {
        // load fares into memory since there aren't much data in fare table
        List<Fare> fares = fareRepository.findAll();

        log.info("Generating trips from tap pairs. {}", kv("totalPairs", pairs.size()));

        List<Trip> trips = pairs.stream()
            .map(new TapPairToTripMapper(fares))
            .sorted(Comparator.comparing(Trip::getStarted))
            .collect(Collectors.toList());

        // we can save to db if we want
        log.info("Completed generating trips. {}", kv("totalTrips", trips.size()));

        return trips;
    }

    public void exportToCsv(List<Trip> trips, Path outputPath) throws IOException {
        log.info("Exporting trips to file. {}", kv("totalTrips", trips.size()));

        CSVFormat format = CSVFormat.DEFAULT
            .builder()
            .setHeader(TripFileHeader.class)
            .setNullString(StringUtils.EMPTY)
            .setDelimiter(", ")
            .build();

        try (CSVPrinter printer = new CSVPrinter(Files.newBufferedWriter(outputPath, StandardCharsets.UTF_8), format)) {
            printer.printRecords(trips
                .stream()
                .map(new TripToCsvRowMapper())
                .collect(Collectors.toList()));
            log.info("Trips exported to file! Check out folder.");
        } catch (IOException ex) {
            log.warn("Encountered error while writing to file.", ex);
            throw ex;
        }
    }

}
