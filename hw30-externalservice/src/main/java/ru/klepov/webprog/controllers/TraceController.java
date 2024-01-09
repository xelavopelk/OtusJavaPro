package ru.klepov.webprog.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.klepov.webprog.entities.TraceData;
import ru.klepov.webprog.entities.TraceItem;
import ru.klepov.webprog.services.InternalService;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Optional;
import java.util.function.Supplier;


@RestController
@RequestMapping("/api/trace")
public class TraceController {
    private InternalService srv;

    public TraceController(InternalService srv) {
        this.srv = srv;
    }

    private ResponseEntity<TraceData> executor(Supplier<Optional<String>> fun, String data) {
        var start = Instant.now();
        var res = new TraceData(new ArrayList<>());
        String resDt;
        try {
            var r = fun.get();
            if (r.isEmpty()) {
                throw new Exception("internal service error");
            } else {
                resDt = r.get();
            }
        } catch (Exception ex) {
            resDt = ex.getMessage();
        }
        res.getItems().add(new TraceItem(start, Instant.now(), "external:" + data, resDt));
        return ResponseEntity.ok(res);
    }


    @GetMapping("/webclient")
    public ResponseEntity<TraceData> webclient(@RequestParam String data) {
        return executor(srv::pingWebClient, data);
    }

    @GetMapping("/resttemplate")
    public ResponseEntity<TraceData> restTemplate(@RequestParam String data) {
        return executor(srv::pingRestTemplate, data);
    }
}
