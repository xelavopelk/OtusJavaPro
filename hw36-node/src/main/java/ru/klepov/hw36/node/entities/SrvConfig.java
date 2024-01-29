package ru.klepov.hw36.node.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Getter
@Setter
@Component
public class SrvConfig {
    private static Integer DEFAULT_SUCCESS_RATE = 100;
    private static String BALANCER_ENV = "NODE_BALANCER";
    private static String PORT_BALANCER_ENV = "PORT_BALANCER";
    private static String SUCCESS_RATE_ENV = "SUCCESS_RATE";
    private final String balancer;
    private final Integer balancerPort;
    private final String hostname;
    private final Integer successRate;

    public SrvConfig() throws UnknownHostException {
        balancer = System.getenv(BALANCER_ENV);
        balancerPort = Integer.parseInt(System.getenv(PORT_BALANCER_ENV));
        successRate = null == System.getenv(PORT_BALANCER_ENV) ? DEFAULT_SUCCESS_RATE : Integer.parseInt(System.getenv(SUCCESS_RATE_ENV));
        hostname = InetAddress.getLocalHost().getHostName();
    }
}
