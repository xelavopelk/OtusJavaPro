package ru.otus.klepov.homeworks.hw15.service;

import io.vavr.control.Either;
import io.vavr.control.Try;
import ru.otus.klepov.homeworks.hw15.domain.newdto.pb.SmsDataOuterClass;

import java.io.InputStream;
import java.io.OutputStream;

public class NewSmsDataProtobufSerializer {
    public Either<Throwable,Boolean> serialize(OutputStream stream, SmsDataOuterClass.SmsData data) {
        var res = Try
                .of(() -> {
                    data.writeTo(stream);
                    return true;
                }).toEither();
        return res;
    }
    public Either<Throwable, SmsDataOuterClass.SmsData> deserialize(InputStream stream) {
        return Try.of(() -> SmsDataOuterClass.SmsData.newBuilder().mergeFrom(stream).build()).toEither();
    }
}
