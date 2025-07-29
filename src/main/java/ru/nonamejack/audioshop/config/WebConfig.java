package ru.nonamejack.audioshop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.Duration;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.images.path}")
    private String imagesPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("file:" + imagesPath + "/")
                .setCacheControl(CacheControl.maxAge(Duration.ofDays(365)));
    }
}
