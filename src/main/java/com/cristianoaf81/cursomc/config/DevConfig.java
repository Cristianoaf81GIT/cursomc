package com.cristianoaf81.cursomc.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import com.cristianoaf81.cursomc.services.DBService;
import com.cristianoaf81.cursomc.services.EmailService;
import com.cristianoaf81.cursomc.services.SmtpEmailService;

@Configuration
@Profile("dev")
public class DevConfig {

  // @Autowired
  // private DBService dbService;

  @Autowired
  public DevConfig(DBService dbService) {
    this.dbService = dbService;
  }

  @Value("${spring.jpa.hibernate.ddl-auto}")
  private String strategy;
  private DBService dbService;

  @Bean
  public boolean instatiateDatabase() {
    if (!"create".equals(strategy))
      return false;
    try {
      this.dbService.instantiateTestDatabase();
      return true;
    } catch (Exception e) {
      return false;
    }
  }

  @Bean
  public EmailService emailService() {
    return new SmtpEmailService();
  }
}
