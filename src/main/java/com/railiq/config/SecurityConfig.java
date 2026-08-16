package com.railiq.config;

import com.railiq.security.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                // Public JSP pages
                .requestMatchers("/", "/results", "/pnr-status", "/help", "/booking", "/profile", "/login", "/register").permitAll()
                // JSP internal forwards (Spring forwards view names to /WEB-INF/views/*.jsp — this forward is a separate request that also passes through Security)
                .requestMatchers("/WEB-INF/**").permitAll()
                // Spring Boot's error handling forwards internally to /error on any exception —
                // without this, a real 500 gets masked as a misleading 403
                .requestMatchers("/error").permitAll()
                // Static assets
                .requestMatchers("/css/**", "/js/**").permitAll()
                // Public REST APIs
                .requestMatchers("/api/auth/**").permitAll()
                .requestMatchers("/api/trains/**").permitAll()
                .requestMatchers("/api/stations/**").permitAll()
                .requestMatchers("/api/routes/**").permitAll()
                .requestMatchers("/api/historical-runs/**").permitAll()
                .requestMatchers("/api/historical-bookings/**").permitAll()
                .requestMatchers("/api/predictions/**").permitAll()
                // Everything else requires a valid JWT
                .anyRequest().authenticated()
            )
            .csrf(csrf -> csrf.disable())
            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}