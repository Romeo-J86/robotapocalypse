package com.robotapocalypse.robotapocalypse.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @author Romeo Jerenyama
 * @created 15/12/2022 - 16:46
 */
@Configuration
public class BeanConfigs {
    @Bean
    public ModelMapper modelMapper() {return new ModelMapper();}
    @Bean
    public RestTemplate restTemplate(){return new RestTemplate();}
}
