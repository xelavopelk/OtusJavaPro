package ru.klepov.webprog.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "internalservice")
@Getter
@Setter
@NoArgsConstructor
public class InternalSrvConfig {
    private String url;
    private Integer port;
    private Integer timeout;
    public String makeFullUrl() {
        return String.format("%s:%d", getUrl(),getPort());
    }
}
