package ru.klepov.hw36.node;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NodeProgram {

    public static void main(String[] args) {
        SpringApplication.run(NodeProgram.class, args);
    }

}
