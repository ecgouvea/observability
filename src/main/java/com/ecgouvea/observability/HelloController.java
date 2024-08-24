package com.ecgouvea.observability;

import java.time.Instant;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import io.micrometer.core.annotation.Timed;
import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.TimeGauge;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.distribution.Histogram;

@RestController
public class HelloController {

    // Counter visitCounter;
    // Counter totalRequestTime;
    io.micrometer.core.instrument.Timer timer;
    io.micrometer.core.instrument.Gauge gauge;

    private final RestTemplate restTemplate;
    private MeterRegistry registry;

    Histogram methodExecutionTime;
    Counter methodInvocations;

    public HelloController(MeterRegistry registry) {
        this.restTemplate = new RestTemplate();
        this.registry = registry;

        // Create a histogram to record method durations
        // methodExecutionTime = Histogram.build()
        // .name("my_method_execution_time_1")
        // .help("Method execution time in seconds")
        // .register(registry);

        // Create a counter for tracking method invocations
        // methodInvocations = Counter.build()
        // .name("my_method_invocations_total_1")
        // .help("Total number of method invocations")
        // .register();

        // Visit counter
        // visitCounter = Counter.builder("visit_counter_hello_operation")
        // .description("Number of visits to the api's hello operation")
        // .register(registry);

        // Total request time
        // totalRequestTime = Counter.builder("total_request_time_hello_operation")
        // .description("Total request time to the api's hello operation")
        // .register(registry);

    }

    @GetMapping("/")
    public String rootContext() {
        return "Call /hello instead";
    }

    @GetMapping("/hello")
    public String hello(String name) {
        // try (Histogram.Timer timer = methodExecutionTime.startTimer()) {
        // Your method logic here

        // timer = Timer.builder("my_timer_hello_operation").register(this.registry);
        // Timer methodExecutionTimer = Metrics.timer("my_timer_hello_operation");
        // Timer.Sample sample = Timer.start(/* registry */);
        String response = "";
        String msg = "";

        long startTime = System.currentTimeMillis();

        // Visite counter
        // visitCounter.increment();

        msg = "Hello, World! " + name + " at " + Instant.now();
        System.out.println(msg);

        // Wait some seconds
        try {
            long sleepInMillis = 1_000 * (name != null ? Math.round(Math.random() * name.length()) : 0);
            System.out.println("Will wait for " + sleepInMillis + " ms");
            Thread.sleep(sleepInMillis);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        if ("John".equals(name)) {
            System.out.println("Calling spring-prometheus-demo:8080/");
            String url = String.format("http://spring-prometheus-demo:8080/", name);
            response = this.restTemplate.getForObject(url, String.class);
            System.out.println("Second service returned " + response);
        }

        // Set request time taken
        long endTime = System.currentTimeMillis();
        Long totalRequestTime = endTime - startTime;
        System.out.println("Time taken to total request: " + totalRequestTime + " ms");
        // timer.record(totalRequestTime, TimeUnit.NANOSECONDS);
        // sample.stop(methodExecutionTimer);
        // TimeGauge timeGauge = TimeGauge.builder("my_timer_hello_operation_4",
        // totalRequestTime::longValue,
        // TimeUnit.MILLISECONDS)
        // .register(registry);

        // methodInvocations.inc();

        timer = Timer.builder("my_timer_hello_operation_5").register(this.registry);
        timer.record(totalRequestTime, TimeUnit.MILLISECONDS);

        return msg + "; Second service returned: " + response;
        // }
    }
}
