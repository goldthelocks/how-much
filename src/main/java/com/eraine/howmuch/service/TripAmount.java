package com.eraine.howmuch.service;

import com.eraine.howmuch.model.jpa.Fare;
import com.eraine.howmuch.model.api.TapPair;
import com.eraine.howmuch.model.jpa.Trip;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;

public final class TripAmount {

    private final List<Fare> fares;

    public TripAmount(List<Fare> fares) {
        this.fares = fares;
    }

    public BigDecimal get(TapPair pair, Trip.Status status) {
        if (status == Trip.Status.COMPLETED) {
            return complete(pair);
        } else if (status == Trip.Status.INCOMPLETE) {
            return incomplete(pair);
        } else {
            return cancelled();
        }
    }

    private BigDecimal cancelled() {
        return BigDecimal.ZERO;
    }

    private BigDecimal incomplete(TapPair pair) {
        return fares.stream()
            .filter(fare -> fare.getFromStop().getId().equalsIgnoreCase(pair.getTapOn().getStop().getId()))
            .map(Fare::getAmount)
            .max(Comparator.naturalOrder())
            .orElse(BigDecimal.ZERO);
    }

    private BigDecimal complete(TapPair pair) {
        return fares
            .stream()
            .filter(fare -> fare.getFromStop().getId().equalsIgnoreCase(pair.getTapOn().getStop().getId())
                && fare.getToStop().getId().equalsIgnoreCase(pair.getTapOff().getStop().getId()))
            .findFirst()
            .map(Fare::getAmount)
            .orElse(BigDecimal.ZERO);
    }

}
