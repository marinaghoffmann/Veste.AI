package com.vesteai.backend.infrastructure.config;

import com.vesteai.backend.domain.event.LookEventListener;
import com.vesteai.backend.domain.event.LookEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DomainEventsConfig {

    @Bean
    public LookEventPublisher lookEventPublisher(List<LookEventListener> listeners) {
        LookEventPublisher publisher = new LookEventPublisher();
        listeners.forEach(publisher::registrar);
        return publisher;
    }
}
