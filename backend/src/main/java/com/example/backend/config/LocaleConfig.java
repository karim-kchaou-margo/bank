package com.example.backend.config;

import java.util.TimeZone;
import javax.annotation.PostConstruct;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LocaleConfig {

  @PostConstruct
  public void setDefaultTimeZone() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
  }
}
