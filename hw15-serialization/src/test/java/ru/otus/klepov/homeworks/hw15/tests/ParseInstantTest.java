package ru.otus.klepov.homeworks.hw15.tests;

import org.junit.jupiter.api.Test;
import ru.otus.klepov.homeworks.hw15.service.InstantTypeAdapter;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.otus.klepov.homeworks.hw15.service.Utils.parseString2Instant;

public class ParseInstantTest {
    @Test
    public void simpleSuccess() {
        var res = parseString2Instant("03-31-2023 14:09:19", InstantTypeAdapter.UTC_ZONE, InstantTypeAdapter.DATE_PATTERN);
        assertEquals(Instant.ofEpochSecond(1680271759),res);
    }
}
