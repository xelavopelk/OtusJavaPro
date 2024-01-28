package ru.klepov.hw36.node.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import ru.klepov.hw36.node.entities.NodeConfig;
import ru.klepov.hw36.node.entities.RegisterRequest;
import ru.klepov.hw36.node.entities.RegisterResponse;
import ru.klepov.hw36.node.entities.SrvConfig;


@Service
public class BalancerService {
    static final Logger logger = LoggerFactory.getLogger(BalancerService.class);

    private final SrvConfig config;
    private final WebClient client;
    private final NodeConfig nodeConfig;

    public BalancerService(SrvConfig config, WebClient client, NodeConfig nodeConfig) {
        this.config = config;
        this.client = client;
        this.nodeConfig = nodeConfig;
    }

    @Scheduled(fixedDelayString = "${server.register-interval}")
    public void Register() {
        try {
            logger.info(String.format("Try register on %s:%d", config.getBalancer(), config.getBalancerPort()));
            client.post()
                    .uri("/lb/api/registry")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(Mono.just(new RegisterRequest(config.getHostname(),nodeConfig.getPort())), RegisterRequest.class)
                    .retrieve()
                    .bodyToMono(RegisterResponse.class)
                    .block();
            logger.info(String.format("Successfully registered on %s", config.getBalancer()));
        } catch (Exception ex) {
            logger.error(ex.getMessage());
        }
    }
}
