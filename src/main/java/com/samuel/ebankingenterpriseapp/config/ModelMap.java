package com.samuel.ebankingenterpriseapp.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMap {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
