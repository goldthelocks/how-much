package com.eraine.howmuch;

import com.eraine.howmuch.model.api.TapPair;
import com.eraine.howmuch.model.jpa.Company;
import com.eraine.howmuch.model.jpa.Stop;
import com.eraine.howmuch.model.jpa.Tap;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;

public final class TapTestData {

    private static final String PAN_1 = "122000000000003";
    private static final String PAN_2 = "34343434343434";
    private static final String PAN_3 = "6011000400000000";

    public static List<Tap> createShuffledTaps() {
        List<Tap> taps = new ArrayList<>(createCompletedTaps());
        taps.addAll(createIncompleteTaps());
        taps.addAll(createCancelledTaps());

        Collections.shuffle(taps);

        return taps;
    }

    public static List<Tap> createCancelledTaps() {
        Tap on1 = createTap(Tap.Type.ON, LocalDateTime.of(LocalDate.of(2022, 9, 26), LocalTime.of(7, 44, 22)),
            "Stop1", "Bus1", PAN_2);
        Tap off1 = createTap(Tap.Type.OFF, LocalDateTime.of(LocalDate.of(2022, 9, 26), LocalTime.of(7, 44, 23)),
            "Stop1", "Bus1", PAN_2);

        Tap on2 = createTap(Tap.Type.ON, LocalDateTime.of(LocalDate.of(2022, 9, 26), LocalTime.of(11, 5, 3)),
            "Stop3", "Bus3", PAN_3);
        Tap off2 = createTap(Tap.Type.OFF, LocalDateTime.of(LocalDate.of(2022, 9, 26), LocalTime.of(11, 25, 4)),
            "Stop3", "Bus3", PAN_3);

        return Arrays.asList(on1, off1, on2, off2);
    }

    public static List<Tap> createCompletedTaps() {
        Tap on1 = createTap(Tap.Type.ON, LocalDateTime.of(LocalDate.of(2022, 9, 26), LocalTime.of(12, 25, 5)),
            "Stop1", "Bus1", PAN_1);
        Tap off1 = createTap(Tap.Type.OFF, LocalDateTime.of(LocalDate.of(2022, 9, 26), LocalTime.of(12, 35, 56)),
            "Stop3", "Bus1", PAN_1);

        Tap on2 = createTap(Tap.Type.ON, LocalDateTime.of(LocalDate.of(2022, 9, 26), LocalTime.of(14, 11, 53)),
            "Stop2", "Bus2", PAN_2);
        Tap off2 = createTap(Tap.Type.OFF, LocalDateTime.of(LocalDate.of(2022, 9, 26), LocalTime.of(14, 21, 54)),
            "Stop1", "Bus2", PAN_2);

        Tap on3 = createTap(Tap.Type.ON, LocalDateTime.of(LocalDate.of(2022, 9, 26), LocalTime.of(15, 9, 34)),
            "Stop2", "Bus3", PAN_3);
        Tap off3 = createTap(Tap.Type.OFF, LocalDateTime.of(LocalDate.of(2022, 9, 26), LocalTime.of(15, 19, 35)),
            "Stop3", "Bus3", PAN_3);

        Tap on4 = createTap(Tap.Type.ON, LocalDateTime.of(LocalDate.of(2022, 9, 26), LocalTime.of(18, 15, 1)),
            "Stop3", "Bus3", PAN_1);
        Tap off4 = createTap(Tap.Type.OFF, LocalDateTime.of(LocalDate.of(2022, 9, 26), LocalTime.of(18, 25, 2)),
            "Stop2", "Bus3", PAN_1);

        return Arrays.asList(on1, off1, on2, off2, on3, off3, on4, off4);
    }

    public static List<Tap> createIncompleteTaps() {
        Tap on1 = createTap(Tap.Type.ON, LocalDateTime.of(LocalDate.of(2022, 9, 26), LocalTime.of(9, 12, 2)),
            "Stop1", "Bus1", PAN_1);
        Tap on2 = createTap(Tap.Type.ON, LocalDateTime.of(LocalDate.of(2022, 9, 26), LocalTime.of(10, 15, 3)),
            "Stop2", "Bus2", PAN_1);
        Tap on3 = createTap(Tap.Type.ON, LocalDateTime.of(LocalDate.of(2022, 9, 26), LocalTime.of(11, 18, 4)),
            "Stop3", "Bus3", PAN_1);
        Tap on4 = createTap(Tap.Type.ON, LocalDateTime.of(LocalDate.of(2022, 9, 26), LocalTime.of(10, 16, 3)),
            "Stop2", "Bus2", PAN_3);

        return Arrays.asList(on1, on2, on3, on4);
    }

    public static TapPair createCompletedPair() {
        Tap on = createTap(Tap.Type.ON, LocalDateTime.of(LocalDate.of(2022, 9, 26), LocalTime.of(12, 25, 5)),
            "Stop1", "Bus1", "122000000000003");
        Tap off = createTap(Tap.Type.OFF, LocalDateTime.of(LocalDate.of(2022, 9, 26), LocalTime.of(12, 35, 56)),
            "Stop3", "Bus1", "122000000000003");

        return new TapPair(on, off);
    }

    public static TapPair createIncompletePair() {
        Tap on = TapTestData.createTap(Tap.Type.ON, LocalDateTime.of(LocalDate.of(2022, 9, 26), LocalTime.of(9, 12, 2)),
            "Stop1", "Bus1", "122000000000003");

        return new TapPair(on, null);
    }

    public static TapPair createCancelledPair() {
        Tap on = TapTestData.createTap(Tap.Type.ON, LocalDateTime.of(LocalDate.of(2022, 9, 26), LocalTime.of(7, 44, 22)),
            "Stop1", "Bus1", "122000000000003");
        Tap off = TapTestData.createTap(Tap.Type.OFF, LocalDateTime.of(LocalDate.of(2022, 9, 26), LocalTime.of(7, 44, 23)),
            "Stop1", "Bus1", "122000000000003");

        return new TapPair(on, off);
    }

    public static Tap createTap(Tap.Type type, LocalDateTime dateTime, String stopId, String busId, String pan) {
        Tap tap = new Tap();
        tap.setId(RandomUtils.nextLong());
        tap.setDateTime(dateTime);
        tap.setType(type);
        tap.setStop(new Stop(stopId));
        tap.setCompany(new Company(RandomStringUtils.randomAlphabetic(15)));
        tap.setBusId(busId);
        tap.setPrimaryAccountNumber(pan);
        return tap;
    }

}
