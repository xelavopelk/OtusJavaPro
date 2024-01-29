package ru.klepov.hw36.loadbalancer.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class RegistryItem {
    private String host;
    private Integer port;
    private Instant registerTime;
    private Integer counter;

    public RegistryItem(String host, Instant registerTime, Integer counter, Integer port) {
        Objects.requireNonNull(host, "Host must be not null");
        Objects.requireNonNull(registerTime, "registerTime must be not null");
        Objects.requireNonNull(counter, "counter must be not null");
        Objects.requireNonNull(counter, "port must be not null");
        this.host = host;
        this.port = port;
        this.registerTime = registerTime;
        this.counter = counter;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        } else {
            return ((RegistryItem) obj).host.equals(this.host);
        }
    }

    @Override
    public int hashCode() {
        return host.hashCode();
    }

    public void incrementCounter() {
        counter++;
    }
}
