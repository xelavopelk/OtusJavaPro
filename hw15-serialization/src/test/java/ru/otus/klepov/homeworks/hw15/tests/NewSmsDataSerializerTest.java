package ru.otus.klepov.homeworks.hw15.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.klepov.homeworks.hw15.service.NewSmsDataSerializer;

import java.io.StringWriter;
import java.text.ParseException;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class NewSmsDataSerializerTest {
    private NewSmsDataSerializer s;

    @BeforeEach
    public void init() {
        s = new NewSmsDataSerializer();
    }

    @Test
    public void simpleTestSuccess() throws ParseException {
        var sample = TestUtils.prepareSimpleSample();
        StringWriter w = new StringWriter();
        var res = s.serialize(w,sample);
        assertTrue(res.isRight());
    }

    @Test
    public void simple2WayTestSuccess() throws ParseException {
        var sample = TestUtils.prepareSimpleSample();
        StringWriter w = new StringWriter();
        var res = s
            .serialize(w,sample)
            .map(v -> w.toString())
            .flatMap(s::deserialize);
        assertTrue(res.isRight());
    }
}
