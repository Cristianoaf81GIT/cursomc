package com.cristianoaf81.cursomc.config;

import com.cristianoaf81.cursomc.domain.PagamentoComBoleto;
import com.cristianoaf81.cursomc.domain.PagamentoComCartao;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

@Configuration
public class JacksonConfig {

  @Bean
  public Jackson2ObjectMapperBuilder objectMapperBuilder() {
    return new Jackson2ObjectMapperBuilder() {
      @Override
      public void configure(ObjectMapper objectMapper) {
        objectMapper.registerSubtypes(PagamentoComCartao.class);
        objectMapper.registerSubtypes(PagamentoComBoleto.class);
        super.configure(objectMapper);
      }
    };

  }
}
