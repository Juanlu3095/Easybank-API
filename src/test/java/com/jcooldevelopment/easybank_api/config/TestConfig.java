package com.jcooldevelopment.easybank_api.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestConfig {
    
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        
        // Need this to use LocalDateTime in Jackson library for testing
        // https://howtodoinjava.com/jackson/java-8-date-time-type-not-supported-by-default/
        mapper.registerModule(new JavaTimeModule());
        return mapper;
    }
}
