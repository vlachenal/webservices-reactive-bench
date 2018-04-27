/*
 * Copyright © 2017 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservice.reactive.bench.webflux.api;

import java.util.List;
import java.util.UUID;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.github.vlachenal.webservice.reactive.bench.business.CustomerBusiness;
import com.github.vlachenal.webservice.reactive.bench.cache.StatisticsCache;
import com.github.vlachenal.webservice.reactive.bench.dto.CallDTO;
import com.github.vlachenal.webservice.reactive.bench.errors.InvalidParametersException;
import com.github.vlachenal.webservice.reactive.bench.errors.NotFoundException;
import com.github.vlachenal.webservice.reactive.bench.mapping.mapstruct.MapStructMappers;
import com.github.vlachenal.webservice.reactive.bench.rest.api.dto.Customer;

import reactor.core.publisher.Mono;


/**
 * Webflux customer service handler
 *
 * @author Vincent Lachenal
 */
@Component
public class CustomerHandler {

  // Attributes +
  /** Customer business */
  private final CustomerBusiness business;

  /** MapStruct mappers */
  private final MapStructMappers mapstruct;

  /** Statistics cache */
  private final StatisticsCache stats;
  // Attributes -


  // Constructors +
  /**
   * {@link CustomerHandler} constructor
   *
   * @param business the customer business to use
   * @param mapstruct the MapStruct mappers to use
   * @param stats the statistics cache to use
   */
  public CustomerHandler(final CustomerBusiness business, final MapStructMappers mapstruct, final StatisticsCache stats) {
    this.business = business;
    this.mapstruct = mapstruct;
    this.stats = stats;
  }
  // Constructors -


  // Methods +
  /**
   * Initialize call
   *
   * @param requestSeq the request sequence
   * @param method the method
   *
   * @return the call if requestSeq is not equal to {@code -1}, {@code null} otherwise
   */
  private CallDTO initializeCall(final List<String> requestSeq, final String method) {
    CallDTO call = null;
    int seq = -1;
    if(requestSeq != null && !requestSeq.isEmpty()) {
      try {
        seq = Integer.parseInt(requestSeq.get(0));
      } catch(final Exception e) {
        // Nothing to do
      }
    }
    if(seq != -1) {
      final long start = System.nanoTime();
      call = new CallDTO();
      call.setSeq(seq);
      call.setServerStart(start);
      call.setProtocol("webflux");
      call.setMethod(method);
    }
    return call;
  }

  /**
   * Register call if not null in cache
   *
   * @param call the call to register
   */
  private void registerCall(final CallDTO call) {
    if(call != null) {
      call.setServerEnd(System.nanoTime());
      stats.register(call);
    }
  }

  /**
   * Get customer
   *
   * @param req the request
   *
   * @return the response
   */
  public Mono<ServerResponse> get(final ServerRequest req) {
    final CallDTO call = initializeCall(req.headers().header("request_seq"), "get");
    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(BodyInserters.fromObject(mapstruct.customer().toRest(business.getDetails(req.pathVariable("id")))))
        .onErrorResume(InvalidParametersException.class, e -> ServerResponse.badRequest().body(BodyInserters.fromObject(e.getMessage())))
        .onErrorResume(NotFoundException.class, e -> ServerResponse.notFound().build())
        .doFinally(s -> registerCall(call));
  }

  /**
   * Create customer
   *
   * @param req the request
   *
   * @return the response
   */
  public Mono<ServerResponse> create(final ServerRequest req) {
    final CallDTO call = initializeCall(req.headers().header("request_seq"), "create");
    final UUID uuid = UUID.randomUUID();
    return ServerResponse.created(req.uriBuilder().path("/{id}").build(uuid.toString()))
        .body(BodyInserters.fromObject(business.create(req.bodyToMono(Customer.class).map(mapstruct.customer()::fromRest), uuid)))
        .onErrorResume(InvalidParametersException.class, e -> ServerResponse.badRequest().body(BodyInserters.fromObject(e.getMessage())))
        .doFinally(s -> registerCall(call));
  }

  /**
   * List customers
   *
   * @param req the request
   *
   * @return the response
   */
  public Mono<ServerResponse> list(final ServerRequest req) {
    final CallDTO call = initializeCall(req.headers().header("request_seq"), "get");
    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(business.listAll().map(mapstruct.customer()::toRest), Customer.class)
        .doFinally(s -> registerCall(call));
  }

  /**
   * Delete all customers
   *
   * @param req the request
   *
   * @return the response
   */
  public Mono<ServerResponse> delete(final ServerRequest req) {
    return ServerResponse.ok().build(t -> business.deleteAll());
  }
  // Methods -

}
