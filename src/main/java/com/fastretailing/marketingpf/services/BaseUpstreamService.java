package com.fastretailing.marketingpf.services;

import com.fastretailing.marketingpf.configs.Config;
import com.fastretailing.marketingpf.exceptions.MkdbApiUpstreamFailureException;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClient.RequestHeadersSpec;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@Service
public class BaseUpstreamService {

    @Autowired
    public Config config;

    /**
     * Build web client for requesting MKDIB-API
     *
     * @return WebClient
     */
    public WebClient getWebClient() {
        return WebClient.builder()
                .baseUrl(config.getMkdbApi().getBaseUrl())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    /**
     * Execute requestHeadersSpec and return mono object<br>
     * Throw MkdbApiUpstreamFailureException if response is not OK
     *
     * @param <T>
     * @param requestHeadersSpec
     * @param monoClass
     * @return T
     */
    private <T> T requestHeadersSpecToMono(RequestHeadersSpec<?> requestHeadersSpec, Class<T> monoClass) {
        return requestHeadersSpec.exchangeToMono(response -> {
            if (response.statusCode().equals(HttpStatus.OK)) {
                return response.bodyToMono(monoClass);
            }
            return response.createException().flatMap(Mono::error);
        }).doOnError(WebClientResponseException.class, e -> {
            throw new MkdbApiUpstreamFailureException("Mkdb return error response, status = " + e.getStatusCode(), e,
                    e.getStatusCode(), e.getResponseBodyAsString());
        }).block();
    }

    /**
     * Get as mono object
     *
     * @param <T>
     * @param uri
     * @param monoClass
     * @return T
     */
    public <T> T getAsMono(URI uri, Class<T> monoClass) {
        return requestHeadersSpecToMono(
                getWebClient().get().uri(uriBuilder -> uriBuilder.path(uri.getPath()).query(uri.getQuery()).build()),
                monoClass);
    }

    /**
     * Do put request and parse response to mono class
     *
     * @param uri
     * @param object
     * @param monoClass
     * @param <T>
     * @return T
     */
    public <T> T putForMono(URI uri, Object object, Class<T> monoClass) {
        return requestHeadersSpecToMono(
                getWebClient().put().uri(uriBuilder -> uriBuilder.path(uri.getPath()).query(uri.getQuery()).build()).bodyValue(object),
                monoClass);
    }

    /**
     * Do put request and parse response to mono class
     *
     * @param <T>
     * @param uri
     * @param monoClass
     * @return T
     */
    public <T> T putForMono(URI uri, Class<T> monoClass) {
        return requestHeadersSpecToMono(
                getWebClient().put().uri(uriBuilder -> uriBuilder.path(uri.getPath()).query(uri.getQuery()).build()),
                monoClass);
    }

    /**
     * Do post request and parse response to mono class
     *
     * @param uri
     * @param object
     * @param monoClass
     * @return <T>
     */
    public <T> T postForMono(URI uri, Object object, Class<T> monoClass) {
        return requestHeadersSpecToMono(
                getWebClient().post().uri(uriBuilder -> uriBuilder.path(uri.getPath()).query(uri.getQuery()).build()).bodyValue(object),
                monoClass);
    }

    /**
     * Do delete request and parse response to mono
     *
     * @param uri
     * @param monoClass
     * @param <T>
     * @return T
     */
    public <T> T deleteForMono(URI uri, Class<T> monoClass) {
        return requestHeadersSpecToMono(
                getWebClient().delete().uri(uriBuilder -> uriBuilder.path(uri.getPath()).query(uri.getQuery()).build()),
                monoClass);
    }
}
