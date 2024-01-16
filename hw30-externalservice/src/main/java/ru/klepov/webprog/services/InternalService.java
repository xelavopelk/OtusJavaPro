package ru.klepov.webprog.services;

import java.util.Optional;

public interface InternalService {
    Optional<String> pingWebClient();

    Optional<String> pingRestTemplate();
}
