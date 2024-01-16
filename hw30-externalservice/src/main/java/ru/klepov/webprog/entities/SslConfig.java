package ru.klepov.webprog.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "client.ssl")
@Getter
@Setter
@NoArgsConstructor
public class SslConfig {
    @Value("${client.ssl.trust-store-path}")
    private Resource trustStorePath;
    @Value("${client.ssl.trust-store-password}")
    private String trustStorePass;
    @Value("${client.ssl.trust-store-type}")
    private String trustStoreType;
}
