package ru.klepov.hw36.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
public class AppConfig {
    private static String BALANCER_ENV = "NODE_BALANCER";
    private static String PORT_BALANCER_ENV = "PORT_BALANCER";
    private final String balancer;
    private final Integer balancerPort;

    public AppConfig() {
        balancer = System.getenv(BALANCER_ENV);
        balancerPort = Integer.parseInt(System.getenv(PORT_BALANCER_ENV));
    }
}
