package com.github.vlachenal.webservice.reactive.bench.errors;


/**
 * Invalid parameter(s) error which can be thrown by business classes
 *
 * @author Vincent Lachenal
 */
@SuppressWarnings("serial")
public class InvalidParametersException extends Exception {

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
