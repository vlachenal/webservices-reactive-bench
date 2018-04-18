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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.vlachenal.webservice.reactive.bench.dto.TestSuiteDTO;


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
  private JdbcTemplate jdbc;
  // Attributes -


  // Methods +
  /**
   * Initialize JDBC template with datasource
   *
   * @param dataSource the datasource to use
   */
  @Autowired
  public void setDataSource(@Qualifier("ds.customer") final DataSource dataSource) {
    jdbc = new JdbcTemplate(dataSource);
  }

  /**
   * Save test suite in database
   *
   * @param testSuite the test suite to save
   */
  @Transactional
  public void save(final TestSuiteDTO testSuite) {
    Optional.ofNullable(testSuite).ifPresent(test -> {
      final UUID uuid = UUID.randomUUID();
      jdbc.update(INS_TEST_SUITE, ps -> {
        ps.setObject(1, uuid);
        ps.setString(2, test.getClientCpu());
        ps.setString(3, test.getClientMemory());
        ps.setString(4, test.getClientJvmVersion());
        ps.setString(5, test.getClientJvmVendor());
        ps.setString(6, test.getClientOsName());
        ps.setString(7, test.getClientOsVersion());
        ps.setString(8, test.getServerCpu());
        ps.setString(9, test.getServerMemory());
        ps.setString(10, test.getServerJvmVersion());
        ps.setString(11, test.getServerJvmVendor());
        ps.setString(12, test.getServerOsName());
        ps.setString(13, test.getServerOsVersion());
        ps.setString(14, test.getProtocol());
        ps.setString(15, test.getCompression());
        ps.setInt(16, test.getNbThreads());
        ps.setString(17, test.getComment());
        ps.setString(18, test.getMapper());
      });
      jdbc.batchUpdate(INS_TEST_CALL, test.getCalls(), test.getCalls().size(), (ps, call) -> {
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
    });
  }
  // Methods -

}
