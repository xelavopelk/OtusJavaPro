package ru.klepov.hw36.loadbalancer.services;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import ru.klepov.hw36.loadbalancer.entities.RegistryItem;

@Component
public class WebClientFactory {
    public WebClient createWebClient(RegistryItem node) {
        var wcb = WebClient.builder()
                .baseUrl(String.format("http://%s:%d", node.getHost(), node.getPort()));
        return wcb.build();
    }
}