package ru.klepov.hw36.loadbalancer.services;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WebClientFactory {
    public WebClient createWebClient(String node, Integer port) {
        var wcb = WebClient.builder()
                .baseUrl(String.format("http://%s:%d", node, port));
        return wcb.build();
    }
}