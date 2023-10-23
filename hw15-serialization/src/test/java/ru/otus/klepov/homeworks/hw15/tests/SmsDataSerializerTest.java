package ru.otus.klepov.homeworks.hw15.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.otus.klepov.homeworks.hw15.service.DateTypeAdapter;
import ru.otus.klepov.homeworks.hw15.service.SmsDataSerializer;
import ru.otus.klepov.homeworks.hw15.service.Utils;
import ru.otus.klepov.homeworks.hw15.domain.olddto.Member;
import ru.otus.klepov.homeworks.hw15.domain.olddto.Message;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SmsDataSerializerTest {

    private SmsDataSerializer s;

    @BeforeEach
    public void Init() {
        s = new SmsDataSerializer();
    }

    @Test
    public void happyPathSuccess() {
        var jsonInput = Utils.readFile("sms.json");
        var output = jsonInput.flatMap(s::deserialize);
        assertTrue(output.isRight());
    }

    @Test
    public void noFileFail() {
        var jsonInput = Utils.readFile("sms1.json");
        var output = jsonInput.flatMap(s::deserialize);
        assertTrue(output.isLeft());
    }

    @Test
    public void sessionCountSuccess() {
        var output = Utils
                .readFile("sms.json")
                .flatMap(s::deserialize)
                .map(data -> data.sessions.size());
        Assertions.assertEquals(1, output.get());
    }

    @Test
    public void sessionDataSuccess() {
        var chat_id = 946;
        var chat_identifier = "Apple";
        var display_name = "";
        var is_deleted = 0;
        var output = Utils
                .readFile("simpleTestData.json")
                .flatMap(s::deserialize)
                .map(data -> data.sessions.get(0))
                .map(session -> session.id == chat_id &&
                        session.chatIdentifier.equals(chat_identifier) &&
                        session.DisplayName.equals(display_name) &&
                        session.IsDeleted == is_deleted &&
                        session.members.size() == 1 &&
                        session.messages.size() == 1);
        assertTrue(output.get());
    }

    @Test
    public void sessionMemberDataSuccess() {
        var first = "Moskow";
        var handle_id = 934;
        var image_path = "N:\\Tenorshare iCareFone\\Temp\\AnalysisTemp\\113.jpg";
        var last = "Saint-Petersburg";
        var middle = "Bologoe";
        var phone_number = "Apple";
        var service = "SMS";
        var thumb_path = "N:\\Tenorshare iCareFone\\Temp\\AnalysisTemp\\113.thumb";
        Function<Member, Boolean> checkMember = member -> member.First.equals(first) &&
                member.HandleId == handle_id &&
                member.ImagePath.equals(image_path) &&
                member.Last.equals(last) &&
                member.Middle.equals(middle) &&
                member.PhoneNumber.equals(phone_number) &&
                member.Service.equals(service) &&
                member.ThumbPath.equals(thumb_path);

        var output = Utils
                .readFile("simpleTestData.json")
                .flatMap(s::deserialize)
                .map(data -> data.sessions.get(0))
                .map(session -> session.members.get(0))
                .map(checkMember);
        Assertions.assertTrue(output.get());
    }

    @Test
    public void sessionMessageDataSuccess() throws ParseException {
        var ROWID = 19315;
        var attributedBody = "BAtzdHJlYW10eXBlZIHoA4QBQISABClOU011dGFibGVBdHRyaWJ1dGVkU3RyaW5nAISEEk5TQXR0cmlidXRlZFN0cmluZwCEhAhOU09iamVjdACFkoSEhA9OU011dGFibGVTdHJpbmcBhIQITlNTdHJpbmcBlYQBK4GAAFBST0JMRU0gQ1JJVElDQUwgUEdfU01FIC0gRlAgcHJvY2Vzc2luZyBzdGF0dXMgbG9jYWxob3N0IDEyNy4wLjAuMSBFUlJPUi4gUmVzdWx0PVRoZXJlIGFyZSBbMV0gcGF5bWVudHMgaW4gRlAgcHJvY2Vzc2luZyBzdGF0dXM6hoQCaUkBOZKEhIQMTlNEaWN0aW9uYXJ5AJWEAWkBkoSYmB1fX2tJTU1lc3NhZ2VQYXJ0QXR0cmlidXRlTmFtZYaShISECE5TTnVtYmVyAISEB05TVmFsdWUAlYQBKoSbmwCGhpkCCZKEmpsCkoSYmB5fX2tJTURhdGFEZXRlY3RlZEF0dHJpYnV0ZU5hbWWGkoSEhAZOU0RhdGEAlZuBLQKEBls1NTdjXWJwbGlzdDAw1AECAwQFBgcMWCR2ZXJzaW9uWSRhcmNoaXZlclQkdG9wWCRvYmplY3RzEgABhqBfEA9OU0tleWVkQXJjaGl2ZXLSCAkKC1d2ZXJzaW9uWWRkLXJlc3VsdIALgAGsDQ4cJCUmLC0uMjY6VSRudWxs1w8QERITFBUWFxgZGhsaUk1TViRjbGFzc1JBUlFUUVBSU1JSVk6ABoAKgAKABxABgAjUHR4fECAhIiNfEBJOUy5yYW5nZXZhbC5sZW5ndGhfEBROUy5yYW5nZXZhbC5sb2NhdGlvblpOUy5zcGVjaWFsgAOABBAEgAUQCRA50icoKSpaJGNsYXNzbmFtZVgkY2xhc3Nlc1dOU1ZhbHVloikrWE5TT2JqZWN0WTEyNy4wLjAuMVlJUEFkZHJlc3PSLxAwMVpOUy5vYmplY3RzoIAJ0icoMzReTlNNdXRhYmxlQXJyYXmjMzUrV05TQXJyYXnSJyg3OF8QD0REU2Nhbm5lclJlc3VsdKI5K18QD0REU2Nhbm5lclJlc3VsdBABAAgAEQAaACQAKQAyADcASQBOAFYAYABiAGQAcQB3AIYAiQCQAJMAlQCXAJoAnQCfAKEAowClAKcAqQCyAMcA3gDpAOsA7QDvAPEA8wD1APoBBQEOARYBGQEiASwBNgE7AUYBRwFJAU4BXQFhAWkBbgGAAYMBlQAAAAAAAAIBAAAAAAAAADsAAAAAAAAAAAAAAAAAAAGXhpKbkpyGmQE+hg==";
        var belong_number = "+79219213267";
        var date = new Date(701953759344326016L);
        var date_read = new Date(701953934692792192L);
        var guid = UUID.fromString("A6EF7781-F25E-0351-8847-140D0810A973");
        var handle_id = 934;
        var has_dd_results = true;
        var is_deleted = false;
        var is_from_me = false;
        SimpleDateFormat df = new SimpleDateFormat(DateTypeAdapter.DATE_PATTERN);
        var send_date = df.parse("03-31-2023 14:09:19");
        var send_status = 0;
        var service = "SMS";
        var text = "PROBLEM CRITICAL - There is hight price of the order";
        Function<Message, Boolean> checkMessage = message -> message.RowId == ROWID &&
                message.AttributedBody.equals(attributedBody) &&
                message.BelongNumber.equals(belong_number) &&
                message.Date.equals(date) &&
                message.DateRead.equals(date_read) &&
                message.Guid.equals(guid) &&
                message.HandleId == handle_id &&
                message.HasDdResults == has_dd_results &&
                message.IsDeleted == is_deleted &&
                message.IsFromMe == is_from_me &&
                message.SendDate.equals(send_date) &&
                message.SendStatus == send_status &&
                message.Service.equals(service) &&
                message.Text.equals(text);
        var output = Utils
                .readFile("simpleTestData.json")
                .flatMap(s::deserialize)
                .map(data -> data.sessions.get(0))
                .map(session -> session.messages.get(0))
                .map(checkMessage);
        Assertions.assertTrue(output.get());

    }
}
