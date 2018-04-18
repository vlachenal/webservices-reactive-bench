/*
 * Copyright © 2017 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservice.reactive.bench.rest.api.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * Address
 *
 * @author Vincent Lachenal
 */
@ApiModel(description="Customer's address")
public class Address {

  // Attributes +
  /** Address lines */
  @ApiModelProperty(notes="Address lines",required=true)
  @JsonProperty(value="lines",required=true)
  private List<String> lines;

  /** Address ZIP code */
  @ApiModelProperty(notes="ZIP code",required=true)
  @JsonProperty(value="zip_code",required=true)
  private String zipCode;

  /** City */
  @ApiModelProperty(notes="City",required=true)
  @JsonProperty(value="city",required=true)
  private String city;

  /** Country */
  @ApiModelProperty(notes="Country",required=true)
  @JsonProperty(value="country",required=true)
  private String country;
  // Attributes -


  // Accessors +
  /**
   * Address lines getter
   *
   * @return the lines
   */
  public List<String> getLines() {
    return lines;
  }

  /**
   * Address lines setter
   *
   * @param lines the lines to set
   */
  public void setLines(final List<String> lines) {
    this.lines = lines;
  }

  /**
   * ZIP code getter
   *
   * @return the ZIP code
   */
  public String getZipCode() {
    return zipCode;
  }

  /**
   * ZIP code setter
   *
   * @param zipCode the ZIP code to set
   */
  public void setZipCode(final String zipCode) {
    this.zipCode = zipCode;
  }

  /**
   * City getter
   *
   * @return the city
   */
  public String getCity() {
    return city;
  }

  /**
   * City setter
   *
   * @param city the city to set
   */
  public void setCity(final String city) {
    this.city = city;
  }

  /**
   * Country getter
   *
   * @return the country
   */
  public String getCountry() {
    return country;
  }

  /**
   * Country setter
   *
   * @param country the country to set
   */
  public void setCountry(final String country) {
    this.country = country;
  }
  // Accessors -

}
