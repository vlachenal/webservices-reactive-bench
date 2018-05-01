/*
 * Copyright © 2017 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservice.reactive.bench.mapping.manual;

import com.github.vlachenal.webservice.reactive.bench.dto.CustomerDTO;
import com.github.vlachenal.webservice.reactive.bench.rest.api.dto.Customer;


/**
 * Customer conversion utility class
 *
 * @author Vincent Lachenal
 */
public final class CustomerBridge {

  // Constructors +
  /**
   * {@link CustomerBridge} private construtor
   */
  private CustomerBridge() {
    // Nothing to do
  }
  // Constructors -


  // Methods +
  /**
   * Convert customer DTO to REST/JSON structure
   *
   * @param dto the DTO to convert
   *
   * @return the JSON structure
   */
  public static Customer toRest(final CustomerDTO dto) {
    Customer customer = null;
    if(dto != null) {
      customer = new Customer();
      customer.setId(dto.getId());
      customer.setFirstName(dto.getFirstName());
      customer.setLastName(dto.getLastName());
      customer.setEmail(dto.getEmail());
      customer.setBirthDate(dto.getBirthDate());
      customer.setAddress(AddressBridge.toRest(dto.getAddress()));
      customer.setPhones(PhoneBridge.toRest(dto.getPhones()));
    }
    return customer;
  }

  /**
   * Convert customer REST/JSON structure to DTO
   *
   * @param customer the REST/JSON structure to convert
   *
   * @return the DTO
   */
  public static CustomerDTO fromRest(final Customer customer) {
    CustomerDTO dto = null;
    if(customer != null) {
      dto = new CustomerDTO();
      dto.setId(customer.getId());
      dto.setFirstName(customer.getFirstName());
      dto.setLastName(customer.getLastName());
      dto.setEmail(customer.getEmail());
      dto.setBirthDate(customer.getBirthDate());
      dto.setAddress(AddressBridge.fromRest(customer.getAddress()));
      dto.setPhones(PhoneBridge.fromRest(customer.getPhones()));
    }
    return dto;
  }
  // Methods -

}
