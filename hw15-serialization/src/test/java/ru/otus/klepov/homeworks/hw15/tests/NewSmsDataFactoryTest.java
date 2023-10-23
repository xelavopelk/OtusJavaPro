package ru.otus.klepov.homeworks.hw15.tests;

import io.vavr.control.Either;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.klepov.homeworks.hw15.service.Utils;
import ru.otus.klepov.homeworks.hw15.domain.olddto.SmsData;
import ru.otus.klepov.homeworks.hw15.service.*;

import java.text.ParseException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class NewSmsDataFactoryTest {
    private SmsDataSerializer s;
    private NewSmsDataFactory df;



    @BeforeEach
    public void Init() {
        df = new NewSmsDataFactory();
        s = new SmsDataSerializer();
    }

    private Either<Throwable,SmsData> prepareSimpleData() {
        return Utils
                .readFile("simpleTestData.json")
                .flatMap(json->s.deserialize(json));
    }

    @Test
    public void testComparationSuccess() throws ParseException {
        var sample1 = TestUtils.prepareSimpleSample();
        var sample2 = TestUtils.prepareSimpleSample();
        assertEquals(sample1, sample2);
    }

    @Test
    public void testComparationFail() throws ParseException {
        var sample1 = TestUtils.prepareSimpleSample();
        var sample2 = TestUtils.prepareSimpleSample();
        sample2.sessions.get(0).chatIdentifier = "FAILDATA";
        var res = sample1.equals(sample2);
        assertFalse(res);
    }

    @Test
    public void testComparationListFail() throws ParseException {
        var sample1 = TestUtils.prepareSimpleSample();
        var sample2 = TestUtils.prepareSimpleSample();
        sample2.sessions.get(0).Last = new ArrayList<String>();
        var res = sample1.equals(sample2);
        assertFalse(res);
    }
    @Test
    public void conversionTestSuccess() {
        var res = prepareSimpleData()
                .map(data -> df.makeSmsData(data));
        assertTrue(res.isRight());
    }
    @Test
    public void conversionTestSimpleSuccess() throws ParseException {
        var sample = TestUtils.prepareSimpleSample();
        var res = prepareSimpleData()
                .map(data -> df.makeSmsData(data))
                .get();
        assertTrue(sample.equals(res));
    }
}
