package ru.otus.klepov.homeworks.hw15.service;

import ru.otus.klepov.homeworks.hw15.domain.newdto.NewChatSession;
import ru.otus.klepov.homeworks.hw15.domain.newdto.NewMessage;
import ru.otus.klepov.homeworks.hw15.domain.newdto.NewSmsData;
import ru.otus.klepov.homeworks.hw15.domain.newdto.NumberData;
import ru.otus.klepov.homeworks.hw15.domain.newdto.pb.*;

import java.util.stream.Collectors;

import static ru.otus.klepov.homeworks.hw15.service.Utils.timestampFromInstant;

public class ProtoNewSmsDataFactory {
    public MessageDataOuterClass.MessageData makeMessageData(NewMessage message) {
        MessageDataOuterClass.MessageData d = MessageDataOuterClass
                .MessageData
                .newBuilder()
                .setSendDate(timestampFromInstant(message.SendDate))
                .setText(message.Text)
                .build();
        return d;
    }

    public NumberDataOuterClass.NumberData makeNumberData(NumberData nd) {
        var d = NumberDataOuterClass
                .NumberData
                .newBuilder()
                .setBelongNumber(nd.BelongNumber)
                .addAllMessageData(
                        nd.messages
                                .stream()
                                .map(v -> makeMessageData(v))
                                .collect(Collectors.toList())
                ).build();
        return d;
    }

    public ChatSessionDataOuterClass.ChatSessionData makeChatSession(NewChatSession cs) {
        var d = ChatSessionDataOuterClass
                .ChatSessionData
                .newBuilder()
                .setChatIdentifier(cs.chatIdentifier)
                .addAllLast(cs.Last)
                .addAllNumberData(
                        cs.numberData
                                .stream()
                                .map(v -> makeNumberData(v))
                                .collect(Collectors.toList())
                ).build();
        return d;
    }

    public SmsDataOuterClass.SmsData makeSmsData(NewSmsData sd) {
        var d = SmsDataOuterClass
                .SmsData
                .newBuilder()
                .addAllChatSession(
                        sd.sessions
                                .stream()
                                .map(v -> makeChatSession(v))
                                .collect(Collectors.toList())

                ).build();
        return d;
    }
}
