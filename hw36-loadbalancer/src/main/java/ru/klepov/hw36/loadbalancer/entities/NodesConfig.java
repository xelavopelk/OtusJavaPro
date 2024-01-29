package ru.klepov.hw36.loadbalancer.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "nodes")
@Getter
@Setter
@NoArgsConstructor
public class NodesConfig {
    private Integer port;
}
