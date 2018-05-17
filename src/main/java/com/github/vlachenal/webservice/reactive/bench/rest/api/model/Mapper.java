/*
 * Copyright © 2017 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservice.reactive.bench.rest.api.model;


/**
 * Mapper
 *
 * @author Vincent Lachenal
 */
public enum Mapper {

  /** Manual mapping (user *Bridge classes) */
  MANUAL,

  /** Dozer mapper */
  DOZER,

  /** MapStruct mapper */
  MAPSTRUCT

}
