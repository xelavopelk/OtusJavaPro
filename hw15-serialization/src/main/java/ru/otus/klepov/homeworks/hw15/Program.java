package ru.otus.klepov.homeworks.hw15;

import com.google.common.io.Resources;
import ru.otus.klepov.homeworks.hw15.service.NewSmsDataFactory;
import ru.otus.klepov.homeworks.hw15.service.NewSmsDataSerializer;
import ru.otus.klepov.homeworks.hw15.service.SmsDataSerializer;
import ru.otus.klepov.homeworks.hw15.service.Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class Program {
    public static void main(String[] args) throws IOException {
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
        System.out.println(res);

    }
}
