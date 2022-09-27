package com.eraine.howmuch;

import com.eraine.howmuch.model.api.FileLocation;
import com.eraine.howmuch.model.api.TapPair;
import com.eraine.howmuch.service.TapService;
import com.eraine.howmuch.service.TripService;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class Application implements CommandLineRunner {

    private final TapService tapService;
    private final TripService tripService;

    public Application(TapService tapService, TripService tripService) {
        this.tapService = tapService;
        this.tripService = tripService;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(String... args) {
        log.info("Calculating bus fares...");

        try {
            List<TapPair> pairs = tapService.pairTaps(
                tapService.read(Paths.get(FileLocation.INPUT + "taps.csv")));
            tripService.exportToCsv(tripService.createTrips(pairs),
                Paths.get(FileLocation.OUTPUT + "trips.csv"));
        } catch (IOException ex) {
            log.error(ex.getMessage());
        }
    }

}
