/*
 * Copyright © 2017 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservice.reactive.bench.dao;

import java.util.Optional;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.vlachenal.webservice.reactive.bench.dto.CallDTO;
import com.github.vlachenal.webservice.reactive.bench.dto.TestSuiteDTO;
import com.github.vlachenal.webservice.reactive.bench.jdbc.ReactiveJdbcTemplate;

import reactor.core.publisher.Flux;


/**
 * Statistics DAO
 *
 * @author Vincent Lachenal
 */
@Repository
public class StatisticsDAO {

  // Attributes +
  /** Insert test suite */
  private static final String INS_TEST_SUITE = "INSERT INTO TestSuite "
      + "(id, client_cpu, client_memory, client_jvm_version, client_jvm_vendor, client_os_name, "
      + "client_os_version, server_cpu, server_memory, server_jvm_version, server_jvm_vendor, "
      + "server_os_name, server_os_version, protocol, compression, nb_threads, comment, mapper) "
      + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

  /** Insert test call */
  private static final String INS_TEST_CALL = "INSERT INTO TestCall "
      + "(request_seq, test_suite_id, method, client_start, server_start, server_end, client_end, ok, error_message) "
      + "VALUES (?,?,?,?,?,?,?,?,?)";

  /** JDBC template */
  private ReactiveJdbcTemplate jdbc;
  // Attributes -


  // Methods +
  /**
   * Initialize JDBC template with datasource
   *
   * @param dataSource the datasource to use
   */
  @Autowired
  public void setDataSource(@Qualifier("ds.customer") final DataSource dataSource) {
    jdbc = new ReactiveJdbcTemplate(dataSource);
  }

  /**
   * Save test suite in database
   *
   * @param testSuite the test suite to save
   *
   * @return the test suite UUID
   */
  @Transactional
  public String save(final TestSuiteDTO testSuite) {
    final UUID uuid = UUID.randomUUID();
    jdbc.update(INS_TEST_SUITE, ps -> {
      ps.setObject(1, uuid);
      ps.setString(2, testSuite.getClientCpu());
      ps.setString(3, testSuite.getClientMemory());
      ps.setString(4, testSuite.getClientJvmVersion());
      ps.setString(5, testSuite.getClientJvmVendor());
      ps.setString(6, testSuite.getClientOsName());
      ps.setString(7, testSuite.getClientOsVersion());
      ps.setString(8, testSuite.getServerCpu());
      ps.setString(9, testSuite.getServerMemory());
      ps.setString(10, testSuite.getServerJvmVersion());
      ps.setString(11, testSuite.getServerJvmVendor());
      ps.setString(12, testSuite.getServerOsName());
      ps.setString(13, testSuite.getServerOsVersion());
      ps.setString(14, testSuite.getProtocol());
      ps.setString(15, testSuite.getCompression());
      ps.setInt(16, testSuite.getNbThreads());
      ps.setString(17, testSuite.getComment());
      ps.setString(18, testSuite.getMapper());
    });
    Optional.ofNullable(testSuite.getCalls()).ifPresent(calls -> {
      registerCalls(uuid, Flux.fromIterable(calls));
    });
    return uuid.toString();
  }

  /**
   * Register calls to test suite
   *
   * @param uuid the test suite UUID
   * @param calls the calls to register
   */
  @Transactional
  public void registerCalls(final UUID uuid, final Flux<CallDTO> calls) {
    jdbc.batchUpdate(INS_TEST_CALL, calls, 250, (ps, call) -> {
      ps.setInt(1, call.getSeq());
      ps.setObject(2, uuid);
      ps.setString(3, call.getMethod());
      ps.setLong(4, call.getClientStart());
      ps.setLong(5, call.getServerStart());
      ps.setLong(6, call.getServerEnd());
      ps.setLong(7, call.getClientEnd());
      ps.setBoolean(8, call.isOk());
      ps.setString(9, call.getErrMsg());
    });
  }
  // Methods -

}
