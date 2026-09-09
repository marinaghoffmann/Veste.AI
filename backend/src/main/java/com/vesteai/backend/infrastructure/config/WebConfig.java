package com.vesteai.backend.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final String pecasDir;

    public WebConfig(@Value("${app.storage.pecas-dir:./uploads/pecas}") String pecasDir) {
        this.pecasDir = pecasDir;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String location = Path.of(pecasDir).toUri().toString();
        registry.addResourceHandler("/uploads/pecas/**").addResourceLocations(location);
    }
}
