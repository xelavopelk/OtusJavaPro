package ru.otus.klepov.homeworks.hw15;

import com.google.common.io.Resources;
import ru.otus.klepov.homeworks.hw15.service.*;

import java.io.*;

public class Program {

    private static void jsonFlow() throws IOException {
        System.out.println("start JSON flow");
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
        if(res.isRight()) {
            System.out.println(String.format("session count=%d", res.get().sessions.size()));
        }
        else {
            System.out.println(String.format("shit happens=%s", res.getLeft().getCause()));
        }
        System.out.println("finish JSON flow");
    }

    private static void protobufFlow() throws IOException {
        System.out.println("start Protobuf flow");
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
        if(res.isRight()) {
            System.out.println(String.format("session count=%d", res.get().getChatSessionCount()));
        }
        else {
            System.out.println(String.format("shit happens=%s", res.getLeft().getCause()));
        }
        System.out.println("finish Protobuf flow");
    }


    public static void main(String[] args) throws IOException {
        jsonFlow();
        protobufFlow();
    }
}
