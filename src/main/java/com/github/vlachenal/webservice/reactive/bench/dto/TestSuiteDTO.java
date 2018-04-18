/*
 * Copyright © 2017 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservice.reactive.bench.dto;

import java.util.List;

/**
 * Test suite
 *
 * @author Vincent Lachenal
 */
public class TestSuiteDTO {

  // Attributes +
  /** Test suite UUID */
  private String id;

  /** Client CPU information */
  private String clientCpu;

  /** Client memory information */
  private String clientMemory;

  /** Client JVM version */
  private String clientJvmVersion;

  /** Client JVM vendor */
  private String clientJvmVendor;

  /** Client OS name */
  private String clientOsName;

  /** Client OS version */
  private String clientOsVersion;

  /** Server CPU information */
  private String serverCpu;

  /** Server memory information */
  private String serverMemory;

  /** Server JVM version */
  private String serverJvmVersion;

  /** Server JVM vendor */
  private String serverJvmVendor;

  /** Server OS name */
  private String serverOsName;

  /** Server OS version */
  private String serverOsVersion;

  /** Test suite protocol */
  private String protocol;

  /** Test suite transport compression */
  private String compression;

  /** Test suite number of simultaneous calls */
  private int nbThreads;

  /** Comments */
  private String comment;

  /** Calls */
  private List<CallDTO> calls;

  /** The mapper which has been used */
  private String mapper;
  // Attributes -


  // Accessors +
  /**
   * Test suite UUID getter
   *
   * @return the UUID
   */
  public final String getId() {
    return id;
  }

  /**
   * Test suite UUID setter
   *
   * @param id the UUID to set
   */
  public final void setId(final String id) {
    this.id = id;
  }

  /**
   * Client CPU information getter
   *
   * @return the client CPU
   */
  public final String getClientCpu() {
    return clientCpu;
  }

  /**
   * Client CPU information setter
   *
   * @param clientCpu the client CPU to set
   */
  public final void setClientCpu(final String clientCpu) {
    this.clientCpu = clientCpu;
  }

  /**
   * Client memory information getter
   *
   * @return the client memory
   */
  public final String getClientMemory() {
    return clientMemory;
  }

  /**
   * Client memory information setter
   *
   * @param clientMemory the client memory to set
   */
  public final void setClientMemory(final String clientMemory) {
    this.clientMemory = clientMemory;
  }

  /**
   * Client JVM version getter
   *
   * @return the client JVM version
   */
  public final String getClientJvmVersion() {
    return clientJvmVersion;
  }

  /**
   * Client JVM version setter
   *
   * @param clientJvmVersion the client JVM version to set
   */
  public final void setClientJvmVersion(final String clientJvmVersion) {
    this.clientJvmVersion = clientJvmVersion;
  }

  /**
   * Client JVM vendor getter
   *
   * @return the client JVM vendor
   */
  public final String getClientJvmVendor() {
    return clientJvmVendor;
  }

  /**
   * Client JVM vendor setter
   *
   * @param clientJvmVendor the client JVM vendor to set
   */
  public final void setClientJvmVendor(final String clientJvmVendor) {
    this.clientJvmVendor = clientJvmVendor;
  }

  /**
   * Client OS name getter
   *
   * @return the client OS name
   */
  public final String getClientOsName() {
    return clientOsName;
  }

  /**
   * Client OS name setter
   *
   * @param clientOsName the clientOS name to set
   */
  public final void setClientOsName(final String clientOsName) {
    this.clientOsName = clientOsName;
  }

  /**
   * Client OS version getter
   *
   * @return the client OS version
   */
  public final String getClientOsVersion() {
    return clientOsVersion;
  }

  /**
   * Client OS version setter
   *
   * @param clientOsVersion the client OS version to set
   */
  public final void setClientOsVersion(final String clientOsVersion) {
    this.clientOsVersion = clientOsVersion;
  }

  /**
   * Server CPU information getter
   *
   * @return the server CPU
   */
  public final String getServerCpu() {
    return serverCpu;
  }

  /**
   * Server CPU information setter
   *
   * @param serverCpu the server CPU to set
   */
  public final void setServerCpu(final String serverCpu) {
    this.serverCpu = serverCpu;
  }

  /**
   * Server memory information getter
   *
   * @return the server memory
   */
  public final String getServerMemory() {
    return serverMemory;
  }

  /**
   * Server memory information setter
   *
   * @param serverMemory the server memory to set
   */
  public final void setServerMemory(final String serverMemory) {
    this.serverMemory = serverMemory;
  }

  /**
   * Server JVM version getter
   *
   * @return the server JVM version
   */
  public final String getServerJvmVersion() {
    return serverJvmVersion;
  }

  /**
   * Server JVM version setter
   *
   * @param serverJvmVersion the server JVM version to set
   */
  public final void setServerJvmVersion(final String serverJvmVersion) {
    this.serverJvmVersion = serverJvmVersion;
  }

  /**
   * Server JVM vendor getter
   *
   * @return the server JVM vendor
   */
  public final String getServerJvmVendor() {
    return serverJvmVendor;
  }

  /**
   * Server JVM vendor setter
   *
   * @param serverJvmVendor the server JVM vendor to set
   */
  public final void setServerJvmVendor(final String serverJvmVendor) {
    this.serverJvmVendor = serverJvmVendor;
  }

  /**
   * Server OS name getter
   *
   * @return the server OS name
   */
  public final String getServerOsName() {
    return serverOsName;
  }

  /**
   * Server OS name setter
   *
   * @param serverOsName the server OS name to set
   */
  public final void setServerOsName(final String serverOsName) {
    this.serverOsName = serverOsName;
  }

  /**
   * Server OS version getter
   *
   * @return the server OS version
   */
  public final String getServerOsVersion() {
    return serverOsVersion;
  }

  /**
   * Server OS version setter
   *
   * @param serverOsVersion the server OS version to set
   */
  public final void setServerOsVersion(final String serverOsVersion) {
    this.serverOsVersion = serverOsVersion;
  }

  /**
   * Test suite protocol getter
   *
   * @return the protocol
   */
  public final String getProtocol() {
    return protocol;
  }

  /**
   * Test suite protocol setter
   *
   * @param protocol the protocol to set
   */
  public final void setProtocol(final String protocol) {
    this.protocol = protocol;
  }

  /**
   * Transport compression getter
   *
   * @return the compression
   */
  public final String getCompression() {
    return compression;
  }

  /**
   * Transport compression getter
   *
   * @param compression the compression to set
   */
  public final void setCompression(final String compression) {
    this.compression = compression;
  }

  /**
   * Test suite number of simultaneous calls getter
   *
   * @return the number of threads
   */
  public final int getNbThreads() {
    return nbThreads;
  }

  /**
   * Test suite number of simultaneous calls setter
   *
   * @param nbThreads the number of threads to set
   */
  public final void setNbThreads(final int nbThreads) {
    this.nbThreads = nbThreads;
  }

  /**
   * Comments getter
   *
   * @return the comment
   */
  public final String getComment() {
    return comment;
  }

  /**
   * Comments setter
   *
   * @param comment the comment to set
   */
  public final void setComment(final String comment) {
    this.comment = comment;
  }

  /**
   * Calls getter
   *
   * @return the calls
   */
  public final List<CallDTO> getCalls() {
    return calls;
  }

  /**
   * Calls setter
   *
   * @param calls the calls to set
   */
  public final void setCalls(final List<CallDTO> calls) {
    this.calls = calls;
  }

  /**
   * Mapper getter
   *
   * @return the mapper
   */
  public final String getMapper() {
    return mapper;
  }

  /**
   * Mapper setter
   *
   * @param mapper the mapper to set
   */
  public final void setMapper(final String mapper) {
    this.mapper = mapper;
  }
  // Accessors -

}
