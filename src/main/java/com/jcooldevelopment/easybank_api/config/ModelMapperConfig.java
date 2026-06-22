package com.jcooldevelopment.easybank_api.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    /*
    * It allows to use always the same instance of ModelMapper.
    */
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
