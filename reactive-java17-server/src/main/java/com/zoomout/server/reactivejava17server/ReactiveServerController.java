package com.zoomout.server.reactivejava17server;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.UUID;

@RestController
public class ReactiveServerController {

    @GetMapping("/server/string")
    public Mono<ResponseEntity<String>> getString(
            @RequestParam("d") Integer delayMs
    ) {
        return Mono.just(UUID.randomUUID())
                .delayElement(Duration.ofMillis(delayMs))
                .map(UUID::toString)
                .map(ResponseEntity::ok);
    }

}
