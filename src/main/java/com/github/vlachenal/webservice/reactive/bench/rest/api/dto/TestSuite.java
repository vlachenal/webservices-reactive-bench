/*
 * Copyright © 2017 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservice.reactive.bench.rest.api.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;


/**
 * Test suite for REST protocol
 *
 * @author Vincent Lachenal
 */
public class TestSuite {

  // Attributes +
  /** Test suite UUID */
  @JsonProperty(value="id",required=false)
  private String id;

  /** Number of simultaneous call */
  @JsonProperty(value="nb_thread",required=true)
  private Integer nbThread;

  /** Compression type */
  @JsonProperty(value="compression",required=false)
  private String compression;

  /** Client CPU model */
  @JsonProperty(value="cpu",required=true)
  private String cpu;

  /** Client RAM */
  @JsonProperty(value="memory",required=true)
  private String memory;

  /** Client JVM version */
  @JsonProperty(value="jvm",required=true)
  private String jvm;

  /** Client JVM vendor */
  @JsonProperty(value="vendor",required=true)
  private String vendor;

  /** Client OS family */
  @JsonProperty(value="os_family",required=true)
  private String osFamily;

  /** Client OS version */
  @JsonProperty(value="os_version",required=true)
  private String osVersion;

  /** Test suite protocol */
  @JsonProperty(value="protocol",required=true,defaultValue="rest")
  private String protocol;

  /** Test suite comments */
  @JsonProperty(value="comment",required=false)
  private String comment;

  /** Client call statistics */
  @JsonProperty(value="calls",required=true)
  private List<ClientCall> calls;

  /** Mapper */
  @JsonProperty(value="mapper")
  private Mapper mapper;
  // Attributes -


  // Accessors +
  /**
   * Test suite identifier getter
   *
   * @return the id
   */
  public final String getId() {
    return id;
  }

  /**
   * Test suite identifier setter
   *
   * @param id the id to set
   */
  public final void setId(final String id) {
    this.id = id;
  }

  /**
   * Number of simultaneous call getter
   *
   * @return the number of thread
   */
  public final Integer getNbThread() {
    return nbThread;
  }

  /**
   * Number of simultaneous call setter
   *
   * @param nbThread the number of thread to set
   */
  public final void setNbThread(final Integer nbThread) {
    this.nbThread = nbThread;
  }

  /**
   * HTTP compression getter
   *
   * @return the compression
   */
  public final String getCompression() {
    return compression;
  }

  /**
   * HTTP compression setter
   *
   * @param compression the compression to set
   */
  public final void setCompression(final String compression) {
    this.compression = compression;
  }

  /**
   * Client CPU information getter
   *
   * @return the cpu
   */
  public final String getCpu() {
    return cpu;
  }

  /**
   * Client CPU information setter
   *
   * @param cpu the cpu to set
   */
  public final void setCpu(final String cpu) {
    this.cpu = cpu;
  }

  /**
   * Client memory getter
   *
   * @return the memory
   */
  public final String getMemory() {
    return memory;
  }

  /**
   * Client memory setter
   *
   * @param memory the memory to set
   */
  public final void setMemory(final String memory) {
    this.memory = memory;
  }

  /**
   * JVM version getter
   *
   * @return the jvm
   */
  public final String getJvm() {
    return jvm;
  }

  /**
   * JVM version setter
   *
   * @param jvm the jvm to set
   */
  public final void setJvm(final String jvm) {
    this.jvm = jvm;
  }

  /**
   * JVM vendor getter
   *
   * @return the vendor
   */
  public final String getVendor() {
    return vendor;
  }

  /**
   * JVM vendor setter
   *
   * @param vendor the vendor to set
   */
  public final void setVendor(final String vendor) {
    this.vendor = vendor;
  }

  /**
   * Client OS family getter
   *
   * @return the OS familiy
   */
  public final String getOsFamily() {
    return osFamily;
  }

  /**
   * Client OS family setter
   *
   * @param osFamily the osFamiliy to set
   */
  public final void setOsFamily(final String osFamily) {
    this.osFamily = osFamily;
  }

  /**
   * Client OS version getter
   *
   * @return the OS version
   */
  public final String getOsVersion() {
    return osVersion;
  }

  /**
   * Client OS version setter
   *
   * @param osVersion the OS version to set
   */
  public final void setOsVersion(final String osVersion) {
    this.osVersion = osVersion;
  }

  /**
   * Test protocol getter
   *
   * @return the protocol
   */
  public final String getProtocol() {
    return protocol;
  }

  /**
   * Test protocol setter
   *
   * @param protocol the protocol to set
   */
  public final void setProtocol(final String protocol) {
    this.protocol = protocol;
  }

  /**
   * Comment getter
   *
   * @return the comment
   */
  public final String getComment() {
    return comment;
  }

  /**
   * Comment setter
   *
   * @param comment the comment to set
   */
  public final void setComment(final String comment) {
    this.comment = comment;
  }

  /**
   * Client calls statistics getter
   *
   * @return the calls
   */
  public final List<ClientCall> getCalls() {
    return calls;
  }

  /**
   * Client calls statistics setter
   *
   * @param calls the calls to set
   */
  public final void setCalls(final List<ClientCall> calls) {
    this.calls = calls;
  }

  /**
   * Mapper getter
   *
   * @return the mapper
   */
  public final Mapper getMapper() {
    return mapper;
  }

  /**
   * Mapper setter
   *
   * @param mapper the mapper to set
   */
  public final void setMapper(final Mapper mapper) {
    this.mapper = mapper;
  }
  // Accessors -

}
