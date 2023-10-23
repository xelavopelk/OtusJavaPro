package ru.otus.klepov.homeworks.hw15.domain.newdto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewChatSession {
    @SerializedName("chat_identifier")
    public String chatIdentifier;
    @SerializedName("last")
    public List<String> Last;
    @SerializedName("BelongNumberList")
    public List<NumberData> numberData;
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        final NewChatSession other = (NewChatSession) obj;
        return this.Last.equals(other.Last)
                && this.chatIdentifier.equals(other.chatIdentifier)
                && this.numberData.equals(other.numberData) ;
    }

}
