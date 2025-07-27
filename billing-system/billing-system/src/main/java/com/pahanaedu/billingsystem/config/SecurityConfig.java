// File: src/main/java/com/pahanaedu/billingsystem/config/SecurityConfig.java
package com.pahanaedu.billingsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod; // Make sure this import is here
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF (Cross-Site Request Forgery) protection for REST APIs.
                // This is generally safe for stateless APIs that don't rely on session cookies for authentication.
                .csrf(AbstractHttpConfigurer::disable)

                // Configure authorization rules for HTTP requests
                .authorizeHttpRequests(authorize -> authorize
                        // --- Public Endpoints (Accessible without authentication) ---

                        // Allow POST requests to /api/customers (for user registration)
                        .requestMatchers(HttpMethod.POST, "/api/customers").permitAll()
                        // Allow POST requests to /api/customers/login (for user login)
                        .requestMatchers(HttpMethod.POST, "/api/customers/login").permitAll()

                        // Allow POST requests to /api/bills (for creating bills)
                        .requestMatchers(HttpMethod.POST, "/api/bills").permitAll()
                        // Allow GET requests to /api/bills (for getting all bills)
                        .requestMatchers(HttpMethod.GET, "/api/bills").permitAll()
                        // Allow GET requests to /api/bills/{id} (for getting a single bill by ID)
                        .requestMatchers(HttpMethod.GET, "/api/bills/**").permitAll() // Covers /api/bills/{id}, /api/bills/customer/{customerId}, /api/bills/status/{status}
                        // Allow access to the default Spring Boot error page (for better error visibility)
                        .requestMatchers("/error").permitAll()

                        // --- Secured Endpoints (Require authentication) ---
                        // For all other requests that haven't been explicitly permitted above, require authentication.
                        // This means any other GET, PUT, DELETE, etc. requests to /api/customers
                        // or any other future endpoints will be protected.
                        .anyRequest().authenticated()
                );

        // Build and return the SecurityFilterChain
        return http.build();
    }

    // This bean provides a password encoder. It's crucial for securely storing
    // and comparing passwords (e.g., for login). BCryptPasswordEncoder is recommended.
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // Note: For a real application with authenticated users, you would typically
    // integrate a UserDetailsService to load user details from your database
    // and configure an AuthenticationManager. For now, we are relying on permitAll()
    // for the specified endpoints.
}
