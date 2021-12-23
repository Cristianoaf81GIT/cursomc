package com.cristianoaf81.cursomc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import com.cristianoaf81.cursomc.services.DBService;

@Configuration
@Profile("test")
public class TestConfig {

  @Autowired
  private DBService dbService;

  @Bean
  public boolean instatiateDatabase() {
    try {
      dbService.instantiateTestDatabase();
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
