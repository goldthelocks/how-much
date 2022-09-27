package com.eraine.howmuch.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import com.eraine.howmuch.TapTestData;
import com.eraine.howmuch.model.api.TapPair;
import com.eraine.howmuch.model.jpa.Tap;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class TapServiceTest {

    private TapService tapService;

    @BeforeEach
    public void setUp() {
        tapService = new TapService();
    }

    @Test
    public void shouldPairTaps() {
        final List<TapPair> pairs = tapService.pairTaps(TapTestData.createShuffledTaps());

        assertThat(pairs).hasSize(10);
        assertThat(pairs).extracting(
                TapOnExtractor.typeExtractor(),
                TapOnExtractor.stopExtractor(),
                TapOnExtractor.busExtractor(),
                TapOnExtractor.panExtractor(),
                TapOffExtractor.typeExtractor(),
                TapOffExtractor.stopExtractor(),
                TapOffExtractor.busExtractor(),
                TapOffExtractor.panExtractor())
            .containsExactlyInAnyOrder(
                tuple(Tap.Type.ON, "Stop1", "Bus1", "122000000000003",
                    Tap.Type.OFF, "Stop3", "Bus1", "122000000000003"),
                tuple(Tap.Type.ON, "Stop2", "Bus2", "34343434343434",
                    Tap.Type.OFF, "Stop1", "Bus2", "34343434343434"),
                tuple(Tap.Type.ON, "Stop2", "Bus3", "6011000400000000",
                    Tap.Type.OFF, "Stop3", "Bus3", "6011000400000000"),
                tuple(Tap.Type.ON, "Stop3", "Bus3", "122000000000003",
                    Tap.Type.OFF, "Stop2", "Bus3", "122000000000003"),
                tuple(Tap.Type.ON, "Stop1", "Bus1", "34343434343434",
                    Tap.Type.OFF, "Stop1", "Bus1", "34343434343434"),
                tuple(Tap.Type.ON, "Stop3", "Bus3", "6011000400000000",
                    Tap.Type.OFF, "Stop3", "Bus3", "6011000400000000"),
                tuple(Tap.Type.ON, "Stop1", "Bus1", "122000000000003",
                    null, null, null, null),
                tuple(Tap.Type.ON, "Stop2", "Bus2", "122000000000003",
                    null, null, null, null),
                tuple(Tap.Type.ON, "Stop3", "Bus3", "122000000000003",
                    null, null, null, null),
                tuple(Tap.Type.ON, "Stop2", "Bus2", "6011000400000000",
                    null, null, null, null)
            );
    }

    @Test
    public void shouldPairTapsWithTapOnAndTapOffAtDifferentStops() {
        final List<TapPair> pairs = tapService.pairTaps(TapTestData.createCompletedTaps());

        assertThat(pairs).hasSize(4);
        assertThat(pairs).extracting(
                TapOnExtractor.typeExtractor(),
                TapOnExtractor.stopExtractor(),
                TapOnExtractor.busExtractor(),
                TapOnExtractor.panExtractor(),
                TapOffExtractor.typeExtractor(),
                TapOffExtractor.stopExtractor(),
                TapOffExtractor.busExtractor(),
                TapOffExtractor.panExtractor())
            .containsExactlyInAnyOrder(
                tuple(Tap.Type.ON, "Stop1", "Bus1", "122000000000003",
                    Tap.Type.OFF, "Stop3", "Bus1", "122000000000003"),
                tuple(Tap.Type.ON, "Stop2", "Bus2", "34343434343434",
                    Tap.Type.OFF, "Stop1", "Bus2", "34343434343434"),
                tuple(Tap.Type.ON, "Stop2", "Bus3", "6011000400000000",
                    Tap.Type.OFF, "Stop3", "Bus3", "6011000400000000"),
                tuple(Tap.Type.ON, "Stop3", "Bus3", "122000000000003",
                    Tap.Type.OFF, "Stop2", "Bus3", "122000000000003")
            );
    }

    @Test
    public void shouldPairTapsWithTapOnAndTapOffAtSameStop() {
        final List<TapPair> pairs = tapService.pairTaps(TapTestData.createCancelledTaps());

        assertThat(pairs).hasSize(2);
        assertThat(pairs).extracting(
                TapOnExtractor.typeExtractor(),
                TapOnExtractor.stopExtractor(),
                TapOnExtractor.busExtractor(),
                TapOnExtractor.panExtractor(),
                TapOffExtractor.typeExtractor(),
                TapOffExtractor.stopExtractor(),
                TapOffExtractor.busExtractor(),
                TapOffExtractor.panExtractor())
            .containsExactlyInAnyOrder(
                tuple(Tap.Type.ON, "Stop1", "Bus1", "34343434343434",
                    Tap.Type.OFF, "Stop1", "Bus1", "34343434343434"),
                tuple(Tap.Type.ON, "Stop3", "Bus3", "6011000400000000",
                    Tap.Type.OFF, "Stop3", "Bus3", "6011000400000000")
            );
    }

    @Test
    public void shouldPairTapsWithTapOnAndWithoutTapOff() {
        final List<TapPair> pairs = tapService.pairTaps(TapTestData.createIncompleteTaps());

        assertThat(pairs).hasSize(4);
        assertThat(pairs).extracting(
                TapOnExtractor.typeExtractor(),
                TapOnExtractor.stopExtractor(),
                TapOnExtractor.busExtractor(),
                TapOnExtractor.panExtractor(),
                TapOffExtractor.typeExtractor(),
                TapOffExtractor.stopExtractor(),
                TapOffExtractor.busExtractor(),
                TapOffExtractor.panExtractor())
            .containsExactlyInAnyOrder(
                tuple(Tap.Type.ON, "Stop1", "Bus1", "122000000000003",
                    null, null, null, null),
                tuple(Tap.Type.ON, "Stop2", "Bus2", "122000000000003",
                    null, null, null, null),
                tuple(Tap.Type.ON, "Stop3", "Bus3", "122000000000003",
                    null, null, null, null),
                tuple(Tap.Type.ON, "Stop2", "Bus2", "6011000400000000",
                    null, null, null, null)
            );
    }

    @Test
    public void shouldReadFile() throws IOException {
        Path tapsFile = Paths.get(getClass().getResource("/taps.csv").getPath());

        List<Tap> taps = tapService.read(tapsFile);

        assertThat(taps).hasSize(2);
    }

    private static class TapOnExtractor {

        public static Function<TapPair, String> stopExtractor() {
            return tapPair -> tapPair.getTapOn().getStop().getId();
        }

        public static Function<TapPair, Tap.Type> typeExtractor() {
            return tapPair -> tapPair.getTapOn().getType();
        }

        public static Function<TapPair, String> busExtractor() {
            return tapPair -> tapPair.getTapOn().getBusId();
        }

        public static Function<TapPair, String> panExtractor() {
            return tapPair -> tapPair.getTapOn().getPrimaryAccountNumber();
        }

    }

    private static class TapOffExtractor {

        public static Function<TapPair, String> stopExtractor() {
            return tapPair -> tapPair.getTapOff() != null
                ? tapPair.getTapOff().getStop().getId()
                : null;
        }

        public static Function<TapPair, Tap.Type> typeExtractor() {
            return tapPair -> tapPair.getTapOff() != null
                ? tapPair.getTapOff().getType()
                : null;
        }

        public static Function<TapPair, String> busExtractor() {
            return tapPair -> tapPair.getTapOff() != null
                ? tapPair.getTapOff().getBusId()
                : null;
        }

        public static Function<TapPair, String> panExtractor() {
            return tapPair -> tapPair.getTapOff() != null
                ? tapPair.getTapOff().getPrimaryAccountNumber()
                : null;
        }

    }

}
