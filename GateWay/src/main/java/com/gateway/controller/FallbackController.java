package com.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
public class FallbackController {

    @GetMapping("/employeeServiceFallback")
    public Mono<String> employeeServiceFallBack() {
        return Mono.just("Employee Service is temporarily unavailable. Please try again later.");
    }

    @GetMapping("/addressServiceFallback")
    public Mono<String> addressServiceFallBack() {
        return Mono.just("Address Service is temporarily unavailable. Please try again later.");
    }
}