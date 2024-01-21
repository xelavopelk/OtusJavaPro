package ru.klepov.hw36.node.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.klepov.hw36.node.entities.SrvConfig;

import java.util.concurrent.ThreadLocalRandom;


@RestController
@RequestMapping("/ping")
public class PingController {
    private final Integer RND_UP_BORDER = 101;
    private final SrvConfig config;

    public PingController(SrvConfig config) {
        this.config = config;
    }

    @GetMapping()
    public ResponseEntity<String> ping() {
        int randomNum = ThreadLocalRandom.current().nextInt(0, RND_UP_BORDER);
        if(randomNum>config.getSuccessRate()) {
            return new ResponseEntity<>("Shit happens at this node...", HttpStatus.SERVICE_UNAVAILABLE);
        }
        else {
            return new ResponseEntity<>("pong", HttpStatus.OK);
        }
    }
}