/*
 * Copyright © 2017 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservice.reactive.bench.errors;


/**
 * Data not found exception
 *
 * @author Vincent Lachenal
 */
@SuppressWarnings("serial")
public class NotFoundException extends Exception {

  /**
   * {@link NotFoundException} default constructor
   */
  public NotFoundException() {
    super();
  }

  /**
   * {@link NotFoundException} constructor
   *
   * @param messages the error message
   */
  public NotFoundException(final String messages) {
    super(messages);
  }

  /**
   * {@link NotFoundException} constructor
   *
   * @param cause the error cause
   */
  public NotFoundException(final Throwable cause) {
    super(cause);
  }

  /**
   * {@link NotFoundException} constructor
   *
   * @param message the error message
   * @param cause the error cause
   */
  public NotFoundException(final String message, final Throwable cause) {
    super(message, cause);
  }

}
