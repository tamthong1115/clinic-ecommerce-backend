package com.fg.gateway.filter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class JwtValidationGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    private final RestTemplate restTemplate;
    private final DiscoveryClient discoveryClient;
    private final ObjectMapper objectMapper;

    public JwtValidationGatewayFilterFactory(RestTemplate restTemplate, DiscoveryClient discoveryClient, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            String token = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
            log.debug("Incoming request: {}", exchange.getRequest().getURI());
            log.debug("Authorization header: {}", token);

            if (token == null || !token.startsWith("Bearer ")) {
                log.warn("Missing or invalid Authorization header");
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            // Extract the token without "Bearer " prefix
            String authToken = token.substring(7);

            try {
                // Resolve the auth-service instance dynamically using DiscoveryClient
                List<ServiceInstance> instances = discoveryClient.getInstances("auth-service");
                if (instances.isEmpty()) {
                    log.error("No instances of auth-service found");
                    exchange.getResponse().setStatusCode(HttpStatus.SERVICE_UNAVAILABLE);
                    return exchange.getResponse().setComplete();
                }

                String authServiceUrl = instances.get(0).getUri().toString() + "/api/v1/validate";

                HttpHeaders headers = new HttpHeaders();
                headers.set(HttpHeaders.AUTHORIZATION, "Bearer " + authToken);
                HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

                ResponseEntity<List> response = restTemplate.exchange(
                        authServiceUrl,
                        HttpMethod.POST,
                        requestEntity,
                        List.class
                );

                List<String> roles = response.getBody();
                if (roles == null || roles.isEmpty()) {
                    log.warn("No roles found in response");
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }

                // Add roles as a header in the request
                ServerHttpRequest request = exchange.getRequest().mutate()
                        .header("X-User-Roles", String.join(",", roles))
                        .build();

                return chain.filter(exchange.mutate().request(request).build());

            } catch (HttpClientErrorException.Forbidden ex) {
                log.error("403 Forbidden: Access denied", ex);
                return setErrorResponse(exchange.getResponse(), HttpStatus.FORBIDDEN,
                        "Access denied: " + ex.getMessage());
            } catch (HttpClientErrorException.Unauthorized ex) {
                log.error("401 Unauthorized: Invalid token", ex);
                return setErrorResponse(exchange.getResponse(), HttpStatus.UNAUTHORIZED,
                        "Invalid authentication token");
            } catch (Exception ex) {
                log.error("Error during token validation", ex);
                return setErrorResponse(exchange.getResponse(), HttpStatus.UNAUTHORIZED,
                        "Authentication failed: " + ex.getMessage());
            }
        };
    }


    private Mono<Void> setErrorResponse(ServerHttpResponse response, HttpStatus status, String message) {
        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        Map<String, Object> errorDetails = new HashMap<>();
        errorDetails.put("status", status.value());
        errorDetails.put("error", status.getReasonPhrase());
        errorDetails.put("message", message);

        try {
            byte[] bytes = objectMapper.writeValueAsBytes(errorDetails);
            DataBuffer buffer = response.bufferFactory().wrap(bytes);
            return response.writeWith(Mono.just(buffer));
        } catch (JsonProcessingException e) {
            log.error("Error writing response", e);
            return response.setComplete();
        }
    }
}
