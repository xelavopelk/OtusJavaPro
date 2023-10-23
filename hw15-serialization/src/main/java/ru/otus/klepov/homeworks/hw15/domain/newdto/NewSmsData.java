package ru.otus.klepov.homeworks.hw15.domain.newdto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewSmsData {
    @SerializedName("chat_sessions")
    public List<NewChatSession> sessions;

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        final NewSmsData other = (NewSmsData) obj;
        return this.sessions.equals(other.sessions);
    }
}
