package ru.otus.klepov.homeworks.hw15.domain.olddto;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ChatSession {
    @SerializedName("chat_id")
    public Long id;
    @SerializedName("chat_identifier")
    public String chatIdentifier;
    @SerializedName("display_name")
    public String DisplayName;
    @SerializedName("is_deleted")
    public Integer IsDeleted;
    @SerializedName("members")
    public List<Member> members;
    @SerializedName("messages")
    public List<Message> messages;
}
