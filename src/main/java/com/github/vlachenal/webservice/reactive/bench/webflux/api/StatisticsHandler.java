/*
 * Copyright © 2017 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservice.reactive.bench.webflux.api;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.github.vlachenal.webservice.reactive.bench.business.StatisticsBusiness;
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
  // Attributes -


  // Constructors +
  /**
   * {@link StatisticsHandler} constructor
   *
   * @param business the customer business to use
   * @param mapstruct the MapStruct mappers to use
   */
  public StatisticsHandler(final StatisticsBusiness business, final MapStructMappers mapstruct) {
    this.business = business;
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
    Mono<ServerResponse> res = null;
    try {
      final String id = business.consolidate(request.bodyToMono(TestSuite.class).blockOptional()
                                             .map(mapstruct.suite()::fromRest).get());
      final URI uri = new URI("webflux/customer/" + id);
      res = ServerResponse.created(uri).body(BodyInserters.fromObject(id));
    } catch(final InvalidParametersException e) {
      res = ServerResponse.badRequest().body(BodyInserters.fromObject(e.getMessage()));
    } catch(final URISyntaxException e) {
      res = ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BodyInserters.fromObject("Can not convert URI ..."));
    }
    return res;
  }

  /**
   * Add calls to test suite
   *
   * @param req the request
   *
   * @return the response
   */
  public Mono<ServerResponse> addCalls(final ServerRequest req) {
    Mono<ServerResponse> res = null;
    try {
      business.registerCalls(req.pathVariable("id"), req.bodyToFlux(ClientCall.class).map(mapstruct.call()::fromRest));
      res = ServerResponse.ok().build();
    } catch(final InvalidParametersException e) {
      res = ServerResponse.badRequest().body(BodyInserters.fromObject(e.getMessage()));
    }
    return res;
  }
  // Methods -

}
