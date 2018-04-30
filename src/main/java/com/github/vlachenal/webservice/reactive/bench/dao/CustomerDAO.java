/*
 * Copyright © 2017 Vincent Lachenal
 * This work is free. You can redistribute it and/or modify it under the
 * terms of the Do What The Fuck You Want To Public License, Version 2,
 * as published by Sam Hocevar. See the COPYING file for more details.
 */
package com.github.vlachenal.webservice.reactive.bench.dao;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.github.vlachenal.webservice.reactive.bench.dto.AddressDTO;
import com.github.vlachenal.webservice.reactive.bench.dto.CustomerDTO;
import com.github.vlachenal.webservice.reactive.bench.dto.PhoneDTO;
import com.github.vlachenal.webservice.reactive.bench.jdbc.ReactiveJdbcTemplate;

import reactor.core.publisher.Flux;


/**
 * Customer DAO
 *
 * @author Vincent Lachenal
 */
@Repository
public class CustomerDAO {

  // Attributes +
  // SQL requests +
  /** List all customer SQL request */
  private static final String REQ_LIST_ALL = "SELECT id,first_name,last_name FROM Customer";

  /** Get customer details SQL request */
  private static final String REQ_GET_CUST = "SELECT id,first_name,last_name,birth_date,email FROM Customer WHERE id = ?";

  /** Get customer address SQL request */
  private static final String REQ_GET_CUST_ADDR = "SELECT line1,line2,line3,line4,line5,line6,zip_code,city,country FROM address WHERE customer_id = ?";

  /** Get customer phones SQL request */
  private static final String REQ_GET_CUST_PHONES = "SELECT phone_type,number FROM phone WHERE customer_id = ?";

  /** Delete all customer SQL request */
  private static final String REQ_DELETE_ALL = "DELETE FROM Customer";

  /** Insert customer in database */
  private static final String ADD_CUSTOMER = "INSERT INTO customer "
      + "(id,first_name,last_name,birth_date,email) "
      + "VALUES (?,?,?,?,?)";

  /** Insert phone in database */
  private static final String ADD_ADDRESS = "INSERT INTO address "
      + "(customer_id,line1,line2,line3,line4,line5,line6,zip_code,city,country) "
      + "VALUES (?,?,?,?,?,?,?,?,?,?)";

  /** Insert phone in database */
  private static final String ADD_PHONE = "INSERT INTO phone "
      + "(customer_id,phone_type,number) "
      + "VALUES (?,?,?)";

  /** Vacuum requests */
  @Value("${ds.customer.vacuum}")
  private String vacuumReqs;
  // SQL requests -

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
   * List all customers in database
   *
   * @return the customers' stream
   */
  public Flux<CustomerDTO> listAll() {
    return jdbc.queryForFlux(REQ_LIST_ALL, (rs, rowNum) -> new CustomerDTO(rs.getObject(1, UUID.class), rs.getString(2), rs.getString(3)));
  }

  /**
   * Get customer details
   *
   * @param id the customer identifier
   *
   * @return the customer details
   */
  public Optional<CustomerDTO> getDetails(final UUID id) {
    final Optional<CustomerDTO> customer = Optional.ofNullable(jdbc.queryForObject(REQ_GET_CUST, (rs, rowNum) -> new CustomerDTO(rs.getString(1), rs.getString(2), rs.getString(3), rs.getDate(4), rs.getString(5)), id));
    customer.ifPresent(cust -> {
      cust.setAddress(Optional.ofNullable(jdbc.query(REQ_GET_CUST_ADDR,
                                                     (rs, rowNum) -> new AddressDTO(rs.getString(7).trim(),
                                                                                    rs.getString(8),
                                                                                    rs.getString(9),
                                                                                    rs.getString(1),
                                                                                    rs.getString(2),
                                                                                    rs.getString(3),
                                                                                    rs.getString(4),
                                                                                    rs.getString(5),
                                                                                    rs.getString(6)), id)
                                          .stream().findFirst().orElse(null))
                      .orElse(null));
      cust.setPhones(Optional.ofNullable(jdbc.query(REQ_GET_CUST_PHONES, (rs, rowNum) -> new PhoneDTO(rs.getShort(1), rs.getString(2)), id)).orElse(null));
    });
    return customer;
  }

  /**
   * Get address line value to insert
   *
   * @param lines the address lines
   * @param idx the line index
   *
   * @return {@code true} if line exists, {@code false} otherwise
   */
  private String getLine(final List<String> lines, final int idx) {
    String line = null;
    if(lines != null && lines.size() > idx) {
      line = lines.get(idx);
    }
    return line;
  }

  /**
   * Create customer in database
   *
   * @param customer the customer to create
   * @param uuid the new customer identifier
   *
   * @return the customer identifier
   */
  @Transactional
  public String create(final CustomerDTO customer, final UUID uuid) {
    jdbc.update(ADD_CUSTOMER, new Object[] {
      uuid,
      customer.getFirstName(),
      customer.getLastName(),
      customer.getBirthDate(),
      customer.getEmail()
    });
    if(customer.getAddress() != null) {
      final AddressDTO address = customer.getAddress();
      jdbc.update(ADD_ADDRESS, new Object[] {
        uuid,
        getLine(customer.getAddress().getLines(),0),
        getLine(customer.getAddress().getLines(),1),
        getLine(customer.getAddress().getLines(),2),
        getLine(customer.getAddress().getLines(),3),
        getLine(customer.getAddress().getLines(),4),
        getLine(customer.getAddress().getLines(),5),
        address.getZipCode(),
        address.getCity(),
        address.getCountry()
      });
    }
    if(customer.getPhones() != null && !customer.getPhones().isEmpty()) {
      jdbc.batchUpdate(ADD_PHONE, customer.getPhones(), 250, (ps, phone) -> {
        ps.setObject(1, uuid);
        ps.setShort(2, phone.getType().getCode());
        ps.setString(3, phone.getNumber());
      });
    }
    return uuid.toString();
  }

  /**
   * Delete all customers in database
   */
  public void deleteAll() {
    jdbc.update(REQ_DELETE_ALL);
    if(vacuumReqs != null && !vacuumReqs.isEmpty()) {
      jdbc.batchUpdate(vacuumReqs.split(";"));
    }
  }
  // Methods -

}
