package com.zoomout.client.reactivejava17client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.IntStream;

@RestController
public class ReactiveClientController {

    private final WebClient client;

    public ReactiveClientController(
            @Value("${reactive.server.base-url}") String baseUrl
    ) {
        client = WebClient.create(baseUrl);
    }

    @GetMapping("/client/string")
    public Mono<List<String>> getString(
            @RequestParam("n") Integer number,
            @RequestParam("d") Integer delayMs
    ) {
        return Flux.fromStream(IntStream.rangeClosed(0, number).boxed())
                .flatMap(ignored -> getStringFromServer(delayMs))
                .collectList();
    }

    private Mono<String> getStringFromServer(Integer delayMs) {
        return client.method(HttpMethod.GET)
                .uri("/server/string?d=" + delayMs)
                .retrieve()
                .bodyToMono(String.class);
    }

}
