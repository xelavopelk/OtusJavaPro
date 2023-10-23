package ru.otus.klepov.homeworks.hw15.domain.olddto;
import com.google.gson.annotations.SerializedName;

public class Member {
    @SerializedName("first")
    public String First;
    @SerializedName("handle_id")
    public Long HandleId;
    @SerializedName("image_path")
    public String ImagePath;
    @SerializedName("last")
    public String Last;
    @SerializedName("middle")
    public String Middle;
    @SerializedName("phone_number")
    public String PhoneNumber;
    @SerializedName("service")
    public String Service;
    @SerializedName("thumb_path")
    public String ThumbPath;
}
