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

                        // Customer Endpoints
                        .requestMatchers(HttpMethod.POST, "/api/customers").permitAll() // Register
                        .requestMatchers(HttpMethod.POST, "/api/customers/login").permitAll() // Login
                        // Note: Other /api/customers GET/PUT/DELETE will be protected by .anyRequest().authenticated()

                        // Bill Endpoints
                        .requestMatchers(HttpMethod.POST, "/api/bills").permitAll() // Create Bill
                        .requestMatchers(HttpMethod.GET, "/api/bills").permitAll() // Get All Bills
                        .requestMatchers(HttpMethod.GET, "/api/bills/**").permitAll() // Get Bill by ID, by customer, by status

                        // Product Endpoints (NEWLY ADDED)
                        .requestMatchers(HttpMethod.POST, "/api/products").permitAll() // Create Product
                        .requestMatchers(HttpMethod.GET, "/api/products").permitAll() // Get All Products
                        .requestMatchers(HttpMethod.GET, "/api/products/**").permitAll() // Get Product by ID or Name

                        // Allow access to the default Spring Boot error page
                        .requestMatchers("/error").permitAll()

                        // --- Secured Endpoints (Require authentication) ---
                        .anyRequest().authenticated()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
