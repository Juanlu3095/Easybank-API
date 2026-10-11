package com.jcooldevelopment.easybank_api.config;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.javafaker.Faker;

@Configuration 
public class JavaFakerConfig {

    @Bean 
    public Faker generateFakerObject() {
        // Since Java 19 constructors are deprecated: 
        // https://docs.oracle.com/en/java/javase/21/docs/api/java.base/java/util/Locale.html
        // https://github.com/DiUS/java-faker
        // https://tahaburak.medium.com/spring-boot-002-a-simple-model-with-faker-f69ee433596
        return new Faker(Locale.ENGLISH);
    }
}
