package ru.otus.klepov.homeworks.hw15.domain.newdto;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class NewMessage {
    @SerializedName("send_date")
    public java.util.Date SendDate;
    @SerializedName("text")
    public String Text;
    public NewMessage() {};
    public NewMessage(Date sendDate, String text) {
        this.SendDate=sendDate;
        this.Text=text;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        final NewMessage other = (NewMessage) obj;
        return this.Text.equals(other.Text) && this.SendDate.equals(other.SendDate);
    }
}
