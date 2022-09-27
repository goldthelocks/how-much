package com.eraine.howmuch.mapper;

import com.eraine.howmuch.model.api.TapFileHeader;
import com.eraine.howmuch.model.jpa.Company;
import com.eraine.howmuch.model.jpa.Stop;
import com.eraine.howmuch.model.jpa.Tap;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;
import org.apache.commons.csv.CSVRecord;

public final class CsvRecordToTapMapper implements Function<CSVRecord, Tap> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    @Override
    public Tap apply(CSVRecord csvRecord) {
        Tap tap = new Tap();
        tap.setId(Long.parseLong(csvRecord.get(TapFileHeader.ID)));
        tap.setDateTime(LocalDateTime.parse(String.valueOf(csvRecord.get(TapFileHeader.DateTimeUTC)), DATE_TIME_FORMATTER));
        tap.setType(String.valueOf(csvRecord.get(TapFileHeader.TapType)).equalsIgnoreCase(Tap.Type.ON.name())
            ? Tap.Type.ON : Tap.Type.OFF);

        // these may come from db for validation but for now we will just create an instance
        tap.setStop(new Stop(String.valueOf(csvRecord.get(TapFileHeader.StopId))));
        tap.setCompany(new Company(String.valueOf(csvRecord.get(TapFileHeader.CompanyId))));

        tap.setBusId(String.valueOf(csvRecord.get(TapFileHeader.BusID)));
        tap.setPrimaryAccountNumber(String.valueOf(csvRecord.get(TapFileHeader.PAN)));
        return tap;
    }

}
