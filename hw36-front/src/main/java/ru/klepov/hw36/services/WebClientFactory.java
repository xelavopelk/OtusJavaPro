package ru.klepov.hw36.services;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.klepov.hw36.entities.AppConfig;

@Component
public class WebClientFactory {
    @Bean
    public WebClient createWebClient(AppConfig config) {
        var wcb = WebClient.builder()
                .baseUrl(String.format("http://%s:%d", config.getBalancer(), config.getBalancerPort()))
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return wcb.build();
    }
}
