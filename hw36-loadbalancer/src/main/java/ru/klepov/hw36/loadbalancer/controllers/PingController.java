package ru.klepov.hw36.loadbalancer.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/ping")
public class PingController {
    @GetMapping()
    public String ping() {
        return "pong-lb";
    }
}