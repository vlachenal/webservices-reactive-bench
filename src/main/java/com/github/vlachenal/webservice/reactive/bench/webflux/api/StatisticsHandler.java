/*
 * Copyright © 2017 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservice.reactive.bench.webflux.api;

import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.github.vlachenal.webservice.reactive.bench.business.StatisticsBusiness;
import com.github.vlachenal.webservice.reactive.bench.cache.StatisticsCache;
import com.github.vlachenal.webservice.reactive.bench.errors.InvalidParametersException;
import com.github.vlachenal.webservice.reactive.bench.mapping.mapstruct.MapStructMappers;
import com.github.vlachenal.webservice.reactive.bench.rest.api.dto.ClientCall;
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
  /** Statistics business */
  private final StatisticsBusiness business;

  /** MapStruct mappers */
  private final MapStructMappers mapstruct;

  /** Statistics cache */
  private final StatisticsCache stats;
  // Attributes -


  // Constructors +
  /**
   * {@link StatisticsHandler} constructor
   *
   * @param business the customer business to use
   * @param mapstruct the MapStruct mappers to use
   * @param stats the statistics cache to use
   */
  public StatisticsHandler(final StatisticsBusiness business, final MapStructMappers mapstruct, final StatisticsCache stats) {
    this.business = business;
    this.mapstruct = mapstruct;
    this.stats = stats;
  }
  // Constructors -


  // Methods +
  /**
   * Create a new test suite
   *
   * @param req the request
   *
   * @return the response
   */
  public Mono<ServerResponse> create(final ServerRequest req) {
    final UUID uuid = UUID.randomUUID();
    return ServerResponse.created(req.uriBuilder().path("/{id}").build(uuid.toString()))
        .body(BodyInserters.fromPublisher(business.consolidate(req.bodyToMono(TestSuite.class).map(mapstruct.suite()::fromRest), uuid), String.class))
        .onErrorResume(InvalidParametersException.class, e -> ServerResponse.badRequest().body(BodyInserters.fromObject(e.getMessage())));
  }

  /**
   * Add calls to test suite
   *
   * @param req the request
   *
   * @return the response
   */
  public Mono<ServerResponse> addCalls(final ServerRequest req) {
    return ServerResponse.ok().build(business.registerCalls(req.pathVariable("id"), req.bodyToFlux(ClientCall.class).map(mapstruct.call()::fromRest)))
        .onErrorResume(InvalidParametersException.class, e -> ServerResponse.badRequest().body(BodyInserters.fromObject(e.getMessage())));
  }

  /**
   * Purge statistics cache
   *
   * @param req the request
   *
   * @return the response
   */
  public Mono<ServerResponse> purge(final ServerRequest req) {
    stats.clean();
    return ServerResponse.ok().build();
  }
  // Methods -

}
