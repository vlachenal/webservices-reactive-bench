/*
 * Copyright © 2017 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservice.reactive.bench.mapping.manual;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.github.vlachenal.webservice.reactive.bench.dto.PhoneDTO;
import com.github.vlachenal.webservice.reactive.bench.rest.api.model.Phone;


/**
 * Phone conversion utility class
 *
 * @author Vincent Lachenal
 */
public final class PhoneBridge {

  // Constructors +
  /**
   * {@link PhoneBridge} private construtor
   */
  private PhoneBridge() {
    // Nothing to do
  }
  // Constructors -


  // Methods +
  /**
   * Convert phone DTO to REST/JSON structure
   *
   * @param dto the DTO to convert
   *
   * @return the JSON structure
   */
  public static Phone toRest(final PhoneDTO dto) {
    Phone phone = null;
    if(dto != null) {
      phone = new Phone();
      phone.setNumber(dto.getNumber());
      if(dto.getType() != null) {
        switch(dto.getType()) {
          case LANDLINE:
            phone.setType(Phone.Type.LANDLINE);
            break;
          case MOBILE:
            phone.setType(Phone.Type.MOBILE);
            break;
          default:
            // Nothing to do
        }
      }
    }
    return phone;
  }

  /**
   * Convert phone DTOs to REST/JSON structures
   *
   * @param dto the DTOs to convert
   *
   * @return the JSON structures
   */
  public static List<Phone> toRest(final List<PhoneDTO> dto) {
    return Optional.ofNullable(dto).map(l -> l.stream().map(phone -> toRest(phone)).collect(Collectors.toList())).orElse(null);
  }

  /**
   * Convert REST phone to DTO
   *
   * @param phone the REST phone
   *
   * @return the DTO
   */
  public static PhoneDTO fromRest(final Phone phone) {
    PhoneDTO dto = null;
    if(phone != null) {
      dto = new PhoneDTO();
      dto.setNumber(phone.getNumber());
      switch(phone.getType()) {
        case LANDLINE:
          dto.setType(PhoneDTO.Type.LANDLINE);
          break;
        case MOBILE:
          dto.setType(PhoneDTO.Type.MOBILE);
          break;
        default:
          // Nothing to do
      }
    }
    return dto;
  }

  /**
   * Convert REST phone list to DTO list
   *
   * @param phones the REST phone list
   *
   * @return the DTO list
   */
  public static List<PhoneDTO> fromRest(final List<Phone> phones) {
    return Optional.ofNullable(phones).map(l -> l.stream().map(phone -> fromRest(phone)).collect(Collectors.toList())).orElse(null);
  }
  // Methods -

}
