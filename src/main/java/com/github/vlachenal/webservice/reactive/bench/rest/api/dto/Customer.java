/*
 * Copyright © 2017 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservice.reactive.bench.rest.api.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;


/**
 * Customer
 *
 * @author Vincent Lachenal
 */
@ApiModel(description="Customer's informations")
public class Customer {

  // Attributes +
  /** Customer identifier */
  @ApiModelProperty(notes="Customer's UUID")
  @JsonProperty(value="id",required=false)
  private String id;

  /** Customer first name */
  @ApiModelProperty(notes="Customer's first name",required=true)
  @JsonProperty(value="first_name",required=true)
  private String firstName;

  /* Customer last name */
  @ApiModelProperty(notes="Customer's last name",required=true)
  @JsonProperty(value="last_name",required=true)
  private String lastName;

  /** Customer brith date */
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  @JsonProperty(value="birth_date",required=true)
  @ApiModelProperty(notes="Customer's birth date",required=true)
  private Date birthDate;

  /** Customer address */
  @ApiModelProperty(notes="Customer's address")
  private Address address;

  /** Customer email address */
  @ApiModelProperty(notes="Customer's email address")
  @JsonProperty(value="email",required=false)
  private String email;

  /** Customer phone numbers */
  @ApiModelProperty(notes="Customer's phone numbers")
  private List<Phone> phones;
  // Attributes -


  // Accessors +
  /**
   * Identifier getter
   *
   * @return the identifier
   */
  public String getId() {
    return id;
  }

  /**
   * Identifier setter
   *
   * @param id the identifier to set
   */
  public void setId(final String id) {
    this.id = id;
  }

  /**
   * First name getter
   *
   * @return the first name
   */
  public String getFirstName() {
    return firstName;
  }

  /**
   * First name setter
   *
   * @param firstName the first name to set
   */
  public void setFirstName(final String firstName) {
    this.firstName = firstName;
  }

  /**
   * Last name getter
   *
   * @return the last name
   */
  public String getLastName() {
    return lastName;
  }

  /**
   * Last name setter
   *
   * @param lastName the last name to set
   */
  public void setLastName(final String lastName) {
    this.lastName = lastName;
  }

  /**
   * Birth date getter
   *
   * @return the birth date
   */
  public Date getBirthDate() {
    return birthDate;
  }

  /**
   * Birth date setter
   *
   * @param birthDate the birth date
   */
  public void setBirthDate(final Date birthDate) {
    this.birthDate = birthDate;
  }


  /**
   * Address getter
   *
   * @return the address
   */
  public Address getAddress() {
    return address;
  }

  /**
   * Address setter
   *
   * @param address the address to set
   */
  public void setAddress(final Address address) {
    this.address = address;
  }

  /**
   * Email address getter
   *
   * @return the email
   */
  public String getEmail() {
    return email;
  }

  /**
   * Email address setter
   *
   * @param email the email to set
   */
  public void setEmail(final String email) {
    this.email = email;
  }

  /**
   * Phone numbers getter
   *
   * @return the phone numbers
   */
  public List<Phone> getPhones() {
    return phones;
  }

  /**
   * Phone numbers setter
   *
   * @param phones the phone numbers to set
   */
  public void setPhones(final List<Phone> phones) {
    this.phones = phones;
  }
  // Accessors -

}
