package ru.klepov.webprog.services;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.ssl.SSLContextBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.klepov.webprog.entities.SslConfig;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;

@Service
public class RestTemplateFactory {
    @Bean
    public RestTemplate createRestTemplate(SslConfig sslConfig) {
        try {
            var truststoreFile = sslConfig.getTrustStorePath().getFile();
            SSLContext sslContext = new SSLContextBuilder()
                    .loadTrustMaterial(truststoreFile, sslConfig.getTrustStorePass().toCharArray())
                    .build();

            var sslConFactory = new SSLConnectionSocketFactory(sslContext);
            HttpClientConnectionManager connectionManager = PoolingHttpClientConnectionManagerBuilder.create()
                    .setSSLSocketFactory(sslConFactory).build();
            CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
            ClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient);
            return new RestTemplate(requestFactory);
        } catch (IOException | CertificateException | NoSuchAlgorithmException | KeyStoreException | KeyManagementException ex) {
            throw new RuntimeException(ex);
        }
    }
}
