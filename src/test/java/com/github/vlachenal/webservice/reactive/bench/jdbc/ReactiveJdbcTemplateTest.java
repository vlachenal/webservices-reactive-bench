/*
 * Copyright © 2017 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservice.reactive.bench.jdbc;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.sql.DataSource;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
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
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ReactiveJdbcTemplateTest {

  // Attributes +
  /** {@link ReactiveJdbcTemplateTest} logger instance */
  private static final Logger LOG = LoggerFactory.getLogger(ReactiveJdbcTemplateTest.class);

  /** Reactive JDBC template */
  private static ReactiveJdbcTemplate jdbc;

  /** JDBC template has been initialized */
  private static AtomicBoolean initialized = new AtomicBoolean(false);

  /** Created customers' UUIDs */
  private final Set<String> uuids = new HashSet<>();

  /** Customer datasource */
  @Qualifier("ds.customer")
  @Autowired
  private DataSource dataSource;
  // Attributes -


  // Initialization +
  /**
   * Initialize JDBC template if needed
   */
  @Before
  public void before() {
    LOG.debug("Initialize reactive JDBC template");
    if(!initialized.get()) {
      final boolean ci = Optional.ofNullable(System.getProperty("spring.profiles.active")).map(s -> s.contains("ci")).orElse(Boolean.FALSE);
      if(ci) {
        final ResourceDatabasePopulator populator = new ResourceDatabasePopulator(new ClassPathResource("schema-hsqldb.sql"));
        populator.execute(dataSource);
      }
      jdbc = new ReactiveJdbcTemplate(dataSource);
      initialized.set(true);
      if(ci) {
        jdbc.update("DELETE FROM customer");
      }
    }
  }
  // Initialization -


  // Tests +
  /**
   * Flux batch update unit tests
   */
  @Test
  public void test01BatchUpdate() {
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
    uuids.add(cust1.getId());
    final CustomerDTO cust2 = new CustomerDTO();
    cust2.setId(UUID.randomUUID().toString());
    cust2.setFirstName("Jim");
    cust2.setLastName("Morrison");
    cust2.setBirthDate(Date.from(LocalDate.parse("1943-12-08", format).atStartOfDay(ZoneId.systemDefault()).toInstant()));
    cust2.setEmail("jim.morisson@yopmail.com");
    customers.add(cust2);
    uuids.add(cust2.getId());
    final CustomerDTO cust3 = new CustomerDTO();
    cust3.setId(UUID.randomUUID().toString());
    cust3.setFirstName("Jimi");
    cust3.setLastName("Hendrix");
    cust3.setBirthDate(Date.from(LocalDate.parse("1942-11-27", format).atStartOfDay(ZoneId.systemDefault()).toInstant()));
    cust3.setEmail("jimi.hendrix@yopmail.com");
    customers.add(cust3);
    uuids.add(cust3.getId());
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
  public void test02BatchUpdateOnBatchSize() {
    LOG.info("Enter in testBatchUpdate");
    final DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    final ArrayList<CustomerDTO> customers = new ArrayList<>();
    final CustomerDTO cust1 = new CustomerDTO();
    cust1.setId(UUID.randomUUID().toString());
    cust1.setFirstName("Chuck");
    cust1.setLastName("Norris");
    cust1.setBirthDate(Date.from(LocalDate.parse("1940-03-10", format).atStartOfDay(ZoneId.systemDefault()).toInstant()));
    cust1.setEmail("chuck.norris@yopmail.com");
    customers.add(cust1);
    uuids.add(cust1.getId());
    final CustomerDTO cust2 = new CustomerDTO();
    cust2.setId(UUID.randomUUID().toString());
    cust2.setFirstName("Steven");
    cust2.setLastName("Seagal");
    cust2.setBirthDate(Date.from(LocalDate.parse("1952-04-10", format).atStartOfDay(ZoneId.systemDefault()).toInstant()));
    cust2.setEmail("steven.seagla@yopmail.com");
    customers.add(cust2);
    uuids.add(cust2.getId());
    final CustomerDTO cust3 = new CustomerDTO();
    cust3.setId(UUID.randomUUID().toString());
    cust3.setFirstName("Dolph");
    cust3.setLastName("Lundgren");
    cust3.setBirthDate(Date.from(LocalDate.parse("1957-11-03", format).atStartOfDay(ZoneId.systemDefault()).toInstant()));
    cust3.setEmail("dolph.lundgren@yopmail.com");
    customers.add(cust3);
    uuids.add(cust3.getId());
    final CustomerDTO cust4 = new CustomerDTO();
    cust4.setId(UUID.randomUUID().toString());
    cust4.setFirstName("Jean-Claude");
    cust4.setLastName("Van Damme");
    cust4.setBirthDate(Date.from(LocalDate.parse("1960-10-18", format).atStartOfDay(ZoneId.systemDefault()).toInstant()));
    cust4.setEmail("jean-claude.van-damme@yopmail.com");
    customers.add(cust4);
    uuids.add(cust4.getId());
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
   * Test method for {@link com.github.vlachenal.webservice.reactive.bench.jdbc.ReactiveJdbcTemplate#flow(java.lang.String, org.springframework.jdbc.core.RowMapper, java.lang.Object[])}.
   *
   * @throws InterruptedException can not happened
   */
  @Test
  public void test03QueryForFlux() throws InterruptedException {
    LOG.debug("Enter in testQueryForFlux");
    final boolean checkIds = !uuids.isEmpty();
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
      if(checkIds) {
        assertTrue("Customer " + cust.getId() + " has not been created by test", uuids.contains(cust.getId()));
        uuids.remove(cust.getId());
      }
    }).doOnComplete(() -> {
      LOG.info("Data has been successfully retrieved");
    }).doOnError(e -> {
      LOG.error(e.getMessage(), e);
      fail(e.getMessage());
    }).subscribe();
    assertTrue(uuids.isEmpty());
    LOG.debug("Exit testQueryForFlux");
  }
  // Tests -

}
