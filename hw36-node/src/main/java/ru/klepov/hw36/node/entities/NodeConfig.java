package ru.klepov.hw36.node.entities;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "server")
@Getter
@Setter
@NoArgsConstructor
public class NodeConfig {
    @Value("${server.port}")
    private Integer port;
}
