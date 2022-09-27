package com.eraine.howmuch.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.eraine.howmuch.FareTestData;
import com.eraine.howmuch.TapTestData;
import com.eraine.howmuch.model.api.TapPair;
import com.eraine.howmuch.model.jpa.Tap;
import com.eraine.howmuch.model.jpa.Trip;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TapPairToTripMapperTest {

    private TapPairToTripMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = new TapPairToTripMapper(FareTestData.getFares());
    }

    @Test
    public void shouldCreateTripFromCompletedTapPair() {
        Trip trip = mapper.apply(TapTestData.createCompletedPair());

        assertThat(trip).isNotNull();
        assertThat(trip.getStarted()).isEqualTo(LocalDateTime.of(LocalDate.of(2022, 9, 26), LocalTime.of(12, 25, 5)));
        assertThat(trip.getFromStop().getId()).isEqualTo("Stop1");
        assertThat(trip.getCompany()).isNotNull();
        assertThat(trip.getBusId()).isEqualTo("Bus1");
        assertThat(trip.getPrimaryAccountNumber()).isEqualTo("122000000000003");
        assertThat(trip.getCurrencyCode()).isEqualTo("USD");
        assertThat(trip.getStatus()).isEqualTo(Trip.Status.COMPLETED);
        assertThat(trip.getToStop().getId()).isEqualTo("Stop3");
        assertThat(trip.getFinished()).isEqualTo(LocalDateTime.of(LocalDate.of(2022, 9, 26), LocalTime.of(12, 35, 56)));
        assertThat(trip.getDurationInSeconds()).isEqualTo(651L);
        assertThat(trip.getAmount()).isEqualTo(new BigDecimal("7.30"));
    }

    @Test
    public void shouldCreateTripFromIncompleteTapPair() {
        Trip trip = mapper.apply(TapTestData.createIncompletePair());

        assertThat(trip).isNotNull();
        assertThat(trip.getStarted()).isEqualTo(LocalDateTime.of(LocalDate.of(2022, 9, 26), LocalTime.of(9, 12, 2)));
        assertThat(trip.getFromStop().getId()).isEqualTo("Stop1");
        assertThat(trip.getCompany()).isNotNull();
        assertThat(trip.getBusId()).isEqualTo("Bus1");
        assertThat(trip.getPrimaryAccountNumber()).isEqualTo("122000000000003");
        assertThat(trip.getCurrencyCode()).isEqualTo("USD");
        assertThat(trip.getStatus()).isEqualTo(Trip.Status.INCOMPLETE);
        assertThat(trip.getToStop()).isNull();
        assertThat(trip.getFinished()).isNull();
        assertThat(trip.getDurationInSeconds()).isNull();
        assertThat(trip.getAmount()).isEqualTo(new BigDecimal("7.30"));
    }

    @Test
    public void shouldCreateTripFromCancelledTapPair() {
        Trip trip = mapper.apply(TapTestData.createCancelledPair());

        assertThat(trip).isNotNull();
        assertThat(trip.getStarted()).isEqualTo(LocalDateTime.of(LocalDate.of(2022, 9, 26), LocalTime.of(7, 44, 22)));
        assertThat(trip.getFromStop().getId()).isEqualTo("Stop1");
        assertThat(trip.getCompany()).isNotNull();
        assertThat(trip.getBusId()).isEqualTo("Bus1");
        assertThat(trip.getPrimaryAccountNumber()).isEqualTo("122000000000003");
        assertThat(trip.getCurrencyCode()).isEqualTo("USD");
        assertThat(trip.getStatus()).isEqualTo(Trip.Status.CANCELLED);
        assertThat(trip.getToStop().getId()).isEqualTo("Stop1");
        assertThat(trip.getFinished()).isEqualTo(LocalDateTime.of(LocalDate.of(2022, 9, 26), LocalTime.of(7, 44, 23)));
        assertThat(trip.getDurationInSeconds()).isEqualTo(1L);
        assertThat(trip.getAmount()).isEqualTo(BigDecimal.ZERO);
    }

}
