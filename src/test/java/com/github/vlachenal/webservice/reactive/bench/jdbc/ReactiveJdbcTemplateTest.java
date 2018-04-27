/*
 * Copyright © 2017 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservice.reactive.bench.jdbc;

import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.vlachenal.webservice.reactive.bench.dto.CustomerDTO;

import reactor.core.publisher.Flux;


/**
 * Reactive JDBC template unit tests
 *
 * @author Vincent Lachenal
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class ReactiveJdbcTemplateTest {

  // Attributes +
  /** {@link ReactiveJdbcTemplateTest} logger instance */
  private static final Logger LOG = LoggerFactory.getLogger(ReactiveJdbcTemplateTest.class);

  /** Customer datasource */
  @Qualifier("ds.customer")
  @Autowired
  private DataSource dataSource;

  /** Reactive JDBC template */
  private ReactiveJdbcTemplate jdbc;

  /** JDBC template has been initialized */
  private boolean initialized = false;
  // Attributes -


  // Initialization +
  /**
   * Initialize JDBC template if needed
   */
  @Before
  public void before() {
    if(!initialized) {
      jdbc = new ReactiveJdbcTemplate(dataSource);
      initialized = true;
    }
  }
  // Initialization -


  // Tests +
  /**
   * Test method for {@link com.github.vlachenal.webservice.reactive.bench.jdbc.ReactiveJdbcTemplate#flow(java.lang.String, org.springframework.jdbc.core.RowMapper, java.lang.Object[])}.
   *
   * @throws InterruptedException can not happened
   */
  @Test
  public void testQueryForFlux() throws InterruptedException {
    LOG.debug("Enter in testQueryForFlux");
    jdbc.queryForFlux("SELECT id,first_name,last_name FROM Customer", (rs, rowNum) -> {
      LOG.info("Wait for 50ms");
      try {
        Thread.sleep(50);
      } catch(final InterruptedException e) {
        throw new RuntimeException(e.getMessage(), e);
      }
      LOG.info("Retrieve customer #{} data", rowNum);
      return new CustomerDTO(UUID.fromString(rs.getString(1)), rs.getString(2), rs.getString(3));
    }).doOnNext(cust -> {
      LOG.info("Customer {}: {} {}", cust.getId(), cust.getFirstName(), cust.getLastName());
    }).doOnComplete(() -> {
      LOG.info("Data has been successfully retrieved");
    }).doOnError(e -> {
      LOG.error(e.getMessage(), e);
      fail(e.getMessage());
    }).subscribe();
    LOG.debug("Exit testQueryForFlux");
  }

  /**
   * Flux batch update unit tests
   */
  @Test
  public void testBatchUpdate() {
    LOG.info("Enter in testBatchUpdate");
    final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    final ArrayList<CustomerDTO> customers = new ArrayList<>();
    final CustomerDTO cust1 = new CustomerDTO();
    cust1.setId(UUID.randomUUID().toString());
    cust1.setFirstName("Vincent");
    cust1.setLastName("Lachenal");
    cust1.setBirthDate(Date.from(LocalDate.parse("1981-05-08", format).atStartOfDay(ZoneId.systemDefault()).toInstant()));
    cust1.setEmail("vincent.lachenal@yopmail.com");
    customers.add(cust1);
    final CustomerDTO cust2 = new CustomerDTO();
    cust2.setId(UUID.randomUUID().toString());
    cust2.setFirstName("Jim");
    cust2.setLastName("Morrison");
    cust2.setBirthDate(Date.from(LocalDate.parse("1943-12-08", format).atStartOfDay(ZoneId.systemDefault()).toInstant()));
    cust2.setEmail("jim.morisson@yopmail.com");
    customers.add(cust2);
    final CustomerDTO cust3 = new CustomerDTO();
    cust3.setId(UUID.randomUUID().toString());
    cust3.setFirstName("Jimi");
    cust3.setLastName("Hendrix");
    cust3.setBirthDate(Date.from(LocalDate.parse("1942-11-27", format).atStartOfDay(ZoneId.systemDefault()).toInstant()));
    cust3.setEmail("jimi.hendrix@yopmail.com");
    customers.add(cust3);
    final Flux<CustomerDTO> flux = Flux.fromStream(customers.stream());
    final String sql = "INSERT INTO Customer (id,first_name,last_name,birth_date,email) VALUES (?,?,?,?,?)";
    jdbc.batchUpdate(sql, flux, 2, (ps, cust) -> {
      ps.setObject(1, UUID.fromString(cust.getId()));
      ps.setString(2, cust.getFirstName());
      ps.setString(3, cust.getLastName());
      ps.setDate(4, new java.sql.Date(cust.getBirthDate().getTime()));
      ps.setString(5, cust.getEmail());
    });
    LOG.info("Exit testBatchUpdate");
  }

  /**
   * Flux batch update unit tests
   */
  @Test
  public void testBatchUpdateOnBatchSize() {
    LOG.info("Enter in testBatchUpdate");
    final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    final ArrayList<CustomerDTO> customers = new ArrayList<>();
    final CustomerDTO cust1 = new CustomerDTO();
    cust1.setId(UUID.randomUUID().toString());
    cust1.setFirstName("Vincent");
    cust1.setLastName("Lachenal");
    cust1.setBirthDate(Date.from(LocalDate.parse("1981-05-08", format).atStartOfDay(ZoneId.systemDefault()).toInstant()));
    cust1.setEmail("vincent.lachenal@yopmail.com");
    customers.add(cust1);
    final CustomerDTO cust2 = new CustomerDTO();
    cust2.setId(UUID.randomUUID().toString());
    cust2.setFirstName("Jim");
    cust2.setLastName("Morrison");
    cust2.setBirthDate(Date.from(LocalDate.parse("1943-12-08", format).atStartOfDay(ZoneId.systemDefault()).toInstant()));
    cust2.setEmail("jim.morisson@yopmail.com");
    customers.add(cust2);
    final CustomerDTO cust3 = new CustomerDTO();
    cust3.setId(UUID.randomUUID().toString());
    cust3.setFirstName("Jimi");
    cust3.setLastName("Hendrix");
    cust3.setBirthDate(Date.from(LocalDate.parse("1942-11-27", format).atStartOfDay(ZoneId.systemDefault()).toInstant()));
    cust3.setEmail("jimi.hendrix@yopmail.com");
    customers.add(cust3);
    final CustomerDTO cust4 = new CustomerDTO();
    cust4.setId(UUID.randomUUID().toString());
    cust4.setFirstName("Plip");
    cust4.setLastName("Plop");
    cust4.setBirthDate(Date.from(LocalDate.parse("1942-11-27", format).atStartOfDay(ZoneId.systemDefault()).toInstant()));
    cust4.setEmail("plip.plop@yopmail.com");
    customers.add(cust4);
    final Flux<CustomerDTO> flux = Flux.fromStream(customers.stream());
    final String sql = "INSERT INTO Customer (id,first_name,last_name,birth_date,email) VALUES (?,?,?,?,?)";
    jdbc.batchUpdate(sql, flux, 2, (ps, cust) -> {
      ps.setObject(1, UUID.fromString(cust.getId()));
      ps.setString(2, cust.getFirstName());
      ps.setString(3, cust.getLastName());
      ps.setDate(4, new java.sql.Date(cust.getBirthDate().getTime()));
      ps.setString(5, cust.getEmail());
    });
    LOG.info("Exit testBatchUpdate");
  }
  // Tests -

}
