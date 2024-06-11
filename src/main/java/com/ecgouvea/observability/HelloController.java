package com.ecgouvea.observability;

import java.time.Instant;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;

@RestController
public class HelloController {

    Counter visitCounter;

    public HelloController(MeterRegistry registry) {
        visitCounter = Counter.builder("visit_counter_hello_operation")
                .description("Number of visits to the api's hello operation")
                .register(registry);
    }

    @GetMapping("/hello")
    public String hello(String name) {
        visitCounter.increment();
        String msg = "Hello, World! " + name + " at " + Instant.now();
        System.out.println(msg);
        return msg;
    }
}
