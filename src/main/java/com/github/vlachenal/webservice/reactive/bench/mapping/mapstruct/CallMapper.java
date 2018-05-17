/*
 * Copyright © 2017 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservice.reactive.bench.mapping.mapstruct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.github.vlachenal.webservice.reactive.bench.dto.CallDTO;
import com.github.vlachenal.webservice.reactive.bench.rest.api.model.ClientCall;


/**
 * Call mapper
 *
 * @author Vincent Lachenal
 */
@Mapper(
        componentModel = "spring"
    )
public interface CallMapper {

  /**
   * Convert REST client call to DTO
   *
   * @param call the REST client call
   *
   * @return the DTO
   */
  @Mappings(value= {
    @Mapping(target = "seq", source = "requestSeq"),
    @Mapping(target = "serverEnd", ignore = true),
    @Mapping(target = "serverStart", ignore = true)
  })
  CallDTO fromRest(ClientCall call);

}
