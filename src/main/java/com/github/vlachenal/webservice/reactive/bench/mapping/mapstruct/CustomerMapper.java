/*
 * Copyright © 2017 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservice.reactive.bench.mapping.mapstruct;

import org.mapstruct.Mapper;

import com.github.vlachenal.webservice.reactive.bench.dto.CustomerDTO;
import com.github.vlachenal.webservice.reactive.bench.rest.api.dto.Customer;


/**
 * Customer mapper
 *
 * @author Vincent Lachenal
 */
@Mapper(
        componentModel = "spring",
        uses = {
          PhoneMapper.class,
          AddressMapper.class
        }
    )
public interface CustomerMapper {

  /**
   * Convert REST customer to DTO
   *
   * @param customer the REST customer
   *
   * @return the DTO
   */
  CustomerDTO fromRest(Customer customer);

  /**
   * Convert DTO customer to REST
   *
   * @param customer the DTO customer
   *
   * @return the REST customer
   */
  Customer toRest(CustomerDTO customer);

}
