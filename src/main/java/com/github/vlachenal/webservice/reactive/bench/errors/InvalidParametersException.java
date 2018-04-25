/*
 * Copyright © 2017 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservice.reactive.bench.errors;


/**
 * Invalid parameter(s) error which can be thrown by business classes
 *
 * @author Vincent Lachenal
 */
@SuppressWarnings("serial")
public class InvalidParametersException extends RuntimeException {

  // Constructors +
  /**
   * {@link InvalidParametersException} default constructor
   */
  public InvalidParametersException() {
    super();
  }

  /**
   * {@link InvalidParametersException} constructor
   *
   * @param message the error message
   * @param cause the error cause
   */
  public InvalidParametersException(final String message, final Throwable cause) {
    super(message, cause);
  }

  /**
   * {@link InvalidParametersException} constructor
   *
   * @param message the error message
   */
  public InvalidParametersException(final String message) {
    super(message);
  }

  /**
   * {@link InvalidParametersException} constructor
   *
   * @param cause the error cause
   */
  public InvalidParametersException(final Throwable cause) {
    super(cause);
  }
  // Constructors -

}
