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

                        // Bill Endpoints
                        .requestMatchers(HttpMethod.POST, "/api/bills").permitAll() // Create Bill
                        .requestMatchers(HttpMethod.GET, "/api/bills").permitAll() // Get All Bills
                        .requestMatchers(HttpMethod.GET, "/api/bills/**").permitAll() // Get Bill by ID, by customer, by status

                        // Book Endpoints
                        .requestMatchers(HttpMethod.POST, "/api/books").permitAll() // Create Book
                        .requestMatchers(HttpMethod.GET, "/api/books").permitAll() // Get All Books
                        .requestMatchers(HttpMethod.GET, "/api/books/**").permitAll() // Get Book by ID, ISBN, Author, Genre
                        .requestMatchers(HttpMethod.PUT, "/api/books/*").permitAll() // Allow general PUT /api/books/{id} for updates

                        // NEWLY ADDED: Allow DELETE requests to delete a book by ID
                        .requestMatchers(HttpMethod.DELETE, "/api/books/*").permitAll() // Allow DELETE /api/books/{id}

                        .requestMatchers(HttpMethod.PUT, "/api/books/*/decreaseStock/*").permitAll() // Allow stock decrease
                        .requestMatchers(HttpMethod.PUT, "/api/books/*/increaseStock/*").permitAll() // Allow stock increase

                        // Allow access to the default Spring Boot error page
                        .requestMatchers("/error").permitAll()

                        // --- Secured Endpoints (Require authentication) ---
                        // For all other requests that haven't been explicitly permitted above, require authentication.
                        .anyRequest().authenticated()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
