package com.github.vlachenal.webservice.reactive.bench.business;

import java.util.UUID;

import org.springframework.stereotype.Component;

import com.github.vlachenal.webservice.reactive.bench.dao.CustomerDAO;
import com.github.vlachenal.webservice.reactive.bench.dto.AddressDTO;
import com.github.vlachenal.webservice.reactive.bench.dto.CustomerDTO;
import com.github.vlachenal.webservice.reactive.bench.errors.InvalidParametersException;
import com.github.vlachenal.webservice.reactive.bench.errors.NotFoundException;

import reactor.core.publisher.Flux;


/**
 * Customer business component.<br>
 * Check business rules and call DAO.
 *
 * @author Vincent Lachenal
 */
@Component
public class CustomerBusiness extends AbstractBusiness {

  // Attributes +
  /** Customer DAO */
  private final CustomerDAO dao;
  // Attributes -


  // Constructors +
  /**
   * {@link CustomerBusiness} constructor
   *
   * @param dao the customer DAO to use
   */
  public CustomerBusiness(final CustomerDAO dao) {
    this.dao = dao;
  }
  // Constructors -


  // Methods +
  /**
   * List all customers
   *
   * @return the customers
   */
  public Flux<CustomerDTO> listAll() {
    return dao.listAll();
  }

  /**
   * Get customer's details
   *
   * @param id the customer's identifier
   *
   * @return the customer's details
   *
   * @throws InvalidParametersException invalid or missing parameter
   * @throws NotFoundException customer has not been found
   */
  public CustomerDTO getDetails(final String id) throws InvalidParametersException, NotFoundException {
    UUID custId = null;
    try {
      custId = UUID.fromString(id);
    } catch(final IllegalArgumentException e) {
      throw new InvalidParametersException(id + " is not an UUID");
    }
    return dao.getDetails(custId).orElseThrow(() -> new NotFoundException("Customer " + id + " does not exist"));
  }

  /**
   * Create new customer
   *
   * @param customer the customer to create
   *
   * @return the new customer's identifier
   *
   * @throws InvalidParametersException missing or invalid parameters
   */
  public String create(final CustomerDTO customer) throws InvalidParametersException {
    // Customer structure checks +
    checkParameters("Customer is null", customer);
    checkParameters("Customer first_name, last_name and brith_date has to be set", customer.getFirstName(), customer.getLastName(), customer.getBirthDate());
    // Customer structure checks -
    // Address structure checks +
    final AddressDTO addr = customer.getAddress();
    if(addr != null) {
      checkParameters("Address lines, zip_code, city and country has to be set", addr.getLines(), addr.getZipCode(), addr.getCity(),addr.getCountry());
    }
    // Address structure checks -
    return dao.create(customer);
  }

  /**
   * Delete all customers
   */
  public void deleteAll() {
    dao.deleteAll();
  }
  // Methods -

}
