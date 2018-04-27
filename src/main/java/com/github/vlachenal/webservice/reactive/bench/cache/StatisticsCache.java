/*
 * Copyright © 2017 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservice.reactive.bench.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.springframework.stereotype.Component;

import com.github.vlachenal.webservice.reactive.bench.dto.CallDTO;


/**
 * Statistics cache
 *
 * @author Vincent Lachenal
 */
@Component
public class StatisticsCache {

  // Attributes +
  /** Calls statistics */
  private final Map<String,CallDTO> calls;
  // Attributes -


  // Constructors +
  /**
   * {@link StatisticsCache} default constructor
   */
  public StatisticsCache() {
    calls = new HashMap<>();
  }
  // Constructors -


  // Methods +
  /**
   * Register call into statistics call cache
   *
   * @param call the call to register
   */
  public synchronized void register(final CallDTO call) {
    calls.put(call.getKey(), call);
  }

  /**
   * Merge call
   *
   * @param call the client call statistic
   *
   * @return the complete call if found, {@code null} otherwise
   */
  public void mergeCall(final CallDTO call) {
    Optional.ofNullable(calls.get(call.getKey())).ifPresent(regCall -> {
      call.setServerStart(call.getServerStart());
      call.setServerEnd(call.getServerEnd());
    });
  }

  /**
   * Clean cache
   */
  public synchronized void clean() {
    calls.clear();
  }
  // Methods -

}
