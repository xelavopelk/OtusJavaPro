package ru.klepov.webprog.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TraceItem {
    private Instant start;
    private Instant finish;
    private String server;
    private String result;
}
