package ru.klepov.hw36.services;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.klepov.hw36.RegistryItem;
import ru.klepov.hw36.entities.AppConfig;
import ru.klepov.hw36.entities.RegistryListResponse;

import java.util.List;

@Service
public class BalancerService {
    private final AppConfig config;
    private final WebClient wc;

    public BalancerService(AppConfig config, WebClient wc) {
        this.config = config;
        this.wc = wc;
    }

    public List<RegistryItem> getListNodes() {
        var res = wc.get()
                .uri("/lb/api/registry")
                .retrieve()
                .bodyToMono(RegistryListResponse.class)
                .block();
        return res.getList();
    }
    public String ping() {
        try {
            return wc.get().uri("/lb/api/ping").retrieve().bodyToMono(String.class).block();
        } catch (Exception ex) {
            return  ex.getMessage();
        }
    }
}
