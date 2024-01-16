package ru.klepov.webprog.services;

import io.netty.handler.ssl.SslContextBuilder;
import org.springframework.boot.web.reactive.function.client.WebClientCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;
import ru.klepov.webprog.entities.InternalSrvConfig;
import ru.klepov.webprog.entities.SslConfig;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.List;

@Service
public class WebClientFactory {
    @Bean
    public WebClient createWebClient(InternalSrvConfig config, SslConfig sslConfig) {
        var wcb = WebClient.builder().baseUrl(config.makeFullUrl());
        var customizer = getCustomizer(sslConfig);
        customizer.customize(wcb);
        return wcb.build();
    }


    private WebClientCustomizer getCustomizer(SslConfig sslConfig) {
        return (WebClient.Builder webClientBuilder) -> {
            try {
                final var trustStore = KeyStore.getInstance(sslConfig.getTrustStoreType());
                trustStore.load(new FileInputStream(ResourceUtils.getFile(sslConfig.getTrustStorePath().getURI())), sslConfig.getTrustStorePass().toCharArray());
                List<Certificate> certificateList = Collections.list(trustStore.aliases())
                        .stream()
                        .filter(item -> {
                            try {
                                return trustStore.isCertificateEntry(item);
                            } catch (KeyStoreException ex) {
                                return false;
                            }
                        })
                        .map(item -> {
                            try {
                                return trustStore.getCertificate(item);
                            } catch (KeyStoreException ex) {
                                throw new RuntimeException("Error using truststore", ex);
                            }
                        })
                        .toList();
                final var sslContext = SslContextBuilder.forClient()
                        .trustManager(certificateList.toArray(new X509Certificate[certificateList.size()]))
                        .build();

                HttpClient httpClient = HttpClient.create()
                        .secure(sslContextSpec -> sslContextSpec.sslContext(sslContext));
                var connector = new ReactorClientHttpConnector(httpClient);
                webClientBuilder.clientConnector(connector);
            } catch (KeyStoreException | CertificateException | NoSuchAlgorithmException | IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

}
