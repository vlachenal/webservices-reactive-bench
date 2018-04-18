/*
 * Copyright © 2017 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservice.reactive.bench.webflux.api;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.vlachenal.webservice.reactive.bench.dao.CustomerDAO;
import com.github.vlachenal.webservice.reactive.bench.dto.CustomerDTO;
import com.github.vlachenal.webservice.reactive.bench.mapping.mapstruct.MapStructMappers;
import com.github.vlachenal.webservice.reactive.bench.rest.api.dto.Address;
import com.github.vlachenal.webservice.reactive.bench.rest.api.dto.Customer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * Webflux customer service handler
 *
 * @author Vincent Lachenal
 */
@Component
public class CustomerHandler {

  // Attributes +
  /** Customer DAO */
  private final CustomerDAO dao;

  /** MapStruct mappers */
  private final MapStructMappers mapstruct;
  // Attributes -


  // Constructors +
  /**
   * {@link CustomerHandler} constructor
   *
   * @param dao the customer DAO to use
   * @param mapstruct the MapStruct mappers to use
   */
  public CustomerHandler(final CustomerDAO dao, final MapStructMappers mapstruct) {
    this.dao = dao;
    this.mapstruct = mapstruct;
  }
  // Constructors -


  // Methods +
  /**
   * Get customer
   *
   * @param request the request
   *
   * @return the response
   */
  public Mono<ServerResponse> get(final ServerRequest request) {
    final String custId = request.pathVariable("id");
    UUID uuid = null;
    try {
      uuid = UUID.fromString(request.pathVariable("id"));
    } catch(final IllegalArgumentException e) {
      return ServerResponse.badRequest().body(BodyInserters.fromObject("Invalid UUID: " + custId));
    }
    final Optional<CustomerDTO> cust = dao.getDetails(uuid);
    if(!cust.isPresent()) {
      return ServerResponse.notFound().build();
    }
    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromObject(mapstruct.customer().toRest(cust.get())));
  }

  /**
   * Create customer
   *
   * @param request the request
   *
   * @return the response
   */
  public Mono<ServerResponse> create(final ServerRequest request) {
    final Optional<Customer> req = request.bodyToMono(Customer.class).blockOptional(Duration.of(1, ChronoUnit.SECONDS));
    if(!req.isPresent()) {
      return ServerResponse.badRequest().body(BodyInserters.fromObject("No body"));
    }
    final Customer customer = req.get();
    // Customer structure checks +
    if(customer.getFirstName() == null || customer.getLastName() == null || customer.getBirthDate() == null) {
      String input = null;
      final ObjectMapper jsonMapper = new ObjectMapper();
      try {
        input = new String(jsonMapper.writeValueAsBytes(customer));
      } catch(final Exception e) {
        // Nothing to do
      }
      return ServerResponse.badRequest().body(BodyInserters.fromObject("Customer first_name, last_name and brith_date has to be set: " + input));
    }
    // Customer structure checks -
    // Address structure checks +
    final Address addr = customer.getAddress();
    if(addr != null
        && (addr.getLines() == null || addr.getLines().isEmpty()
        || addr.getZipCode() == null || addr.getCity() == null || addr.getCountry() == null)) {
      String input = null;
      final ObjectMapper jsonMapper = new ObjectMapper();
      try {
        input = new String(jsonMapper.writeValueAsBytes(customer));
      } catch(final Exception e) {
        // Nothing to do
      }
      return ServerResponse.badRequest().body(BodyInserters.fromObject("Address lines[0], zip_code, city and country has to be set: " + input));
    }
    // Address structure checks -
    final String uuid = dao.create(mapstruct.customer().fromRest(customer));
    URI uri = null;
    try {
      uri = new URI("webflux/customer/" + uuid);
    } catch(final URISyntaxException e) {
      return ServerResponse.status(HttpStatus.INTERNAL_SERVER_ERROR).body(BodyInserters.fromObject("Can not convert URI ..."));
    }
    return ServerResponse.created(uri).body(BodyInserters.fromObject(uuid));
  }

  /**
   * List customers
   *
   * @param request the request
   *
   * @return the response
   */
  public Mono<ServerResponse> list(final ServerRequest request) {
    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
        .body(Flux.fromStream(dao.stream().map(mapstruct.customer()::toRest)), Customer.class);
  }

  /**
   * Delete all customers
   *
   * @param request the request
   *
   * @return the response
   */
  public Mono<ServerResponse> delete(final ServerRequest request) {
    dao.deleteAll();
    return ServerResponse.ok().build();
  }
  // Methods -

}
