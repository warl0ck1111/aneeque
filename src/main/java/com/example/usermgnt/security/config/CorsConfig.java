package com.example.usermgnt.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Okala III
 */

@Configuration
@Component
public class CorsConfig implements WebMvcConfigurer {
//    @Value("${usermgnt.trusted-origins}")
//    private String[] whitelist = {"*"};

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        System.out.println("WebMvcConfigurer called");
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders("*")
                .allowCredentials(false).maxAge(3600);
    }
}
