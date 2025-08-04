// File: src/main/java/com/pahanaedu/billingsystem/config/WebConfig.java
package com.pahanaedu.billingsystem.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configures CORS to allow all origins and all HTTP methods.
     * This is suitable for development environments to prevent "Failed to fetch" errors.
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Apply CORS to all endpoints
                .allowedOrigins("*") // Allow requests from any origin
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allow all common HTTP methods
                .allowedHeaders("*"); // Allow all headers
    }
}
