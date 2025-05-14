package com.fg.patientservice.config;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@Slf4j
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests(authorize ->
                        authorize
                                .requestMatchers("/api/v1/admin/**").hasAuthority("ROLE_ADMIN")
                                .requestMatchers("/api/v1/clinic/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_CLINIC_OWNER")
                                .requestMatchers("/api/v1/doctor/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_CLINIC_OWNER", "ROLE_DOCTOR")
                                .requestMatchers("/api/v1/patient/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_CLINIC_OWNER", "ROLE_PATIENT")
                                .requestMatchers("/api/v1/public/**").permitAll()
                                .anyRequest().authenticated())
                .cors(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .addFilterBefore(new RoleHeaderAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}
