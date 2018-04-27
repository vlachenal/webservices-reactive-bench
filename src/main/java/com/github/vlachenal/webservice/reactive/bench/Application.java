/*
 * Copyright © 2017 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservice.reactive.bench;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.reactive.config.EnableWebFlux;

/**
 * Application entry point
 *
 * @author Vincent Lachenal
 */
@Configuration
@EnableWebFlux
@EnableAutoConfiguration
@ComponentScan
@SpringBootApplication
@PropertySource({"classpath:application.properties","classpath:hardware.properties"})
public class Application {

  // Methods +
  /**
   * Application entry point
   *
   * @param args arguments ... not used
   */
  public static void main(final String[] args) {
    SpringApplication.run(Application.class, args);
  }
  // Methods -

}
