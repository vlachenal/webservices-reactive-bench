/*
 * Copyright © 2017 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservice.reactive.bench.dto;

/**
 * Phone number DTO
 *
 * @author Vincent Lachenal
 */
public class PhoneDTO {

  /**
   * Phone type
   *
   * @author Vincent Lachenal
   */
  public enum Type {
    // Values +
    /** Landline phone */
    LANDLINE((short)1),

    /** Mobile phone */
    MOBILE((short)2);
    // Values -

    // Attributes +
    /** Code */
    private final short code;
    // Attributes -

    // Constructors +
    /**
     * {@link Type} private constructor
     *
     * @param code the code
     */
    private Type(final short code) {
      this.code = code;
    }
    // Constructors -

    // Accessors +
    /**
     * {@link Type} code getter
     *
     * @return the code
     */
    public short getCode() {
      return code;
    }
    // Accessors +

    // Methods +
    /**
     * Get type from code
     *
     * @param code the code
     *
     * @return the type
     */
    public static Type fromCode(final short code) {
      switch(code) {
        case 1:
          return LANDLINE;
        case 2:
          return MOBILE;
        default:
          return null;
      }
    }
    // Methods -

  }

  // Attributes +
  /** Phone type */
  private Type type;

  /** Phone number */
  private String number;
  // Attributes -


  // Constructors +
  /**
   * {@link PhoneDTO} default constructor
   */
  public PhoneDTO() {
    // Nothing to do
  }

  /**
   * {@link PhoneDTO} constructor
   *
   * @param type the phone type
   * @param number the phone number
   */
  public PhoneDTO(final short type, final String number) {
    this.type = PhoneDTO.Type.fromCode(type);
    this.number = number;
  }
  // Constructors -


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
