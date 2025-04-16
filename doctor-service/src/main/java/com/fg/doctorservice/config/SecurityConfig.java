package com.fg.doctorservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authorize ->
                        authorize
//                                .requestMatchers("/api/v1/login/**", "/api/v1/register/**", "/swagger-ui.html", "/swagger/**", "/v3/**", "/v3/api-docs/**", "/swagger-ui/index.html", "/swagger-ui/**")
//                                .permitAll()
                                .requestMatchers("/api/v1/doctors/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_DOCTOR")
                                .anyRequest().authenticated())
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(new RoleHeaderAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
