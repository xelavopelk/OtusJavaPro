package ru.klepov.hw36.node.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.klepov.hw36.node.entities.SrvConfig;

@RestController
@RequestMapping("/srv/util")
public class UtilController {
    private final SrvConfig config;

    public UtilController(SrvConfig config) {
        this.config = config;
    }

    @GetMapping("/who")
    public String who() {
        return config.getHostname();
    }
}
