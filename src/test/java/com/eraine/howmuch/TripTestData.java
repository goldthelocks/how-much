package com.eraine.howmuch;

import com.eraine.howmuch.model.jpa.Company;
import com.eraine.howmuch.model.jpa.Stop;
import com.eraine.howmuch.model.jpa.Trip;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public final class TripTestData {

    public static Trip createCompletedTrip() {
        Trip trip = new Trip();
        trip.setStarted(LocalDateTime.of(LocalDate.of(2022, 9, 26), LocalTime.of(12, 25, 5)));
        trip.setFromStop(new Stop("Stop1"));
        trip.setCompany(new Company("Company1"));
        trip.setBusId("Bus1");
        trip.setPrimaryAccountNumber("122000000000003");
        trip.setCurrencyCode("USD");
        trip.setStatus(Trip.Status.COMPLETED);
        trip.setToStop(new Stop("Stop3"));
        trip.setFinished(LocalDateTime.of(LocalDate.of(2022, 9, 26), LocalTime.of(12, 35, 56)));
        trip.setDurationInSeconds(651L);
        trip.setAmount(new BigDecimal("7.30"));
        return trip;
    }

    public static Trip createIncompleteTrip() {
        Trip trip = new Trip();
        trip.setStarted(LocalDateTime.of(LocalDate.of(2022, 9, 26), LocalTime.of(9, 12, 2)));
        trip.setFromStop(new Stop("Stop1"));
        trip.setCompany(new Company("Company1"));
        trip.setBusId("Bus1");
        trip.setPrimaryAccountNumber("122000000000003");
        trip.setCurrencyCode("USD");
        trip.setStatus(Trip.Status.INCOMPLETE);
        trip.setToStop(null);
        trip.setFinished(null);
        trip.setDurationInSeconds(null);
        trip.setAmount(new BigDecimal("7.30"));
        return trip;
    }

}
