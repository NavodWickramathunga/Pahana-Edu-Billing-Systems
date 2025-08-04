// File: src/main/java/com/pahanaedu/billingsystem/config/SecurityConfig.java
package com.pahanaedu.billingsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable) // Disable CSRF for REST APIs

                .authorizeHttpRequests(authorize -> authorize
                        // --- Public Endpoints (Accessible without authentication) ---
                        // These are endpoints for registration, login, and read-only data

                        // Customer Endpoints (Registration, Login, and Profile Viewing)
                        .requestMatchers(HttpMethod.POST, "/api/customers/register").permitAll() // Register
                        .requestMatchers(HttpMethod.POST, "/api/customers/login").permitAll() // Login
                        .requestMatchers(HttpMethod.PUT, "/api/customers/*").permitAll() // Edit Customer Info
                        .requestMatchers(HttpMethod.GET, "/api/customers/**").permitAll() // Get customer by ID

                        // Bill Endpoints (View and Calculate)
                        .requestMatchers(HttpMethod.POST, "/api/bills/calculate").permitAll() // Calculate a new bill
                        .requestMatchers(HttpMethod.GET, "/api/bills").permitAll() // Get All Bills
                        .requestMatchers(HttpMethod.GET, "/api/bills/**").permitAll() // Get Bill by ID, by customer, by status

                        // Book Endpoints (from your previous requirements)
                        .requestMatchers(HttpMethod.POST, "/api/books").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/books").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/books/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/books/*").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/books/*").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/books/*/decreaseStock/*").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/books/*/increaseStock/*").permitAll()

                        // Payment Endpoints (from your previous requirements)
                        .requestMatchers(HttpMethod.POST, "/api/payments").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/payments").permitAll()
                        .requestMatchers(HttpMethod.GET, "/api/payments/**").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/payments/*").permitAll()
                        .requestMatchers(HttpMethod.DELETE, "/api/payments/*").permitAll()

                        // Allow access to the default Spring Boot error page
                        .requestMatchers("/error").permitAll()

                        // --- Admin-only Endpoints ---
                        // All endpoints under /api/admin are for administrators only.
                        .requestMatchers("/api/admin/**").permitAll()

                        // --- Secured Endpoints (Require authentication) ---
                        // Any other request not specified above will require the user to be authenticated
                        .anyRequest().authenticated()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
