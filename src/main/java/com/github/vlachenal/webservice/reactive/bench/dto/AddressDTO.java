/*
 * Copyright © 2017 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservice.reactive.bench.dto;

import java.util.ArrayList;
import java.util.List;


/**
 * Address DTO
 *
 * @author Vincent Lachenal
 */
public class AddressDTO {

  // Attributes +
  /** Address lines */
  private List<String> lines;

  /** Address ZIP code */
  private String zipCode;

  /** City */
  private String city;

  /** Country */
  private String country;
  // Attributes -


  // Constructors +
  /**
   * {@link AddressDTO} default constructor
   */
  public AddressDTO() {
    // Nothing to do
  }

  /**
   * {@link AddressDTO} constructor
   *
   * @param zipCode the ZIP code
   * @param city the city
   * @param country the country
   * @param lines the address' lines
   */
  public AddressDTO(final String zipCode, final String city, final String country, final String... lines) {
    this.zipCode = zipCode;
    this.city = city;
    this.country = country;
    this.lines = new ArrayList<>();
    for(final String line : lines) {
      if(line == null || line.isEmpty()) {
        break;
      }
      this.lines.add(line);
    }
  }
  // Constructors -


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
