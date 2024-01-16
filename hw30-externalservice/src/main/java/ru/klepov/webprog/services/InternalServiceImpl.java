package ru.klepov.webprog.services;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Optional;

import ru.klepov.webprog.entities.InternalSrvConfig;

public class InternalServiceImpl implements InternalService {
    private final WebClient wc;
    private final RestTemplate restTmpl;
    private final InternalSrvConfig config;

    public InternalServiceImpl(WebClient client, RestTemplate restTmpl, InternalSrvConfig config) {
        this.wc = client;
        this.restTmpl = restTmpl;
        this.config = config;
    }

    public Optional<String> pingWebClient() {
        try {
            var res = wc.get().uri("/app/api/ping").retrieve().bodyToMono(String.class).block();
            return Optional.of(res);
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

    public Optional<String> pingRestTemplate() {
        try {
            ResponseEntity<String> res =
                    restTmpl.getForEntity(config.makeFullUrl() + "/app/api/ping", String.class);
            return Optional.of(res.getBody());
        } catch (Exception ex) {
            return Optional.empty();
        }
    }

}
