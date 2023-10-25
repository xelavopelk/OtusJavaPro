package ru.otus.klepov.homeworks.hw15.domain.newdto;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NumberData {
    @SerializedName("belong_number")
    public String BelongNumber;
    @SerializedName("belong_number_messages")
    public List<NewMessage> messages;

    public NumberData() {
    }

    ;

    public NumberData(String belongNumber, List<NewMessage> messages) {
        this.BelongNumber = belongNumber;
        this.messages = messages;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        final NumberData other = (NumberData) obj;
        return this.messages.equals(other.messages) && this.BelongNumber.equals(other.BelongNumber);
    }
}
