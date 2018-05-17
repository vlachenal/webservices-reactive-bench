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

import com.github.vlachenal.webservice.reactive.bench.dto.TestSuiteDTO;
import com.github.vlachenal.webservice.reactive.bench.rest.api.model.TestSuite;


/**
 * Test suite mapper
 *
 * @author Vincent Lachenal
 */
@Mapper(
        componentModel = "spring",
        uses = {
          CallMapper.class
        }
    )
public interface TestSuiteMapper {

  /**
   * Convert REST test suite to DTO
   *
   * @param suite the REST test suite
   *
   * @return the DTO
   */
  @Mappings(value= {
    @Mapping(target = "clientCpu", source = "cpu"),
    @Mapping(target = "clientJvmVendor", source = "vendor"),
    @Mapping(target = "clientJvmVersion", source = "jvm"),
    @Mapping(target = "clientMemory", source = "memory"),
    @Mapping(target = "clientOsName", source = "osFamily"),
    @Mapping(target = "clientOsVersion", source = "osVersion"),
    @Mapping(target = "nbThreads", source = "nbThread"),
    @Mapping(target = "serverCpu", ignore = true),
    @Mapping(target = "serverJvmVendor", ignore = true),
    @Mapping(target = "serverJvmVersion", ignore = true),
    @Mapping(target = "serverMemory", ignore = true),
    @Mapping(target = "serverOsName", ignore = true),
    @Mapping(target = "serverOsVersion", ignore = true)
  })
  TestSuiteDTO fromRest(TestSuite suite);

  /**
   * Convert mapper enumeration to string
   *
   * @param mapper the mapper
   *
   * @return the string
   */
  default String fromRest(final com.github.vlachenal.webservice.reactive.bench.rest.api.model.Mapper mapper) {
    String str = "manual";
    switch(mapper) {
      case DOZER:
        str = "dozer";
        break;
      case MAPSTRUCT:
        str = "mapstruct";
        break;
      default:
        // Nothing to do => manual
    }
    return str;
  }

}
