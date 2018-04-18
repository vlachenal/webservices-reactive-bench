/*
 * Copyright © 2017 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservice.reactive.bench.webflux.api;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.github.vlachenal.webservice.reactive.bench.dao.StatisticsDAO;
import com.github.vlachenal.webservice.reactive.bench.mapping.mapstruct.MapStructMappers;
import com.github.vlachenal.webservice.reactive.bench.rest.api.dto.TestSuite;

import reactor.core.publisher.Mono;


/**
 * Webflux statistics service handler
 *
 * @author Vincent Lachenal
 */
@Component
public class StatisticsHandler {

  // Attributes +
  /** Customer DAO */
  private final StatisticsDAO dao;

  /** MapStruct mappers */
  private final MapStructMappers mapstruct;
  // Attributes -


  // Constructors +
  /**
   * {@link StatisticsHandler} constructor
   *
   * @param dao the customer DAO to use
   * @param mapstruct the MapStruct mappers to use
   */
  public StatisticsHandler(final StatisticsDAO dao, final MapStructMappers mapstruct) {
    this.dao = dao;
    this.mapstruct = mapstruct;
  }
  // Constructors -


  // Methods +
  /**
   * Create a new test suite
   *
   * @param request the request
   *
   * @return the response
   */
  public Mono<ServerResponse> create(final ServerRequest request) {
    dao.save(request.bodyToMono(TestSuite.class).blockOptional().map(mapstruct.suite()::fromRest).get());
    return ServerResponse.ok().build(); // TODO created with path in header ...
  }

  /**
   * Add calls to test suite
   *
   * @param request the request
   *
   * @return the response
   */
  public Mono<ServerResponse> addCalls(final ServerRequest request) {
    dao.save(null);
    mapstruct.address();
    return null;
  }
  // Methods -

}
