package ru.klepov.hw36;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegistryItem {
    private String host;
    private Instant registerTime;
    private Integer counter;
}