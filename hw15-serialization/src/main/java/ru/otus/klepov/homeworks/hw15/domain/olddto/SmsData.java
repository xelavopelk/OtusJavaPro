package ru.otus.klepov.homeworks.hw15.domain.olddto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SmsData {
    @SerializedName("chat_sessions")
    public List<ChatSession> sessions;
}
