package com.fg.authservice.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class SecurityConfig {
    @Value("${application.frontend.url}")
    private String frontendUrl;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("Frontend URL: {}", frontendUrl);

        http.authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/api/v1/login/**", "/api/v1/register/**", "/swagger-ui.html", "/swagger/**", "/v3/**","/v3/api-docs/**","/swagger-ui/index.html", "/swagger-ui/**")
                                .permitAll()
                                .anyRequest().authenticated())
                .csrf(AbstractHttpConfigurer::disable);


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
