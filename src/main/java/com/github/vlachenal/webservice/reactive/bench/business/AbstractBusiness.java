package com.github.vlachenal.webservice.reactive.bench.business;

import java.util.Collection;

import com.github.vlachenal.webservice.reactive.bench.errors.InvalidParametersException;


/**
 * Abstract business class
 *
 * @author Vincent Lachenal
 */
public abstract class AbstractBusiness {

  /**
   * Check parameters
   *
   * @param errorMsg the error message
   * @param params the parameters
   *
   * @throws InvalidParametersException missing or invalid parameters
   */
  protected void checkParameters(final String errorMsg, final Object... params) throws InvalidParametersException {
    for(final Object param : params) {
      if(param == null) {
        throw new InvalidParametersException(errorMsg + ": " + params);
      }
    }
  }

  /**
   * Check collection parameter (null or empty collection is consederd as error)
   *
   * @param errorMsg the error message
   * @param list the collection to check
   *
   * @throws InvalidParametersException missing or invalid parameter
   */
  protected <T> void checkParameter(final String errorMsg, final Collection<T> list) throws InvalidParametersException {
    if(list == null || list.isEmpty()) {
      throw new InvalidParametersException(errorMsg);
    }
  }

}
