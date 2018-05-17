/*
 * Copyright © 2017 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservice.reactive.bench.mapping.mapstruct;

import org.mapstruct.Mapper;

import com.github.vlachenal.webservice.reactive.bench.dto.PhoneDTO;
import com.github.vlachenal.webservice.reactive.bench.rest.api.model.Phone;


/**
 * Phone mapper
 *
 * @author Vincent Lachenal
 */
@Mapper(
        componentModel = "spring"
    )
public interface PhoneMapper {

  /**
   * Convert REST phone to DTO
   *
   * @param phone the REST phone
   *
   * @return the DTO
   */
  PhoneDTO fromRest(Phone phone);

  /**
   * Convert DTO phone to REST
   *
   * @param phone the DTO phone
   *
   * @return the REST phone
   */
  Phone toRest(PhoneDTO phone);

}
