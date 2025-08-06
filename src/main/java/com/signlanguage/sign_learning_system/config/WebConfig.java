package com.signlanguage.sign_learning_system.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Value("${upload.path}")
    private String uploadPath;

    @Bean
    public WebMvcConfigurer corsAndResourceConfigurer() {
        return new WebMvcConfigurer() {

            // Ruhusu maombi kutoka kwa React Web na React Native (Expo Go)
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOriginPatterns(
                                "http://localhost:*",
                                "http://192.168.43.33:*"
                        )
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }

            // Serve video & image files from uploads folder
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/uploads/**")
                        .addResourceLocations("file:" + uploadPath.replace("\\", "/") + "/")
                        .setCachePeriod(0); // optional: disables caching during development
            }
        };
    }
}
