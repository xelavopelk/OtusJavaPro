package ru.klepov.hw36.loadbalancer.entities;

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

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        } else {
            return ((RegistryItem) obj).host == this.host;
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
