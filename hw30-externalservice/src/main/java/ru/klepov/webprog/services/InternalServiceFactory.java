package ru.klepov.webprog.services;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import ru.klepov.webprog.entities.InternalSrvConfig;


@Service
public class InternalServiceFactory {
    @Bean
    public InternalService create(WebClient client, RestTemplate restTmpl, InternalSrvConfig config) {
        return new InternalServiceImpl(client, restTmpl, config);
    }
}
