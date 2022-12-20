package com.robotapocalypse.robotapocalypse.configs;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @author Romeo Jerenyama
 * @created 15/12/2022 - 16:46
 */
@Configuration
public class BeanConfigs {
    @Bean
    public ModelMapper modelMapper() {return new ModelMapper();}
    @Bean
    public WebClient webClient(){ return WebClient.create();}
}
