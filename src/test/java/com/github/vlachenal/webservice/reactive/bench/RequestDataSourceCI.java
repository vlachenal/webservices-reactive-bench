/*
 * Copyright © 2017 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservice.reactive.bench;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;


/**
 * Datasource configuration
 *
 * @author Vincent Lachenal
 */
@Configuration
@Profile("ci")
@PropertySource({"classpath:db-ci.properties"})
public class RequestDataSourceCI {

  // Methods +
  /**
   * Provide customer database datasource
   *
   * @return the datasource
   */
  @Bean(name="ds.customer")
  @Primary
  @ConfigurationProperties(prefix="ds.customer")
  public DataSource provideCustomerDataSource() {
    return DataSourceBuilder.create().build();
  }
  // Methods -

}
