package ru.klepov.hw36.loadbalancer.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.klepov.hw36.loadbalancer.entities.RegisterRequest;
import ru.klepov.hw36.loadbalancer.entities.RegisterResponse;
import ru.klepov.hw36.loadbalancer.entities.RegistryListResponse;
import ru.klepov.hw36.loadbalancer.services.RegisterService;

@RestController
@RequestMapping("/api/registry")
public class RegistryController {
    static final Logger logger = LoggerFactory.getLogger(RegistryController.class);
    private final RegisterService registry;

    public RegistryController(RegisterService registry) {
        this.registry = registry;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest request) {
        logger.info(String.format("Try register '%s'", request.getHostName()));
        var ri = registry.register(request.getHostName());
        RegisterResponse res = new RegisterResponse(ri.getRegisterTime());
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping()
    public ResponseEntity<RegistryListResponse> getList() {
        var res = new RegistryListResponse(registry.getList());
        return new ResponseEntity<>(res, HttpStatus.OK);
    }
}
