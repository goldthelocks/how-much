package com.eraine.howmuch.service;

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

public class TripAmountTest {

    private TripAmount tripAmount;

    @BeforeEach
    public void setUp() {
        tripAmount = new TripAmount(FareTestData.getFares());
    }

    @Test
    public void shouldGetFareForCompletedTrip() {
        Tap on = TapTestData.createTap(Tap.Type.ON, LocalDateTime.of(LocalDate.of(2022, 9, 26), LocalTime.of(18, 15, 1)),
            "Stop1", "Bus1", "12345");
        Tap off = TapTestData.createTap(Tap.Type.OFF, LocalDateTime.of(LocalDate.of(2022, 9, 26), LocalTime.of(18, 25, 2)),
            "Stop3", "Bus1", "12345");

        BigDecimal amount = tripAmount.get(new TapPair(on, off), Trip.Status.COMPLETED);

        assertThat(amount).isEqualTo(new BigDecimal("7.30"));
    }

    @Test
    public void shouldGetFareForIncompleteTrip() {
        Tap on = TapTestData.createTap(Tap.Type.ON, LocalDateTime.of(LocalDate.of(2022, 9, 26), LocalTime.of(18, 15, 1)),
            "Stop2", "Bus1", "12345");

        BigDecimal amount = tripAmount.get(new TapPair(on, null), Trip.Status.INCOMPLETE);

        assertThat(amount).isEqualTo(new BigDecimal("5.50"));
    }

    @Test
    public void shouldGetFareForCancelledTrip() {
        Tap on = TapTestData.createTap(Tap.Type.ON, LocalDateTime.of(LocalDate.of(2022, 9, 26), LocalTime.of(7, 44, 22)),
            "Stop1", "Bus1", "42344");
        Tap off = TapTestData.createTap(Tap.Type.OFF, LocalDateTime.of(LocalDate.of(2022, 9, 26), LocalTime.of(7, 44, 23)),
            "Stop1", "Bus1", "42344");

        BigDecimal amount = tripAmount.get(new TapPair(on, off), Trip.Status.CANCELLED);

        assertThat(amount).isEqualTo(new BigDecimal("0"));
    }

}
