package ru.otus.klepov.homeworks.hw15.tests;

import com.google.protobuf.Timestamp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.klepov.homeworks.hw15.domain.newdto.pb.MessageDataOuterClass;
import ru.otus.klepov.homeworks.hw15.service.NewSmsDataProtobufSerializer;
import ru.otus.klepov.homeworks.hw15.service.ProtoNewSmsDataFactory;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.text.ParseException;
import java.time.Instant;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.otus.klepov.homeworks.hw15.service.Utils.timestampFromInstant;

public class ProtobufTest {
    private ProtoNewSmsDataFactory pdf;
    private NewSmsDataProtobufSerializer pds;
    @BeforeEach
    public void init() {
        pdf = new ProtoNewSmsDataFactory();
        pds = new NewSmsDataProtobufSerializer();
    }
    @Test
    public void simpleTestSuccess() {
        var timestamp = timestampFromInstant(Instant.now());
        var txt = "test";
        MessageDataOuterClass.MessageData d = MessageDataOuterClass
                .MessageData
                .newBuilder()
                .setSendDate(timestamp)
                .setText(txt)
                .build();
        assertTrue(d.getText().equals(txt) && d.getSendDate().equals(timestamp));
    }

    @Test
    public void simpleTestFail() {
        var time = Instant.now();
        var timestamp = Timestamp.newBuilder().setSeconds(time.getEpochSecond())
                .setNanos(time.getNano()).build();
        var txt = "test";
        MessageDataOuterClass.MessageData d = MessageDataOuterClass
                .MessageData
                .newBuilder()
                .setSendDate(timestamp)
                .setText(txt)
                .build();
        assertFalse(d.getText().equals(txt.concat("1")) && d.getSendDate().equals(timestamp));
    }

    @Test
    public void simpleSerializeSuccess() throws ParseException {
        var sample = pdf.makeSmsData(TestUtils.prepareSimpleSample());
        OutputStream fos = new ByteArrayOutputStream();
        var res = pds.serialize(fos, sample);
        assertTrue(res.isRight());
    }
}
