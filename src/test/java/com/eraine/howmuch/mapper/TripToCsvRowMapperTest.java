package com.eraine.howmuch.mapper;

import static org.assertj.core.api.Assertions.assertThat;

import com.eraine.howmuch.TripTestData;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TripToCsvRowMapperTest {

    private TripToCsvRowMapper mapper;

    @BeforeEach
    public void setUp() {
        mapper = new TripToCsvRowMapper();
    }

    @Test
    public void shouldCreateCsvRowFromCompletedTrip() {
        Object[] row = mapper.apply(TripTestData.createCompletedTrip());

        assertThat(row[0]).isEqualTo("26-09-2022 12:25:05");
        assertThat(row[1]).isEqualTo("26-09-2022 12:35:56");
        assertThat(row[2]).isEqualTo(651L);
        assertThat(row[3]).isEqualTo("Stop1");
        assertThat(row[4]).isEqualTo("Stop3");
        assertThat(row[5]).isEqualTo("$7.30");
        assertThat(row[6]).isEqualTo("Company1");
        assertThat(row[7]).isEqualTo("Bus1");
        assertThat(row[8]).isEqualTo("122000000000003");
        assertThat(row[9]).isEqualTo("COMPLETED");
    }

    @Test
    public void shouldCreateCsvRowFromIncompleteTrip() {
        Object[] row = mapper.apply(TripTestData.createIncompleteTrip());

        assertThat(row[0]).isEqualTo("26-09-2022 09:12:02");
        assertThat(row[1]).isEqualTo(StringUtils.EMPTY);
        assertThat(row[2]).isEqualTo(null);
        assertThat(row[3]).isEqualTo("Stop1");
        assertThat(row[4]).isEqualTo(StringUtils.EMPTY);
        assertThat(row[5]).isEqualTo("$7.30");
        assertThat(row[6]).isEqualTo("Company1");
        assertThat(row[7]).isEqualTo("Bus1");
        assertThat(row[8]).isEqualTo("122000000000003");
        assertThat(row[9]).isEqualTo("INCOMPLETE");
    }

}
