/*
 * Copyright © 2017 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservice.reactive.bench.rest.api.model;

/**
 * Phone number
 *
 * @author Vincent Lachenal
 */
public class Phone {

  /**
   * Phone type
   *
   * @author Vincent Lachenal
   */
  public enum Type {
    LANDLINE,
    MOBILE
  }

  // Attributes +
  /** Phone type */
  private Type type;

  /** Phone number */
  private String number;
  // Attributes -


  // Accessors +
  /**
   * Phone type getter
   *
   * @return the type
   */
  public Type getType() {
    return type;
  }

  /**
   * Phone type setter
   *
   * @param type the type to set
   */
  public void setType(final Type type) {
    this.type = type;
  }

  /**
   * Phone number getter
   *
   * @return the phone number
   */
  public String getNumber() {
    return number;
  }

  /**
   * Phone number setter
   *
   * @param number the phone number to set
   */
  public void setNumber(final String number) {
    this.number = number;
  }
  // Accessors -

}

