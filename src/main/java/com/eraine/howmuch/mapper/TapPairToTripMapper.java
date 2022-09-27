package com.eraine.howmuch.mapper;

import com.eraine.howmuch.model.api.TapPair;
import com.eraine.howmuch.model.jpa.Fare;
import com.eraine.howmuch.model.jpa.Trip;
import com.eraine.howmuch.service.TripAmount;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import javax.money.Monetary;

public final class TapPairToTripMapper implements Function<TapPair, Trip> {

    private final List<Fare> fares;

    public TapPairToTripMapper(List<Fare> fares) {
        this.fares = fares;
    }

    @Override
    public Trip apply(TapPair pair) {
        Trip trip = new Trip();

        trip.setStarted(pair.getTapOn().getDateTime());
        trip.setFromStop(pair.getTapOn().getStop());
        trip.setCompany(pair.getTapOn().getCompany());
        trip.setBusId(pair.getTapOn().getBusId());
        trip.setPrimaryAccountNumber(pair.getTapOn().getPrimaryAccountNumber());
        trip.setCurrencyCode(Monetary.getCurrency(Locale.US).getCurrencyCode());

        Trip.Status status = getStatus(pair);
        trip.setStatus(status);

        if (trip.getStatus() != Trip.Status.INCOMPLETE) {
            trip.setToStop(pair.getTapOff().getStop());
            trip.setFinished(pair.getTapOff().getDateTime());
            trip.setDurationInSeconds(ChronoUnit.SECONDS.between(pair.getTapOn().getDateTime(),
                pair.getTapOff().getDateTime()));
        }

        TripAmount amount = new TripAmount(fares);
        trip.setAmount(amount.get(pair, status));

        return trip;
    }

    private Trip.Status getStatus(TapPair pair) {
        if (pair.getTapOff() == null) {
            return Trip.Status.INCOMPLETE;
        } else if (pair.getTapOn().getStop().getId().equalsIgnoreCase(pair.getTapOff().getStop().getId())) {
            return Trip.Status.CANCELLED;
        } else {
            return Trip.Status.COMPLETED;
        }
    }

}
