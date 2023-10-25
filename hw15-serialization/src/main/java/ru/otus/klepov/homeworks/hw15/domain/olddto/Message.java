package ru.otus.klepov.homeworks.hw15.domain.olddto;
import com.google.gson.annotations.SerializedName;

import java.time.Instant;
import java.util.UUID;

public class Message {
    @SerializedName("ROWID")
    public Long RowId;
    @SerializedName("attributedBody")
    public String AttributedBody;
    @SerializedName("belong_number")
    public String BelongNumber;
    @SerializedName("date")
    public Instant Date;
    @SerializedName("date_read")
    public Instant DateRead;
    @SerializedName("guid")
    public UUID Guid;
    @SerializedName("handle_id")
    public Long HandleId;
    @SerializedName("has_dd_results")
    public Boolean HasDdResults;
    @SerializedName("is_deleted")
    public Boolean IsDeleted;
    @SerializedName("is_from_me")
    public Boolean IsFromMe;
    @SerializedName("send_date")
    public Instant SendDate;
    @SerializedName("send_status")
    public Integer SendStatus;
    @SerializedName("service")
    public String Service;
    @SerializedName("text")
    public String Text;
}
