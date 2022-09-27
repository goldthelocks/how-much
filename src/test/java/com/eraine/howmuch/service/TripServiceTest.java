package com.eraine.howmuch.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.linesOf;
import static org.mockito.Mockito.when;

import com.eraine.howmuch.FareTestData;
import com.eraine.howmuch.TapTestData;
import com.eraine.howmuch.TripTestData;
import com.eraine.howmuch.model.jpa.Trip;
import com.eraine.howmuch.repository.FareRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.io.TempDir;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TripServiceTest {

    private TripService tripService;

    @Mock
    private FareRepository fareRepository;

    @TempDir
    private Path outputPath;

    @BeforeEach
    public void setUp() throws IOException {
        tripService = new TripService(fareRepository);

        outputPath = Files.createFile(outputPath.resolve("trips.csv"));
    }

    @Test
    public void shouldCreateTripsFromPairs() {
        when(fareRepository.findAll()).thenReturn(FareTestData.getFares());

        List<Trip> trips = tripService.createTrips(Arrays.asList(
            TapTestData.createCompletedPair(),
            TapTestData.createIncompletePair(),
            TapTestData.createCancelledPair()));

        assertThat(trips).hasSize(3);
    }

    @Test
    public void shouldExportTripsToCsvFile() throws IOException {
        List<Trip> trips = Arrays.asList(TripTestData.createCompletedTrip(), TripTestData.createIncompleteTrip());

        tripService.exportToCsv(trips, outputPath);

        assertThat(outputPath).exists();
        assertThat(linesOf(outputPath.toFile()))
            .containsExactly(
                "Started, Finished, DurationSecs, FromStopId, ToStopId, ChargeAmount, CompanyId, BusID, PAN, Status",
                "26-09-2022 12:25:05, 26-09-2022 12:35:56, 651, Stop1, Stop3, $7.30, Company1, Bus1, 122000000000003, COMPLETED",
                "26-09-2022 09:12:02, , , Stop1, , $7.30, Company1, Bus1, 122000000000003, INCOMPLETE");
    }

}
