package com.zoomout.server.reactivejava17server;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Random;
import java.util.UUID;

@RestController
public class ReactiveServerController {

    private static final Random RANDOM = new Random();
    private static final int RANDOM_MAX = 500;

    @GetMapping("/server/string")
    public Mono<ResponseEntity<String>> getString() {
        return Mono.just(UUID.randomUUID())
                .delayElement(Duration.ofMillis(RANDOM.nextInt(RANDOM_MAX)))
                .map(UUID::toString)
                .map(ResponseEntity::ok);
    }

}
