package ru.otus.klepov.homeworks.hw15;

import com.google.common.io.Resources;
import ru.otus.klepov.homeworks.hw15.service.*;

import java.io.*;
import java.util.logging.Logger;

public class Program {

    private static void jsonFlow(Logger log) throws IOException {
        log.info("start JSON flow");
        var newFile = "newSms.json";
        var oldFile = "sms.json";
        var s = new SmsDataSerializer();
        var sNew = new NewSmsDataSerializer();
        var df = new NewSmsDataFactory();
        var path = new File(Resources.getResource(oldFile).getFile()).getParent();
        FileWriter writer = new FileWriter(new File(path, newFile));
        var res = Utils
                .readFile(oldFile)
                .flatMap(s::deserialize)
                .map(df::makeSmsData)
                .map(o -> sNew.serialize(writer, o))
                .flatMap(r -> Utils.readFile(newFile))
                .flatMap(sNew::deserialize);
        if (res.isRight()) {
            log.info(String.format("session count=%d", res.get().sessions.size()));
        } else {
            log.info(String.format("shit happens=%s", res.getLeft().getCause()));
        }
        log.info("finish JSON flow");
    }

    private static void protobufFlow(Logger log) throws IOException {
        log.info("start Protobuf flow");
        var newFile = "newSms.bin";
        var oldFile = "sms.json";
        var s = new SmsDataSerializer();
        var sNew = new NewSmsDataProtobufSerializer();
        var df = new NewSmsDataFactory();
        var pdf = new ProtoNewSmsDataFactory();
        var path = new File(Resources.getResource(oldFile).getFile()).getParent();
        var writer = new FileOutputStream(new File(path, newFile));
        var reader = new FileInputStream(new File(path, newFile));
        var res = Utils
                .readFile(oldFile)
                .flatMap(s::deserialize)
                .map(df::makeSmsData)
                .map(pdf::makeSmsData)
                .map(o -> sNew.serialize(writer, o))
                .flatMap(r -> sNew.deserialize(reader));
        if (res.isRight()) {
            log.info(String.format("session count=%d", res.get().getChatSessionCount()));
        } else {
            log.info(String.format("shit happens=%s", res.getLeft().getCause()));
        }
        log.info("finish Protobuf flow");
    }


    public static void main(String[] args) throws IOException {
        var log = Logger.getLogger(Program.class.getName());
        jsonFlow(log);
        protobufFlow(log);
    }
}
