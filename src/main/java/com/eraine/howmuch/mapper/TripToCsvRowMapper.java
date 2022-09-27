package com.eraine.howmuch.mapper;

import com.eraine.howmuch.model.jpa.Company;
import com.eraine.howmuch.model.jpa.Stop;
import com.eraine.howmuch.model.jpa.Trip;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;
import javax.money.Monetary;
import javax.money.format.AmountFormatQueryBuilder;
import javax.money.format.MonetaryAmountFormat;
import javax.money.format.MonetaryFormats;
import org.apache.commons.lang3.StringUtils;
import org.javamoney.moneta.Money;
import org.javamoney.moneta.format.CurrencyStyle;

public final class TripToCsvRowMapper implements Function<Trip, Object[]> {

    private static final MonetaryAmountFormat AMOUNT_FORMAT = MonetaryFormats.getAmountFormat(
        AmountFormatQueryBuilder
            .of(Locale.US)
            .set(CurrencyStyle.SYMBOL)
            .build());

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    @Override
    public Object[] apply(Trip trip) {
        return new Object[] {
            trip.getStarted().format(DATE_TIME_FORMATTER),
            Optional.ofNullable(trip.getFinished())
                .map(date -> date.format(DATE_TIME_FORMATTER))
                .orElse(StringUtils.EMPTY),
            trip.getDurationInSeconds(),
            trip.getFromStop().getId(),
            Optional.ofNullable(trip.getToStop())
                .map(Stop::getId)
                .orElse(StringUtils.EMPTY),
            AMOUNT_FORMAT.format(Money.of(trip.getAmount(), Monetary.getCurrency(trip.getCurrencyCode()))),
            Optional.ofNullable(trip.getCompany())
                .map(Company::getId)
                .orElse(StringUtils.EMPTY),
            trip.getBusId(),
            trip.getPrimaryAccountNumber(),
            trip.getStatus().name()
        };
    }

}
