package ru.otus.klepov.homeworks.hw15.service;

import ru.otus.klepov.homeworks.hw15.domain.olddto.ChatSession;
import ru.otus.klepov.homeworks.hw15.domain.olddto.Message;
import ru.otus.klepov.homeworks.hw15.domain.olddto.SmsData;
import ru.otus.klepov.homeworks.hw15.domain.newdto.NewChatSession;
import ru.otus.klepov.homeworks.hw15.domain.newdto.NewMessage;
import ru.otus.klepov.homeworks.hw15.domain.newdto.NewSmsData;
import ru.otus.klepov.homeworks.hw15.domain.newdto.NumberData;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.toList;

public class NewSmsDataFactory {
    public List<String> makeMemberLast(ChatSession session) {
        return session
                .members
                .stream()
                .map(m-> m.Last)
                .collect(Collectors.toList());
    }
    public List<NumberData> makeNumberData(List<Message> messages) {
        Function<List<NewMessage>,List<NewMessage>> sortByDate = (l) -> {
            var s = l.stream().sorted(Comparator.comparing(o -> ((NewMessage)o).SendDate).reversed());
            return s.collect(toList());
        };
        return messages
                .stream()
                .collect(
                        Collectors
                                .groupingBy(m -> m.BelongNumber,
                                    mapping(ms -> new NewMessage(ms.SendDate, ms.Text), toList())

                        )
                )
                .entrySet()
                .stream()
                .map(e -> new NumberData(e.getKey(), sortByDate.apply(e.getValue())))
                .collect(toList());
    }
    public NewChatSession makeChatSession(ChatSession session) {
        var sess = new NewChatSession();
        sess.chatIdentifier=session.chatIdentifier;
        sess.Last = makeMemberLast(session);
        sess.numberData = makeNumberData(session.messages);
        return sess;
    }
    public NewSmsData makeSmsData(SmsData data) {
        var res =new NewSmsData();
        res.sessions = data
                .sessions
                .stream()
                .map(this::makeChatSession)
                .collect(Collectors.toList());
        return res;
    }
}
