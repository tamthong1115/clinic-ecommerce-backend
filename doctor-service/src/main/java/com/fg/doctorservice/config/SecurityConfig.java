package com.fg.appointment_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
                                .requestMatchers("/api/v1/admin/**").hasAuthority("ROLE_ADMIN")
                                .requestMatchers("/api/v1/clinic/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_CLINIC")
                                .requestMatchers("/api/v1/doctor/**").hasAnyAuthority("ROLE_ADMIN","ROLE_CLINIC", "ROLE_DOCTOR")
                                .requestMatchers("/api/v1/patient/**").hasAnyAuthority("ROLE_ADMIN","ROLE_CLINIC", "ROLE_DOCTOR", "ROLE_PATIENT")
                                .requestMatchers(HttpMethod.DELETE, "/api/v1/clinic/**/doctors/**/schedules/**").hasAnyAuthority("ROLE_ADMIN","ROLE_CLINIC")
                                .requestMatchers(HttpMethod.POST, "/api/v1/clinic/**/doctors/**/schedules/**").hasAnyAuthority("ROLE_ADMIN","ROLE_CLINIC")
                                .requestMatchers(HttpMethod.GET, "/api/v1/clinic/**/doctors/**/schedules/**").hasAnyAuthority("ROLE_ADMIN","ROLE_CLINIC", "ROLE_DOCTOR")
                                .requestMatchers(HttpMethod.PUT, "/api/v1/clinic/**/doctors/**/schedules/**").hasAnyAuthority("ROLE_ADMIN","ROLE_CLINIC", "ROLE_DOCTOR")
                                .requestMatchers("/api/v1/public/**").permitAll()
                                .anyRequest().authenticated())
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(new RoleHeaderAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
