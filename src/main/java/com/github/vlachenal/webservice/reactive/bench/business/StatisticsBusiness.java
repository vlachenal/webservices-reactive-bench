package com.github.vlachenal.webservice.reactive.bench.business;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.github.vlachenal.webservice.reactive.bench.cache.StatisticsCache;
import com.github.vlachenal.webservice.reactive.bench.dao.StatisticsDAO;
import com.github.vlachenal.webservice.reactive.bench.dto.CallDTO;
import com.github.vlachenal.webservice.reactive.bench.dto.TestSuiteDTO;
import com.github.vlachenal.webservice.reactive.bench.errors.InvalidParametersException;

import reactor.core.publisher.Flux;


/**
 * Statistics business implementation.<br>
 * Performs checks and call DAOs.
 *
 * @author Vincent Lachenal
 */
@Component
public class StatisticsBusiness extends AbstractBusiness {

  // Attributes +
  /** Server CPU */
  @Value("${cpu}")
  private String cpu;

  /** Server memory */
  @Value("${memory}")
  private String memory;

  /** Statistics DAO */
  private final StatisticsDAO dao;

  /** Statistics cache */
  private final StatisticsCache cache;
  // Attributes -


  // Constructors +
  /**
   * {@link StatisticsBusiness} constructors
   *
   * @param dao the statistics DAO to use
   */
  public StatisticsBusiness(final StatisticsDAO dao, final StatisticsCache cache) {
    this.dao = dao;
    this.cache = cache;
  }
  // Constructors -


  // Methods +
  /**
   * Consolidate test suite
   *
   * @param suite the test suite to consolidate
   *
   * @throws InvalidParametersException missing or invalid parameters
   */
  public String consolidate(final TestSuiteDTO suite) throws InvalidParametersException {
    checkParameters("Test suite is null", suite);
    checkParameters("Invalid test suite information", suite.getClientCpu(), suite.getClientMemory(), suite.getClientJvmVersion(), suite.getClientJvmVendor(), suite.getClientOsName(), suite.getClientOsVersion());

    if(suite.getCalls() != null) {
      suite.getCalls().stream().forEach(call -> cache.mergeCall(call));
    }

    // Gather system informations +
    suite.setServerJvmVersion(System.getProperty("java.version"));
    suite.setServerJvmVendor(System.getProperty("java.vendor"));
    suite.setServerOsName(System.getProperty("os.name"));
    suite.setServerOsVersion(System.getProperty("os.version"));
    suite.setServerCpu(cpu);
    suite.setServerMemory(memory);
    // Gather system informations -

    return dao.save(suite);
  }

  /**
   * Register calls
   *
   * @param id the test suite identifier
   * @param calls the calls flux
   *
   * @throws InvalidParametersException invalid test identifier
   */
  public void registerCalls(final String id, final Flux<CallDTO> calls) throws InvalidParametersException {
    UUID uuid = null;
    try {
      uuid = UUID.fromString(id);
    } catch(final IllegalArgumentException e) {
      throw new InvalidParametersException(id + " is not an UUID");
    }
    dao.registerCalls(uuid, calls.doOnNext(c -> cache.mergeCall(c)));
  }
  // Methods -

}
