package com.zoomout.client.reactivejava17client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@RestController
public class ReactiveClientController {

    private final WebClient client;

    public ReactiveClientController(
            @Value("${reactive.server.base-url}") String baseUrl
    ) {
        client = WebClient.create(baseUrl);
    }

    @GetMapping("/client/string")
    public Mono<ResponseEntity<String>> getString() {
        return client.method(HttpMethod.GET)
                .uri("/server/string")
                .retrieve()
                .bodyToMono(String.class)
                .map(ResponseEntity::ok);
    }

}
