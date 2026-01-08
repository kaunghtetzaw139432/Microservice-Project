package com.gateway.filter;

import java.util.List;
import java.util.function.Predicate;

import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

@Component
public class Validator {

    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    /**
     * Public endpoints (NO JWT REQUIRED)
     */
    private static final List<String> PUBLIC_ENDPOINTS = List.of(
            "/auth/register",
            "/auth/generateToken",
            "/auth/generateToken/**",
            "/auth/validateToken",
            "/auth/validateToken/**"
    );

    /**
     * true  -> SECURED (JWT REQUIRED)
     * false -> PUBLIC  (NO JWT)
     */
    public Predicate<ServerHttpRequest> predicate = request -> {

        String requestPath = request.getURI().getPath();
        System.out.println("Request Path: " + requestPath);

        boolean isPublic = PUBLIC_ENDPOINTS.stream()
                .anyMatch(pattern -> pathMatcher.match(pattern, requestPath));

        // secured endpoint = NOT public
        return !isPublic;
    };
}
