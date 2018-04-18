/*
 * Copyright © 2017 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservice.reactive.bench.mapping.mapstruct;

import org.mapstruct.Mapper;

import com.github.vlachenal.webservice.reactive.bench.dto.AddressDTO;
import com.github.vlachenal.webservice.reactive.bench.rest.api.dto.Address;


/**
 * Address mapper
 *
 * @author Vincent Lachenal
 */
@Mapper(
        componentModel = "spring"
    )
public interface AddressMapper {

  /**
   * Convert REST address to DTO
   *
   * @param address the REST address
   *
   * @return the DTO
   */
  AddressDTO fromRest(Address address);

  /**
   * Convert DTO address to REST
   *
   * @param address the DTO address
   *
   * @return the REST address
   */
  Address toRest(AddressDTO address);

}
