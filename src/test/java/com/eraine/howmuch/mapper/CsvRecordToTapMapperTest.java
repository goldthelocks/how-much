package com.eraine.howmuch.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.eraine.howmuch.model.api.TapFileHeader;
import com.eraine.howmuch.model.jpa.Tap;
import java.io.IOException;
import java.io.StringReader;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CsvRecordToTapMapperTest {

    private CSVRecord csvRecord;
    private CsvRecordToTapMapper mapper;

    @BeforeEach
    public void setUp() throws IOException {
        mapper = new CsvRecordToTapMapper();

        final String[] values = new String[] {"1", "22-01-2022 09:10:00", "ON", "Stop1", "Company10", "Bus10", "34343434343434"};
        final String rowData = String.join(", ", values);

        CSVFormat csvFormat = CSVFormat.DEFAULT
            .builder()
            .setHeader(TapFileHeader.class)
            .setTrim(true)
            .build();

        try (final CSVParser parser = csvFormat.parse(new StringReader(rowData))) {
            csvRecord = parser.iterator().next();
        }
    }

    @Test
    public void shouldCreateTapFromCsvRecord() {
        Tap tap = mapper.apply(csvRecord);

        assertThat(tap).isNotNull();
        assertThat(tap.getDateTime()).isEqualTo(LocalDateTime.of(LocalDate.of(2022, 1, 22),
            LocalTime.of(9, 10, 0)));
        assertThat(tap.getType()).isEqualTo(Tap.Type.ON);
        assertThat(tap.getStop().getId()).isEqualTo("Stop1");
        assertThat(tap.getCompany().getId()).isEqualTo("Company10");
        assertThat(tap.getBusId()).isEqualTo("Bus10");
        assertThat(tap.getPrimaryAccountNumber()).isEqualTo("34343434343434");
    }

}
