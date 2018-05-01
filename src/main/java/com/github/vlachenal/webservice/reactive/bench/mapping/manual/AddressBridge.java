/*
 * Copyright © 2017 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservice.reactive.bench.mapping.manual;

import com.github.vlachenal.webservice.reactive.bench.dto.AddressDTO;
import com.github.vlachenal.webservice.reactive.bench.rest.api.dto.Address;


/**
 * Address conversion utility class
 *
 * @author Vincent Lachenal
 */
public final class AddressBridge {

  // Constructors +
  /**
   * {@link AddressBridge} private construtor
   */
  private AddressBridge() {
    // Nothing to do
  }
  // Constructors -


  // Methods +
  /**
   * Convert addres DTO to REST/JSON structure
   *
   * @param dto the DTO to convert
   *
   * @return the JSON structure
   */
  public static Address toRest(final AddressDTO dto) {
    Address address = null;
    if(dto != null) {
      address = new Address();
      address.setLines(dto.getLines());
      address.setZipCode(dto.getZipCode());
      address.setCity(dto.getCity());
      address.setCountry(dto.getCountry());
    }
    return address;
  }

  /**
   * Convert REST address to DTI
   *
   * @param address the REST address
   *
   * @return the DTI
   */
  public static AddressDTO fromRest(final Address address) {
    AddressDTO dto = null;
    if(address != null) {
      dto = new AddressDTO();
      dto.setLines(address.getLines());
      dto.setZipCode(address.getZipCode());
      dto.setCity(address.getCity());
      dto.setCountry(address.getCountry());
    }
    return dto;
  }
  // Methods -

}
