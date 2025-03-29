package com.fg.gateway.filter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class JwtValidationGatewayFilterFactory extends
        AbstractGatewayFilterFactory<Object> {

    private final WebClient webClient;
    private final DiscoveryClient discoveryClient;


    public JwtValidationGatewayFilterFactory(WebClient.Builder webClientBuilder,
                                             DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
        this.webClient = webClientBuilder.build();
    }

    @Override
    public GatewayFilter apply(Object config) {
        return (exchange, chain) -> {
            String token =
                    exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

            if (token == null || !token.startsWith("Bearer ")) {
                exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                return exchange.getResponse().setComplete();
            }

            String authBaseUrl = getBaseUrl("auth-service");

            return webClient.get()
                    .uri(authBaseUrl + "/api/v1/validate")
                    .header(HttpHeaders.AUTHORIZATION, token)
                    .retrieve()
                    .toBodilessEntity()
                    .then(chain.filter(exchange));
        };
    }

    public String getBaseUrl(String serviceId) {
        return discoveryClient.getInstances(serviceId)
                .stream()
                .findFirst()
                .map(instance -> instance.getUri().toString())
                .orElse("Service not found");
    }
}
