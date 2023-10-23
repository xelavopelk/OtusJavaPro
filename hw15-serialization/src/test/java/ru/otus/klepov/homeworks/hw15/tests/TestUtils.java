package ru.otus.klepov.homeworks.hw15.tests;

import ru.otus.klepov.homeworks.hw15.domain.newdto.NewChatSession;
import ru.otus.klepov.homeworks.hw15.domain.newdto.NewMessage;
import ru.otus.klepov.homeworks.hw15.domain.newdto.NewSmsData;
import ru.otus.klepov.homeworks.hw15.domain.newdto.NumberData;
import ru.otus.klepov.homeworks.hw15.service.DateTypeAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;

public class TestUtils {
    public static NewSmsData prepareSimpleSample() throws ParseException {
        var sample = new NewSmsData();
        sample.sessions = new ArrayList<>();
        var s = new NewChatSession();
        s.chatIdentifier = "Apple";
        s.Last = Arrays.stream((new String[]{"Saint-Petersburg"})).toList();
        SimpleDateFormat df = new SimpleDateFormat(DateTypeAdapter.DATE_PATTERN);
        var messageList = Arrays.stream((new NewMessage[]{
                new NewMessage(df.parse("03-31-2023 14:09:19"), "PROBLEM CRITICAL - There is hight price of the order")
        })).toList();
        s.numberData = Arrays.stream((new NumberData[]{
                new NumberData("+79219213267", messageList)
        })).toList();
        sample.sessions.add(s);
        return sample;
    }
}
