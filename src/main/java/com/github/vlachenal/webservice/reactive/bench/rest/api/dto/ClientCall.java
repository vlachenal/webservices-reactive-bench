/*
 * Copyright © 2017 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservice.reactive.bench.rest.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Client call
 *
 * @author Vincent Lachenal
 */
public class ClientCall {

  // Attributes +
  /** Request sequence identifier */
  @JsonProperty(value="request_seq",required=true)
  private Integer requestSeq;

  /** Protocol (always 'rest') */
  private String protocol = "rest";

  /** The method which has been called */
  @JsonProperty(value="method",required=true)
  private String method;

  /** Client start timestamp */
  @JsonProperty(value="client_start",required=true)
  private Long clientStart;

  /** Client end timestamp */
  @JsonProperty(value="client_end",required=true)
  private Long clientEnd;

  /** Call status */
  @JsonProperty(value="ok",required=true)
  private boolean ok = true;

  /** Error message (it should be set when ok = false) */
  @JsonProperty(value="err_msg",required=false)
  private String errMsg;
  // Attributes -


  // Accessors +
  /**
   * Request sequence getter
   *
   * @return the request sequence
   */
  public final Integer getRequestSeq() {
    return requestSeq;
  }

  /**
   * Request sequence setter
   *
   * @param requestSeq the request sequence to set
   */
  public final void setRequestSeq(final Integer requestSeq) {
    this.requestSeq = requestSeq;
  }

  /**
   * Protocol getter
   *
   * @return the protocol
   */
  public final String getProtocol() {
    return protocol;
  }

  /**
   * Protocol setter
   *
   * @param protocol the protocol to set
   */
  public final void setProtocol(final String protocol) {
    this.protocol = protocol;
  }

  /**
   * Method getter
   *
   * @return the method
   */
  public final String getMethod() {
    return method;
  }

  /**
   * Method setter
   *
   * @param method the method to set
   */
  public final void setMethod(final String method) {
    this.method = method;
  }

  /**
   * Client start time in ns getter
   *
   * @return the time
   */
  public final Long getClientStart() {
    return clientStart;
  }

  /**
   * Client start time in ns setter
   *
   * @param clientStart the time to set
   */
  public final void setClientStart(final Long clientStart) {
    this.clientStart = clientStart;
  }

  /**
   * Client end time in ns getter
   *
   * @return the time
   */
  public final Long getClientEnd() {
    return clientEnd;
  }

  /**
   * Client end time in ns setter
   *
   * @param clientEnd the time to set
   */
  public final void setClientEnd(final Long clientEnd) {
    this.clientEnd = clientEnd;
  }

  /**
   * Is call OK getter
   *
   * @return the status
   */
  public final boolean isOk() {
    return ok;
  }

  /**
   * Is call OK setter
   *
   * @param ok the status to set
   */
  public final void setOk(final boolean ok) {
    this.ok = ok;
  }

  /**
   * Error message getter
   *
   * @return the error message
   */
  public final String getErrMsg() {
    return errMsg;
  }

  /**
   * Error message setter
   *
   * @param errMsg the error message to set
   */
  public final void setErrMsg(final String errMsg) {
    this.errMsg = errMsg;
  }
  // Accessors -

}
