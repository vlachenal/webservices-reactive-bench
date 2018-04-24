/*
 * Copyright © 2017 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservice.reactive.bench.webflux;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.github.vlachenal.webservice.reactive.bench.webflux.api.CustomerHandler;
import com.github.vlachenal.webservice.reactive.bench.webflux.api.StatisticsHandler;


/**
 * RESTful webflux API router
 *
 * @author Vincent Lachenal
 */
@Configuration
@EnableWebFlux
public class WebfluxRouter implements WebFluxConfigurer {

  /**
   * Route requests to customer resource handler
   *
   * @param handler the handler
   *
   * @return the route configuration
   */
  @Bean
  public RouterFunction<ServerResponse> routeCustomer(final CustomerHandler handler) {
    return RouterFunctions.route(RequestPredicates.GET("/webflux/customer/{id}").and(RequestPredicates.accept(MediaType.APPLICATION_JSON_UTF8)), handler::get)
        .andNest(RequestPredicates.path("/webflux/customer"),
                 RouterFunctions.route(RequestPredicates.method(HttpMethod.GET).and(RequestPredicates.accept(MediaType.APPLICATION_JSON_UTF8)), handler::list)
                 .andRoute(RequestPredicates.method(HttpMethod.POST).and(RequestPredicates.contentType(MediaType.APPLICATION_JSON_UTF8)).and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), handler::create)
                 .andRoute(RequestPredicates.method(HttpMethod.DELETE), handler::delete)
            );
  }

  /**
   * Route requests to statistics resource handler
   *
   * @param handler the handler
   *
   * @return the route configuration
   */
  public RouterFunction<ServerResponse> routeStatistics(final StatisticsHandler handler) {
    return RouterFunctions.route(RequestPredicates.POST("/webflux/stats").and(RequestPredicates.contentType(MediaType.APPLICATION_JSON_UTF8)).and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), handler::create)
        .andRoute(RequestPredicates.POST("/webflux/stats/{id}/calls").and(RequestPredicates.contentType(MediaType.APPLICATION_JSON_UTF8)).and(RequestPredicates.accept(MediaType.TEXT_PLAIN)), handler::addCalls);
  }

}
