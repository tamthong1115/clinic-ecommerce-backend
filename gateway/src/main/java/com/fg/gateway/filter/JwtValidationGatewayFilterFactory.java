package com.fg.gateway.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.*;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
@Slf4j
public class JwtValidationGatewayFilterFactory extends AbstractGatewayFilterFactory<Object> {

    private final RestTemplate restTemplate;
    private final DiscoveryClient discoveryClient;

    public JwtValidationGatewayFilterFactory(RestTemplate restTemplate, DiscoveryClient discoveryClient) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;
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

                // Prepare request with the token
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
                exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                return exchange.getResponse().setComplete();
            } catch (HttpClientErrorException.Unauthorized ex) {
                log.error("401 Unauthorized: Invalid token", ex);
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            } catch (Exception ex) {
                log.error("Error during token validation", ex);
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }
        };
    }
}
