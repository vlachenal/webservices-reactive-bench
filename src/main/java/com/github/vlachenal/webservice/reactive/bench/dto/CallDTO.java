/*
 * Copyright © 2017 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservice.reactive.bench.dto;


/**
 * Call statistic DTO
 *
 * @author Vincent Lachenal
 */
public class CallDTO {

  // Attributes +
  /** Call sequence */
  private int seq = -1;

  /** Call protocol (rest, thrift, ...) */
  private String protocol;

  /** Call method */
  private String method;

  /** Client start in ns */
  private long clientStart = -1;

  /** Server start in ns */
  private long serverStart = -1;

  /** Server end in ns */
  private long serverEnd = -1;

  /** Client end in ns */
  private long clientEnd = -1;

  /** Call status */
  private boolean ok = false;

  /** Call error message */
  private String errMsg = null;
  // Attributes -


  // Methods +
  /**
   * Get call key
   *
   * @return the key
   */
  public String getKey() {
    return protocol + '-' + method + '-' + seq;
  }
  // Methods -


  // Accessors +
  /**
   * Sequence getter
   *
   * @return the sequence
   */
  public int getSeq() {
    return seq;
  }

  /**
   * Sequence setter
   *
   * @param seq the sequence to set
   */
  public void setSeq(final int seq) {
    this.seq = seq;
  }

  /**
   * Protocol getter
   *
   * @return the protocol
   */
  public String getProtocol() {
    return protocol;
  }

  /**
   * Protocol setter
   *
   * @param protocol the protocol to set
   */
  public void setProtocol(final String protocol) {
    this.protocol = protocol;
  }

  /**
   * Method getter
   *
   * @return the method
   */
  public String getMethod() {
    return method;
  }

  /**
   * Method setter
   *
   * @param method the method to set
   */
  public void setMethod(final String method) {
    this.method = method;
  }

  /**
   * Client timestamp start getter
   *
   * @return the timestamp
   */
  public long getClientStart() {
    return clientStart;
  }

  /**
   * Client timestamp start setter
   *
   * @param clientStart the timestamp to set
   */
  public void setClientStart(final long clientStart) {
    this.clientStart = clientStart;
  }

  /**
   * Server timestamp start getter
   *
   * @return the timestamp
   */
  public long getServerStart() {
    return serverStart;
  }

  /**
   * Server timestamp start setter
   *
   * @param serverStart the timestamp to set
   */
  public void setServerStart(final long serverStart) {
    this.serverStart = serverStart;
  }

  /**
   * Server timestamp end getter
   *
   * @return the timestamp
   */
  public long getServerEnd() {
    return serverEnd;
  }

  /**
   * Server timestamp end setter
   *
   * @param serverEnd the timestamp to set
   */
  public void setServerEnd(final long serverEnd) {
    this.serverEnd = serverEnd;
  }

  /**
   * Client timestamp end getter
   *
   * @return the timestamp
   */
  public long getClientEnd() {
    return clientEnd;
  }

  /**
   * Client timestamp end setter
   *
   * @param clientEnd the timestamp to set
   */
  public void setClientEnd(final long clientEnd) {
    this.clientEnd = clientEnd;
  }

  /**
   * Call status getter
   *
   * @return the call status
   */
  public final boolean isOk() {
    return ok;
  }

  /**
   * Call status setter
   *
   * @param ok the call status to set
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
