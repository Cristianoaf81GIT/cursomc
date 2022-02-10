package com.cristianoaf81.cursomc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import com.cristianoaf81.cursomc.services.DBService;
import com.cristianoaf81.cursomc.services.EmailService;
import com.cristianoaf81.cursomc.services.MockEmailService;

@Configuration
@Profile("test")
public class TestConfig {

  // @Autowired
  // private DBService dbService;

  @Bean
  @Autowired
  public boolean instatiateDatabase(DBService dbService) {
    try {
      dbService.instantiateTestDatabase();
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  @Bean
  public EmailService emailService() {
    return new MockEmailService();
  }
}
