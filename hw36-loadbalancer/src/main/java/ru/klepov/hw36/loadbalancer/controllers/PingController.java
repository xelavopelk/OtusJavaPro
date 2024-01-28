package ru.klepov.hw36.loadbalancer.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.klepov.hw36.loadbalancer.entities.NodesConfig;
import ru.klepov.hw36.loadbalancer.services.RegisterService;
import ru.klepov.hw36.loadbalancer.services.WebClientFactory;

@RestController
@RequestMapping("/api/ping")
public class PingController {
    static final Logger logger = LoggerFactory.getLogger(PingController.class);
    private static String NODE_HEADER = "node";
    private final NodesConfig config;
    private final RegisterService register;
    private final WebClientFactory wcFactory;

    public PingController(NodesConfig config, RegisterService register, WebClientFactory wcFactory) {
        this.config = config;
        this.register = register;
        this.wcFactory = wcFactory;
    }

    @GetMapping()
    public ResponseEntity<String> ping() {
        var node = register.getNext();
        if (node.isEmpty()) {
            return new ResponseEntity<>("There aren't any registered node", HttpStatus.INTERNAL_SERVER_ERROR);
        } else {
            var wc = wcFactory.createWebClient(node.get());
            var responseHeaders = new HttpHeaders();
            responseHeaders.set(NODE_HEADER, node.get().getHost());
            try {
                var res = wc.get().uri("/srv/ping").retrieve().bodyToMono(String.class).block();
                return new ResponseEntity<>(res, responseHeaders, HttpStatus.OK);
            } catch (Exception ex) {
                logger.error(String.format("node '%s' error mess: %s", node, ex.getMessage()));
                register.unregister(node.get().getHost());
                logger.error(String.format("node '%s' error and it have been unregistered", node), ex);
                return new ResponseEntity<>(responseHeaders, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }
}