/*
 * Copyright © 2017 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservice.reactive.bench.mapping.dozer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;


/**
 * Dozer configuration
 *
 * @author Vincent Lachenal
 */
@Configuration
public class DozerConfig {

  // Methods +
  /**
   * Configure Dozer mapper
   *
   * @return the mapper
   */
  @Bean
  public Mapper dozer() {
    return DozerBeanMapperBuilder.create().build();
  }
  // Methods -

}
