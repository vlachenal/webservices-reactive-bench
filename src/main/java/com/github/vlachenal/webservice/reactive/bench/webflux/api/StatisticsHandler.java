/*
 * Copyright © 2017 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservice.reactive.bench.webflux.api;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.github.vlachenal.webservice.reactive.bench.dao.StatisticsDAO;
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
    final String id = dao.save(request.bodyToMono(TestSuite.class).blockOptional().map(mapstruct.suite()::fromRest).get());
    URI uri = null;
    try {
      uri = new URI("webflux/customer/" + id);
    } catch(final URISyntaxException e) {
      return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BodyInserters.fromObject("Can not convert URI ..."));
    }
    return ServerResponse.created(uri).body(BodyInserters.fromObject(id));
  }

  /**
   * Add calls to test suite
   *
   * @param request the request
   *
   * @return the response
   */
  public Mono<ServerResponse> addCalls(final ServerRequest request) {
    final String statId = request.pathVariable("id");
    UUID uuid = null;
    try {
      uuid = UUID.fromString(request.pathVariable("id"));
    } catch(final IllegalArgumentException e) {
      return ServerResponse.badRequest().body(BodyInserters.fromObject("Invalid UUID: " + statId));
    }
    dao.registerCalls(uuid, request.bodyToFlux(ClientCall.class).map(mapstruct.call()::fromRest));
    return ServerResponse.ok().build();
  }
  // Methods -

}
