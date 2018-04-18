/*
 * Copyright © 2017 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservice.reactive.bench.dto;

import java.util.Date;
import java.util.List;
import java.util.UUID;


/**
 * Customer DTO
 *
 * @author Vincent Lachenal
 */
public class CustomerDTO {

  // Attributes +
  /** Customer identifier */
  private String id;

  /** Customer first name */
  private String firstName;

  /* Customer last name */
  private String lastName;

  /** Customer brith date */
  private Date birthDate;

  /** Customer address */
  private AddressDTO address;

  /** Customer email address */
  private String email;

  /** Customer phone numbers */
  private List<PhoneDTO> phones;
  // Attributes -


  // Constructors +
  /**
   * {@link CustomerDTO} default constructor
   */
  public CustomerDTO() {
    // Nothing to do
  }

  /**
   * {@link CustomerDTO} constructor
   *
   * @param id the UUID
   * @param firstName the first name
   * @param lastName the last name
   */
  public CustomerDTO(final UUID id, final String firstName, final String lastName) {
    this.id = id.toString();
    this.firstName = firstName;
    this.lastName = lastName;
  }

  /**
   * {@link CustomerDTO} constructor
   *
   * @param id the UUID
   * @param firstName the first name
   * @param lastName the last name
   * @param birthDate the birth date
   * @param email the email
   */
  public CustomerDTO(final String id, final String firstName, final String lastName, final Date birthDate, final String email) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.birthDate = birthDate;
    this.email = email;
  }
  // Constructors -


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
  public AddressDTO getAddress() {
    return address;
  }

  /**
   * Address setter
   *
   * @param address the address to set
   */
  public void setAddress(final AddressDTO address) {
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
  public List<PhoneDTO> getPhones() {
    return phones;
  }

  /**
   * Phone numbers setter
   *
   * @param phones the phone numbers to set
   */
  public void setPhones(final List<PhoneDTO> phones) {
    this.phones = phones;
  }
  // Accessors -

}
