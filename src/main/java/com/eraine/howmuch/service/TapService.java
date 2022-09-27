package com.eraine.howmuch.service;

import static net.logstash.logback.argument.StructuredArguments.kv;

import com.eraine.howmuch.mapper.CsvRecordToTapMapper;
import com.eraine.howmuch.model.api.TapFileHeader;
import com.eraine.howmuch.model.api.TapPair;
import com.eraine.howmuch.model.jpa.Tap;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.iterators.PeekingIterator;
import org.apache.commons.csv.CSVFormat;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class TapService {

    public List<Tap> read(Path inputPath) throws IOException {
        try {
            return CSVFormat.DEFAULT
                .builder()
                .setHeader(TapFileHeader.class)
                .setSkipHeaderRecord(true)
                .setTrim(true)
                .build()
                .parse(Files.newBufferedReader(inputPath))
                .stream()
                .map(new CsvRecordToTapMapper())
                .collect(Collectors.toList());
        } catch (IOException ex) {
            log.warn("Encountered error while reading file.", ex);
            throw ex;
        }
    }

    public List<TapPair> pairTaps(List<Tap> taps) {
        Map<String, List<Tap>> tapsPerAccount = groupByAccountNumber(taps);
        List<TapPair> pairs = new ArrayList<>();

        for (Map.Entry<String, List<Tap>> entry : tapsPerAccount.entrySet()) {
            log.info("Pairing taps for account: {}", kv("primaryAccountNumber", entry.getKey()));

            PeekingIterator<Tap> tapIterator = PeekingIterator.peekingIterator(entry.getValue().iterator());

            while (tapIterator.hasNext()) {
                Tap currentTap = tapIterator.next();

                if (isPair(currentTap, tapIterator.peek())) {
                    pairs.add(new TapPair(currentTap, tapIterator.next()));
                } else {
                    if (currentTap.getType() == Tap.Type.ON) {
                        pairs.add(new TapPair(currentTap, null));
                    } else {
                        // this shouldn't happen - cases like this should be logged to the system
                        log.warn("Tap OFF found without pair. {}", kv("tap", currentTap));
                    }
                }
            }
        }

        log.info("Completed pairing taps. {}", kv("total", pairs.size()));

        return pairs;
    }

    private Map<String, List<Tap>> groupByAccountNumber(List<Tap> taps) {
        return taps
            .stream()
            .sorted(Comparator.comparing(Tap::getDateTime))
            .collect(Collectors.groupingBy(Tap::getPrimaryAccountNumber,
                LinkedHashMap::new,
                Collectors.toCollection(ArrayList::new)));
    }

    private boolean isPair(Tap firstTap, Tap secondTap) {
        return (firstTap != null && secondTap != null
            && firstTap.getType() == Tap.Type.ON && secondTap.getType() == Tap.Type.OFF
            && firstTap.getBusId().equals(secondTap.getBusId()));
    }

}
